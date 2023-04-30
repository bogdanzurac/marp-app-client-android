package dev.bogdanzurac.marp.app.elgoog.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.bogdanzurac.marp.app.elgoog.R
import dev.bogdanzurac.marp.core.navigation.AppNavigator
import dev.bogdanzurac.marp.core.navigation.AppRoute
import dev.bogdanzurac.marp.core.navigation.FeatureNavigator
import dev.bogdanzurac.marp.app.elgoog.core.theme.ElgoogTheme
import dev.bogdanzurac.marp.core.ui.composable.AppDialog
import dev.bogdanzurac.marp.core.ui.composable.BaseScreen
import dev.bogdanzurac.marp.core.ui.composable.LoadingView
import dev.bogdanzurac.marp.feature.crypto.ui.Crypto
import dev.bogdanzurac.marp.feature.crypto.ui.cryptoNavGraph
import dev.bogdanzurac.marp.app.elgoog.dashboard.DashboardViewModel.DashboardUiState.*
import dev.bogdanzurac.marp.app.elgoog.dashboard.ElgoogBottomNavigationItem.*
import dev.bogdanzurac.marp.app.elgoog.login.loginNavGraph
import dev.bogdanzurac.marp.app.elgoog.movies.moviesNavGraph
import dev.bogdanzurac.marp.app.elgoog.news.newsNavGraph
import dev.bogdanzurac.marp.feature.notes.ui.notesNavGraph
import dev.bogdanzurac.marp.feature.weather.ui.weatherNavGraph
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun DashboardScreen(
    appNavigator: AppNavigator,
    viewModel: DashboardViewModel = koinViewModel(),
) = BaseScreen(viewModel) { state ->
    when (val uiState = state.value) {
        is Success -> DashboardView(uiState, viewModel, appNavigator)
        is LoadingFeatures -> LoadingView()
    }
    AppDialog()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardView(
    state: Success,
    events: DashboardUiEvents,
    appNavigator: AppNavigator,
) {
    val navController = rememberNavController()
    DisposableEffect(navController) {
        appNavigator.onAttach(navController)
        onDispose { appNavigator.onDetach(navController) }
    }
    val scrollBehavior = enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopBarView(state, events::onLogoutClicked, scrollBehavior) },
        bottomBar = {
            if (state.isBottomNavigationBarVisible)
                BottomNavigationBarView(state, events::onRouteClicked)
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Crypto.path,
            modifier = Modifier.padding(
                PaddingValues(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
            )
        ) {
            cryptoNavGraph()
            loginNavGraph()
            moviesNavGraph()
            newsNavGraph()
            notesNavGraph()
            weatherNavGraph()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarView(
    state: Success,
    onLogoutClicked: () -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.mipmap.ic_logo),
                        modifier = Modifier.padding(end = 16.dp),
                        contentDescription = "",
                    )
                    Text(
                        text =
                        if (state.isUserLoggedIn) state.user!!.email
                        else stringResource(R.string.app_name),
                        style =
                        if (state.isUserLoggedIn) MaterialTheme.typography.bodyLarge
                        else MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
                if (state.isUserLoggedIn) {
                    Button({ onLogoutClicked() }) {
                        Icon(
                            painterResource(R.drawable.ic_logout),
                            stringResource(R.string.button_logout),
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary),
        scrollBehavior = topAppBarScrollBehavior
    )
}

@Composable
private fun BottomNavigationBarView(
    state: Success,
    onRouteClicked: (route: AppRoute) -> Unit,
) {
    NavigationBar {
        state.items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.imageRes),
                        contentDescription = stringResource(item.titleRes),
                    )
                },
                label = { Text(stringResource(item.titleRes)) },
                selected = state.selectedItemPosition == index,
                onClick = { onRouteClicked(item.route) }
            )
        }
    }
}

@Composable
@Preview
private fun DashboardPreview() {
    ElgoogTheme {
        DashboardView(
            state = Success(bottomNavigationItems, 0),
            events = object : DashboardUiEvents {
                override fun onRouteClicked(route: AppRoute) {}
                override fun onLogoutClicked() {}
            },
            appNavigator = object : AppNavigator() {
                override fun getFeatureNavigatorForRoute(route: String): FeatureNavigator =
                    FeatureNavigator()
            }
        )
    }
}
