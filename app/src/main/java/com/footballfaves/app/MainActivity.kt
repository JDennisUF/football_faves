package com.footballfaves.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.footballfaves.app.ui.LeagueSelectionScreen
import com.footballfaves.app.ui.LeagueSelectionViewModel
import com.footballfaves.app.ui.theme.FootballFavesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FootballFavesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FootballFavesAppRoot()
                }
            }
        }
    }
}

@Composable
fun FootballFavesAppRoot(modifier: Modifier = Modifier) {
    val viewModel: LeagueSelectionViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LeagueSelectionScreen(
        modifier = modifier,
        state = uiState,
        onQueryChange = viewModel::onQueryChange,
        onClearQuery = viewModel::clearQuery,
        onToggleFavorite = viewModel::toggleFavorite,
        onContinue = { /* TODO: navigate to team selector */ }
    )
}
