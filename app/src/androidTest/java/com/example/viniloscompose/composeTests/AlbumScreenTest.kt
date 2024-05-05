package com.example.viniloscompose.composeTests

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.viniloscompose.fakeservices.FakeAlbumService
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.pageobjects.searchForAllNodesWithDescription
import com.example.viniloscompose.ui.navigation.isSelectedBarItem
import com.example.viniloscompose.ui.screens.AlbumScreen
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.theme.VinilosComposeTheme
import com.example.viniloscompose.utils.cache.FixedCacheManager
import com.example.viniloscompose.utils.network.FixedNetworkValidator
import com.example.viniloscompose.viewModel.AlbumViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AlbumScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    lateinit var repository: AlbumRepository
    var amountOfAlbums: Int = 3

    private fun setupContent(amount: Int) {
        amountOfAlbums = amount
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            val isSelected = isSelectedBarItem(navController = navController)
            val cacheManager = FixedCacheManager()
            val networkValidator = FixedNetworkValidator(true)
            val fakeAlbumService = FakeAlbumService(amount)
            repository = AlbumRepository(cacheManager, networkValidator, fakeAlbumService)
            val fakeViewModel = AlbumViewModel(repository)
            VinilosComposeTheme {
                AlbumScreen(
                    onNavigate = { dest -> navController.navigate(dest) },
                    isSelected = { dest -> isSelected(dest) },
                    fakeViewModel
                )
            }
        }
    }

    @Before
    fun setup() {
        setupContent(amountOfAlbums)
    }

    @Test
    fun renderAlbumScreenWith3Albums() {
        composeTestRule.waitUntil(5000) {
                composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_SCREEN.value)
                .isDisplayed()
        }
        composeTestRule.onAllNodesWithContentDescription(ContentDescriptions.ALBUM_CARD.value)
            .assertCountEquals(amountOfAlbums)
    }

    @Test
    fun albumScreenAlbumCardsAreRenderedCorrectly() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_SCREEN.value)
                .isDisplayed()
        }
        searchForAllNodesWithDescription(composeTestRule, ContentDescriptions.ALBUM_CARD).assertAll(
            hasAnyChild(
                hasText(
                    text = "Album",
                    substring = true,
                    ignoreCase = true
                )
            ).and(
                hasAnyChild(
                    hasContentDescription(ContentDescriptions.ALBUM_CARD_IMAGE.value)
                )
            ).and(
                hasAnyChild(
                    hasContentDescription(ContentDescriptions.ALBUM_CARD_PERFORMER_NAME.value)
                )
            )
        )
    }
}