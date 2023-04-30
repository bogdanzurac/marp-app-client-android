package dev.bogdanzurac.marp.feature.notes.ui

import androidx.lifecycle.viewModelScope
import dev.bogdanzurac.marp.feature.notes.ui.NoteDetailsViewModel.NoteDetailsUiState
import dev.bogdanzurac.marp.feature.notes.ui.NoteDetailsViewModel.NoteDetailsUiState.*
import dev.bogdanzurac.marp.core.*
import dev.bogdanzurac.marp.core.auth.AuthManager
import dev.bogdanzurac.marp.core.prompts.DialogManager
import dev.bogdanzurac.marp.core.ui.BaseViewModel
import dev.bogdanzurac.marp.core.ui.Tracker
import dev.bogdanzurac.marp.core.ui.UiState
import dev.bogdanzurac.marp.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.feature.notes.domain.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock.System.now
import org.koin.core.annotation.Factory
import kotlin.Result.Companion.success
import kotlin.time.Duration.Companion.seconds

@Factory
internal class NoteDetailsViewModel(
    args: NoteDetailsArgs,
    private val authManager: AuthManager,
    private val createNoteUseCase: CreateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val observeNoteUseCase: ObserveNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val dialogManager: DialogManager,
    private val tracker: Tracker,
    private val navigator: NotesNavigator,
) : BaseViewModel<NoteDetailsUiState>(), NoteDetailsUiEvents {

    private val noteId: String? = args.noteId
    private val cryptoId: String? = args.cryptoId

    private val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val observeId: MutableStateFlow<String?> = MutableStateFlow(null)
    private val observeEdits: MutableSharedFlow<Pair<String, String>> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    init {
        viewModelScope.launch {
            if (noteId == null) {
                createNoteUseCase(Note(cryptoId = cryptoId, userId = authManager.getUser()!!.id))
                    .onSuccess { observeId.emit(it.id) }
                    .onFailure {
                        logger.e("Could not create note", it)
                        dialogManager.showDialog(getGenericErrorDialogFor(it))
                        navigator.navigateBack()
                    }
            } else {
                observeId.emit(noteId)
            }
        }
        observeEdits
            .debounce(1.seconds)
            .combine(observeNote()) { edit, note ->
                editNote(note.getOrThrow(), edit.first, edit.second)
            }
            .launchIn(viewModelScope)
    }

    private fun observeNote(): Flow<Result<Note>> =
        observeId
            .filterNotNull()
            .flatMapConcat { observeNoteUseCase(it) }
            .shared()

    override val uiState: StateFlow<NoteDetailsUiState> =
        authManager.observeAuthenticatedState()
            .flatMapLatest { isAuthenticated ->
                val flow = if (!isAuthenticated) flowOf(success(null))
                else observeNote()
                flow.combineResult(
                    loadingState.mapAsResult(),
                    flowOf(success(isAuthenticated)),
                )
            }
            .onStart {
                val screenName = noteId?.let { NOTE_DETAILS_SCREEN } ?: NEW_NOTE_SCREEN
                tracker.trackScreen(screenName, noteId)
            }
            .onFailure {
                if (loadingState.value) return@onFailure
                logger.e("Could not get note", it)
                dialogManager.showDialog(getGenericErrorDialogFor(it))
            }
            .foldResult({ (note, isLoading, isAuthenticated) ->
                when {
                    !isAuthenticated -> {
                        navigator.navigateBack()
                        Loading
                    }
                    isLoading -> Loading
                    else -> Success(note!!)
                }
            }, { Error(it) })
            .asState(Loading)

    internal sealed class NoteDetailsUiState : UiState {
        class Error(val exception: Throwable) : NoteDetailsUiState()
        class Success(val note: Note) : NoteDetailsUiState()
        object Loading : NoteDetailsUiState()
    }

    override fun onDeleteNoteClicked() {
        loadingState.tryEmit(true)
        observeNote()
            .mapResult { deleteNoteUseCase(it.id) }
            .onSuccess { navigator.navigateBack() }
            .onFailure {
                loadingState.tryEmit(false)
                logger.e("Could not delete note", it)
                dialogManager.showDialog(getGenericErrorDialogFor(it))
            }
            .launchIn(viewModelScope)
    }

    override fun onNoteEdited(title: String, body: String) {
        observeEdits.tryEmit(Pair(title, body))
    }

    private suspend fun editNote(note: Note, title: String, body: String) {
        val updatedNote = note.copy(title = title, body = body, updatedAt = now())
        editNoteUseCase(updatedNote)
    }

    // Needed because Koin doesn't know how to inject multiple parameters of the same type
    internal data class NoteDetailsArgs(
        val noteId: String?,
        val cryptoId: String? = null
    )
}

internal interface NoteDetailsUiEvents {
    fun onDeleteNoteClicked()
    fun onNoteEdited(title: String, body: String)
}