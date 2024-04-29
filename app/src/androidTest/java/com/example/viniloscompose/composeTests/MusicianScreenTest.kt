package com.example.viniloscompose.composeTests

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.viniloscompose.MainActivity
import com.example.viniloscompose.fakeservices.FakeMusicianService
import com.example.viniloscompose.model.dto.MusicianDto
import com.example.viniloscompose.model.repository.MusicianRepository
import com.example.viniloscompose.model.service.IMusicianService
import com.example.viniloscompose.pageobjects.clickSectionNavigationBar
import com.example.viniloscompose.pageobjects.login
import com.example.viniloscompose.pageobjects.searchForAllMusicianCards
import com.example.viniloscompose.pageobjects.searchForFirstMusicianCard
import com.example.viniloscompose.pageobjects.searchForLastMusicianCard
import com.example.viniloscompose.pageobjects.searchForMusicianCardByText
import com.example.viniloscompose.ui.navigation.isSelectedBarItem
import com.example.viniloscompose.ui.screens.MusicianScreen
import com.example.viniloscompose.ui.shared.BottomNavigationSection
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.shared.LoginType
import com.example.viniloscompose.ui.theme.VinilosComposeTheme
import com.example.viniloscompose.viewModel.MusicianViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class MusicianScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    lateinit var repository: MusicianRepository
    var amountOfMusicians: Int = 3

    private fun setupContent(amount: Int) {
        amountOfMusicians = amount
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            val isSelected = isSelectedBarItem(navController = navController)
            val fakeMusicianService = FakeMusicianService(amount)
            repository = MusicianRepository(fakeMusicianService)
            val fakeViewModel = MusicianViewModel(repository)
            VinilosComposeTheme {
                MusicianScreen(
                    onNavigate = { dest -> navController.navigate(dest) },
                    isSelected = { dest -> isSelected(dest) },
                    fakeViewModel
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
        searchForFirstMusicianCard(composeTestRule).onChildren().assertAny(hasText("Musician: 0"))
        searchForLastMusicianCard(composeTestRule).onChildren()
            .assertAny(hasText("Musician: ${amountOfMusicians - 1}"))
    }

    @Test
    fun musicianScreenMusicianCardsAreRenderedCorrectly() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.MUSICIAN_SCREEN.value)
                .isDisplayed()
        }
        searchForAllMusicianCards(composeTestRule).assertAll(
            hasAnyChild(
                hasText(
                    text = "Musician",
                    substring = true,
                    ignoreCase = true
                )
            ).and(
                hasAnyChild(
                    hasContentDescription(ContentDescriptions.MUSICIAN_CARD_IMAGE.value)
                )
            )
        )
    }
}

