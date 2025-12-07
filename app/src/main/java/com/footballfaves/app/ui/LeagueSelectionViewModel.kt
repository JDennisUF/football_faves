package com.footballfaves.app.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class LeagueSelectionViewModel @Inject constructor() : ViewModel() {
    private val defaultLeagues = listOf(
        LeagueListItem(id = 39, name = "Premier League", country = "England", tier = "Top Flight"),
        LeagueListItem(id = 140, name = "La Liga", country = "Spain", tier = "Top Flight"),
        LeagueListItem(id = 135, name = "Serie A", country = "Italy", tier = "Top Flight"),
        LeagueListItem(id = 61, name = "Ligue 1", country = "France", tier = "Top Flight"),
        LeagueListItem(id = 78, name = "Bundesliga", country = "Germany", tier = "Top Flight"),
        LeagueListItem(id = 94, name = "Primeira Liga", country = "Portugal", tier = "Top Flight"),
        LeagueListItem(id = 88, name = "Eredivisie", country = "Netherlands", tier = "Top Flight"),
        LeagueListItem(id = 144, name = "MLS", country = "USA & Canada", tier = "Top Flight"),
        LeagueListItem(id = 2, name = "UEFA Champions League", country = "Europe", tier = "Continental"),
        LeagueListItem(id = 3, name = "UEFA Europa League", country = "Europe", tier = "Continental"),
        LeagueListItem(id = 848, name = "Saudi Pro League", country = "Saudi Arabia", tier = "Top Flight"),
        LeagueListItem(id = 253, name = "Brasileirão Série A", country = "Brazil", tier = "Top Flight"),
    )

    private val _uiState = MutableStateFlow(
        LeagueSelectionUiState(
            leagues = defaultLeagues
        )
    )
    val uiState: StateFlow<LeagueSelectionUiState> = _uiState.asStateFlow()

    fun onQueryChange(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun clearQuery() {
        onQueryChange("")
    }

    fun toggleFavorite(leagueId: Int) {
        _uiState.update { state ->
            val updatedFavorites = state.favorites.toMutableSet()
            if (!updatedFavorites.add(leagueId)) {
                updatedFavorites.remove(leagueId)
            }

            state.copy(favorites = updatedFavorites)
        }
    }
}

data class LeagueSelectionUiState(
    val query: String = "",
    val leagues: List<LeagueListItem> = emptyList(),
    val favorites: Set<Int> = emptySet()
) {
    val filteredLeagues: List<LeagueListItem>
        get() {
            if (query.isBlank()) return leagues

            val lowercaseQuery = query.trim().lowercase()
            return leagues.filter { league ->
                league.name.lowercase().contains(lowercaseQuery) ||
                    league.country.lowercase().contains(lowercaseQuery)
            }
        }
}

data class LeagueListItem(
    val id: Int,
    val name: String,
    val country: String,
    val tier: String
)
