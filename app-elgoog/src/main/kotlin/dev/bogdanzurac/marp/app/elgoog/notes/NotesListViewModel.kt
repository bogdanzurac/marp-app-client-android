package dev.bogdanzurac.marp.app.elgoog.notes

import dev.bogdanzurac.marp.core.prompts.DialogManager
import dev.bogdanzurac.marp.core.auth.AuthManager
import dev.bogdanzurac.marp.core.ui.BaseViewModel
import dev.bogdanzurac.marp.core.ui.Tracker
import dev.bogdanzurac.marp.core.ui.UiState
import dev.bogdanzurac.marp.core.ui.getGenericErrorDialogFor
import dev.bogdanzurac.marp.app.elgoog.notes.NotesListViewModel.NotesListUiState
import dev.bogdanzurac.marp.app.elgoog.notes.NotesListViewModel.NotesListUiState.*
import dev.bogdanzurac.marp.core.combineResult
import dev.bogdanzurac.marp.core.foldResult
import dev.bogdanzurac.marp.core.logger
import dev.bogdanzurac.marp.core.onFailure
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import org.koin.core.annotation.Factory
import kotlin.Result.Companion.success

@Factory
internal class NotesListViewModel(
    private val dialogManager: DialogManager,
    private val notesNavigator: NotesNavigator,
    private val tracker: Tracker,
    authManager: AuthManager,
    observeUserNotesUseCase: ObserveUserNotesUseCase,
) : BaseViewModel<NotesListUiState>(), NotesListUiEvents {

    override val uiState: StateFlow<NotesListUiState> =
        authManager.observeAuthenticatedState()
            .flatMapLatest { isAuthenticated ->
                if (!isAuthenticated) flowOf(success(Pair(emptyList(), isAuthenticated)))
                else observeUserNotesUseCase().combineResult(flowOf(success(isAuthenticated)))
            }
            .onStart { tracker.trackScreen(NOTES_LIST_SCREEN) }
            .onFailure {
                logger.e("Could not get notes list", it)
                dialogManager.showDialog(getGenericErrorDialogFor(it))
            }
            .foldResult({ (notes, isAuthenticated) ->
                when {
                    !isAuthenticated -> NotLoggedIn
                    notes.isEmpty() -> Empty
                    else -> Success(
                        notes.filter { it.cryptoId.isNullOrBlank() },
                        notes.filter { it.cryptoId?.isNotBlank() == true })
                }
            }, { Error(it) })
            .asState(Loading)

    internal sealed class NotesListUiState : UiState {
        class Error(val exception: Throwable) : NotesListUiState()
        class Success(
            val personalNotes: List<Note>,
            val cryptoNotes: List<Note>
        ) : NotesListUiState()

        object Loading : NotesListUiState()
        object Empty : NotesListUiState()
        object NotLoggedIn : NotesListUiState()
    }

    override fun onAddNoteClicked() {
        notesNavigator.navigateToAddNote()
    }

    override fun onNoteClicked(id: String) {
        notesNavigator.navigateToNoteDetails(id)
    }

    override fun onLoginClicked() {
        notesNavigator.navigateToLogin()
    }
}

internal interface NotesListUiEvents {
    fun onAddNoteClicked()
    fun onNoteClicked(id: String)
    fun onLoginClicked()
}