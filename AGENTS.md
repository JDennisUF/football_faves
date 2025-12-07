## Project Brief

We are building **Football Faves**, an Android-only phone app (Samsung Galaxy A15 target) that lets users pick favorite soccer teams and receive notifications about their matches or score events. It will integrate with the [API-Football](https://www.api-football.com/) service for all league, team, and fixture data. iOS is out of scope unless we happen to get parity via shared Kotlin Multiplatform modules at low effort.

## High-Level Requirements
- Android 14+ support, optimized for Samsung Galaxy A15 hardware profile (6.5" display, 1080x2340, 90Hz).
- Kotlin-first stack with Jetpack Compose UI, MVVM architecture, Kotlin Coroutines/Flow, Hilt for DI.
- API-Football integration for leagues, teams, fixtures, standings.
- League/team selector flow (first milestone) to let users pick favorites and store them locally.
- Notification pipeline to alert users about upcoming fixtures and in-progress score changes for favorites.
- Offline-first behavior for already fetched leagues/teams/favorites, using Room database or DataStore.

## Implementation Plan

1. **Environment & Access**
   - Install Android Studio Giraffe+ with latest SDKs, Pixel 6/Generic emulator, and Samsung Galaxy A15 hardware profile (or configure via Device Manager).
   - Acquire API-Football key, store in local `local.properties` or encrypted resource; add build config entries using Gradle `buildConfigField`.
   - Define package `com.footballfaves.app`.

2. **Project Skeleton**
   - Create new Compose Material 3 project with Kotlin DSL Gradle scripts.
   - Modules:
     - `app`: Android UI + DI graph.
     - `core:model`, `core:network`, `core:database` (optional initial split; start single module if time).
   - Configure baseline dependencies: Compose BOM, Hilt, Retrofit+OkHttp, Kotlin Serialization/Moshi, Room, WorkManager, Firebase Cloud Messaging (for push) or local notifications fallback.

3. **Networking Layer (API-Football)**
   - Define API models for leagues, teams, fixtures using Kotlin data classes.
   - Build Retrofit service with API key header (`x-rapidapi-key`) and host header.
   - Implement repository interfaces exposing suspend functions / Flows for leagues and teams.
   - Add basic logging/interceptors and error mapping to domain results.

4. **Local Persistence**
   - Employ Room for caching leagues/teams and storing user favorites (`FavoriteTeamEntity` linking leagueId+teamId).
   - Provide DAO methods for insert/update/delete favorites and cached data timestamps.
   - Consider DataStore for lightweight user preferences (notification settings, default league).

5. **League & Team Selector (Milestone 1)**
   - Compose screens:
     - League list/search screen (filters, region tabs) pulling from repository (cached+network).
     - Team list per league with multi-select toggles.
     - Favorites review screen summarizing selections.
   - State management via ViewModels + Flow/UiState classes.
   - Persist favorites immediately on toggle; surface offline indicator when network unavailable.
   - Add instrumentation/unit tests for repositories and ViewModels (using Turbine, MockWebServer, Room in-memory DB).

6. **Notifications & Scheduling**
   - Decide notification strategy:
     - Short term: WorkManager periodic job polling API-Football for upcoming fixtures for favorite teams (cache results to avoid rate limits).
     - Long term: Integrate Firebase Cloud Messaging or topic-based pushes if API-Football/webhook allows.
   - Build notification formatter and channel definitions (Android notification channels for match alerts, kickoff reminders).
   - Add settings screen to control notification timing (e.g., 30 min before kickoff, goal alerts).

7. **Samsung Galaxy A15 Optimization**
   - Test layout densities (responsive Compose preview for 1080x2340).
   - Validate performance on physical device/emulator (battery, network usage, background limits).
   - Ensure notification permission requests align with Android 13+ requirements and Samsung’s notification settings UI.

8. **Polish & Future Enhancements**
   - Dark theme, dynamic color, accessibility (TalkBack focus order on selectors).
   - Explore Compose Multiplatform or KMM for optional iOS support later.
   - Add widgets or Wear OS extension if prioritized.

## Next Actions
1. Stand up Android Studio project with Compose template and configure API keys.
2. Implement networking/persistence scaffolding for league and team data.
3. Build the league/team selector flow end-to-end, including local storage of favorites.
4. Prototype WorkManager-based notification polling for favorite fixtures.
