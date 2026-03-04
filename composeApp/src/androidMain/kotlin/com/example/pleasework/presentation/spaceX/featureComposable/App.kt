package com.example.pleasework.presentation.spaceX.featureComposable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pleasework.presentation.spaceX.viewmodel.RocketLaunchViewModel
import com.example.pleasework.feature.spaceX.data.model.ResultState
import com.example.pleasework.feature.spaceX.domain.model.RocketLaunch
import com.example.pleasework.presentation.theme.AppTheme
import com.example.pleasework.presentation.theme.app_theme_successful
import com.example.pleasework.presentation.theme.app_theme_unsuccessful
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {

    val viewModel = koinViewModel<RocketLaunchViewModel>()
    val resultState by viewModel.state.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadLaunches()
    }

    LaunchedEffect(resultState) {
        if (resultState !is ResultState.Loading) {
            isRefreshing = false
        }
    }

    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("SpaceX Launches") }
                )
            }
        ) { padding ->

            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {

                when (val state = resultState) {

                    is ResultState.Loading -> {
                        if (!isRefreshing) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    is ResultState.Success -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.data) { launch ->
                                LaunchItem(launch)
                            }
                        }
                    }

                    is ResultState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(state.message)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LaunchItem(launch: RocketLaunch) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "${launch.missionName} - ${launch.launchYear}",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = if (launch.launchSuccess == true)
                "Successful"
            else
                "Unsuccessful",
            color = if (launch.launchSuccess == true)
                app_theme_successful
            else
                app_theme_unsuccessful
        )

        Spacer(Modifier.height(8.dp))

        launch.details?.takeIf { it.isNotBlank() }?.let {
            Text(it)
        }
    }

    Divider()
}