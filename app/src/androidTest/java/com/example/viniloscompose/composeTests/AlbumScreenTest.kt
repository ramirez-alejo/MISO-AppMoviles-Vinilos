package com.example.viniloscompose.composeTests

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.viniloscompose.fakeservices.FakeAlbumService
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.ui.navigation.AppScreens
import com.example.viniloscompose.ui.navigation.isSelectedBarItem
import com.example.viniloscompose.ui.screens.AlbumScreen
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.theme.VinilosComposeTheme
import com.example.viniloscompose.utils.cache.FixedCacheManager
import com.example.viniloscompose.utils.network.FixedNetworkValidator
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
            VinilosComposeTheme {
                AlbumScreen(
                    onNavigate = { dest -> navController.navigate(dest) },
                    isSelected = { dest -> isSelected(dest) },
                    albumRepository = repository,
                    onCardClick = { id -> navController.navigate(AppScreens.AlbumDetailScreen.route+"/$id")}

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
    fun albumScreenAlbumCardsAreRenderedCorrectly1() {
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_SCREEN.value)
                .isDisplayed()
        }

        composeTestRule.onAllNodesWithContentDescription(ContentDescriptions.ALBUM_CARD.value)
            .assertCountEquals(amountOfAlbums)
    }
}