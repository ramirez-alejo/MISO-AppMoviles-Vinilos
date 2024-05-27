package com.example.viniloscompose.composeTests

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.viniloscompose.fakeservices.FakeAlbumService
import com.example.viniloscompose.fakeservices.FakeTracksService
import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.model.repository.TrackRepository
import com.example.viniloscompose.pageobjects.addTrack
import com.example.viniloscompose.ui.navigation.isSelectedBarItem
import com.example.viniloscompose.ui.screens.AlbumDetailScreen
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.theme.VinilosComposeTheme
import com.example.viniloscompose.utils.cache.FixedCacheManager
import com.example.viniloscompose.utils.network.FixedNetworkValidator
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AlbumDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    lateinit var repository: AlbumRepository

    lateinit var trackRepository: TrackRepository

    private var amountOfTracks: Int = 3

    private fun setupContent(amount: Int) {
        amountOfTracks = amount
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            val isSelected = isSelectedBarItem(navController = navController)
            val cacheManager = FixedCacheManager()
            val networkValidator = FixedNetworkValidator(true)
            val fakeAlbumService = FakeAlbumService(amount)
            val fakeTracksService = FakeTracksService(amount)
            repository = AlbumRepository(cacheManager, networkValidator, fakeAlbumService)
            trackRepository = TrackRepository(cacheManager, networkValidator, fakeTracksService)
            VinilosComposeTheme {
                AlbumDetailScreen(
                    onNavigate = { dest -> navController.navigate(dest) },
                    isSelected = { dest -> isSelected(dest) },
                    albumRepository = repository,
                    albumId = 0,
                    trackRepository = trackRepository,
                    popBackStackAction = {}
                )
            }
        }
    }

    @Before
    fun setup() {
        setupContent(amountOfTracks)
    }

    @Test
    fun renderAlbumDetailScreenWithTracks() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_SCREEN.value)
                .isDisplayed()
        }
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_CARD_IMAGE.value)
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_BACK.value)
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_TITLE.value)
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_CARD_PERFORMER_NAME.value)
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_DATE.value)
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_LABEL.value)
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_TRACK_LIST.value)
    }

    @Test
    fun trackCardsRenderCorrectly() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_SCREEN.value)
                .isDisplayed()
        }

        composeTestRule.onAllNodesWithContentDescription(ContentDescriptions.ALBUM_DETAIL_CARD_TRACK.value)
            .assertCountEquals(amountOfTracks)
    }

    @Test
    fun addTrackRendersCorrectly() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_SCREEN.value)
                .isDisplayed()
        }

        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_TRACK_CREATE.value)

    }

    @Test
    fun addTrackWorks() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_TRACK_CREATE.value)
                .isDisplayed()
        }

        addTrack(composeTestRule, CreateTrackDto(
            name = "New track",
            duration = "11:11"
        ))

        composeTestRule.onAllNodesWithContentDescription(ContentDescriptions.ALBUM_DETAIL_CARD_TRACK.value)
            .assertCountEquals(amountOfTracks + 1)
    }
}