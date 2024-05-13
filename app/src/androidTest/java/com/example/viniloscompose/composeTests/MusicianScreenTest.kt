package com.example.viniloscompose.composeTests

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.viniloscompose.fakeservices.FakeMusicianService
import com.example.viniloscompose.model.repository.MusicianRepository
import com.example.viniloscompose.pageobjects.searchForAllMusicianCards
import com.example.viniloscompose.pageobjects.searchForFirstMusicianCard
import com.example.viniloscompose.pageobjects.searchForLastMusicianCard
import com.example.viniloscompose.ui.navigation.AppScreens
import com.example.viniloscompose.ui.navigation.isSelectedBarItem
import com.example.viniloscompose.ui.screens.MusicianScreen
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.theme.VinilosComposeTheme
import com.example.viniloscompose.utils.cache.FixedCacheManager
import com.example.viniloscompose.utils.network.FixedNetworkValidator
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MusicianScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    private lateinit var repository: MusicianRepository
    private var amountOfMusicians: Int = 3

    private fun setupContent(amount: Int) {
        amountOfMusicians = amount
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            val isSelected = isSelectedBarItem(navController = navController)
            val fakeMusicianService = FakeMusicianService(amount)
            val cacheManager = FixedCacheManager()
            val networkValidator = FixedNetworkValidator(true)
            repository = MusicianRepository(cacheManager, networkValidator, fakeMusicianService)
            VinilosComposeTheme {
                MusicianScreen(
                    onNavigate = { dest -> navController.navigate(dest) },
                    isSelected = { dest -> isSelected(dest) },
                    musicianRepository = repository,
                    onCardClick = { id -> navController.navigate(AppScreens.MusicianDetailScreen.route+"/$id")}
                )
            }
        }
    }

    @Before
    fun setup() {
        setupContent(3)
    }

    @Test
    fun renderMusicianScreenWith3Musicians() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.MUSICIAN_SCREEN.value)
                .isDisplayed()
        }
        composeTestRule.onAllNodesWithContentDescription(ContentDescriptions.MUSICIAN_CARD.value)
            .assertCountEquals(amountOfMusicians)
    }

    @Test
    fun musicianScreenArtistsAreOrdered() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.MUSICIAN_SCREEN.value)
                .isDisplayed()
        }
        composeTestRule.onRoot().printToLog("debug printing")
        searchForFirstMusicianCard(composeTestRule).assert(hasText("Musician: 0"))
        searchForLastMusicianCard(composeTestRule).assert(hasText("Musician: ${amountOfMusicians - 1}"))
    }

    @Test
    fun musicianScreenMusicianCardsAreRenderedCorrectly() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.MUSICIAN_SCREEN.value)
                .isDisplayed()
        }
        searchForAllMusicianCards(composeTestRule).assertAll(
                hasText(
                    text = "Musician",
                    substring = true,
                    ignoreCase = true
                ).and(
                    hasContentDescription(ContentDescriptions.MUSICIAN_CARD_IMAGE.value)

            )
        )
    }
}

