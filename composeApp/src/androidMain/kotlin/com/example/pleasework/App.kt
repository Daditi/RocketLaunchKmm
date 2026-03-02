package com.example.pleasework

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pleasework.entity.RocketLaunch
import com.example.pleasework.theme.AppTheme
import com.example.pleasework.theme.app_theme_successful
import com.example.pleasework.theme.app_theme_unsuccessful
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<RocketLaunchViewModel>()
    val state by viewModel.state
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
//    val pullToRefreshState = rememberPullToRefreshState()

//    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(Unit) {
//            isRefreshing = true
            viewModel.loadLaunches()
//            isRefreshing = false
        }
//    }

    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "SpaceX Launches",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
//                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
            ) {
                if (state.isLoading && !isRefreshing) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(8.dp))
                        Text("Loading...", style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.launches) { launch: RocketLaunch ->
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "${launch.missionName} - ${launch.launchYear}",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = if (launch.launchSuccess == true) "Successful" else "Unsuccessful",
                                    color = if (launch.launchSuccess == true) app_theme_successful else app_theme_unsuccessful
                                )
                                Spacer(Modifier.height(8.dp))
                                val details = launch.details
                                if (details != null && details.isNotBlank()) {
                                    Text(details)
                                }
                            }
//                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}
