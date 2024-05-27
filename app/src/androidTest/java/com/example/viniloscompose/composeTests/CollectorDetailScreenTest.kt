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
import com.example.viniloscompose.fakeservices.FakeCollectorsService
import com.example.viniloscompose.fakeservices.FakeMusicianService
import com.example.viniloscompose.model.repository.AlbumRepository
import com.example.viniloscompose.model.repository.CollectorRepository
import com.example.viniloscompose.model.repository.MusicianRepository
import com.example.viniloscompose.pageobjects.clickAlbumsTab
import com.example.viniloscompose.ui.navigation.isSelectedBarItem
import com.example.viniloscompose.ui.screens.CollectorDetailScreen
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.theme.VinilosComposeTheme
import com.example.viniloscompose.utils.cache.FixedCacheManager
import com.example.viniloscompose.utils.network.FixedNetworkValidator
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CollectorDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController
    lateinit var albumRepository: AlbumRepository
    lateinit var musicianRepository: MusicianRepository
    lateinit var collectorRepository: CollectorRepository
    private var amount: Int = 3


    private fun setupContent(amount: Int) {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            val isSelected = isSelectedBarItem(navController = navController)
            val cacheManager = FixedCacheManager()
            val networkValidator = FixedNetworkValidator(true)
            val fakeAlbumService = FakeAlbumService(amount)
            val fakeMusicianService = FakeMusicianService(amount)
            val fakeCollectorsService = FakeCollectorsService(amount)
            albumRepository = AlbumRepository(cacheManager, networkValidator, fakeAlbumService)
            musicianRepository =
                MusicianRepository(cacheManager, networkValidator, fakeMusicianService)
            collectorRepository =
                CollectorRepository(cacheManager, networkValidator, fakeCollectorsService)
            VinilosComposeTheme {
                CollectorDetailScreen(
                    collectorRepository = collectorRepository,
                    collectorId = 0,
                    albumRepository = albumRepository,
                    musicianRepository = musicianRepository,
                    onNavigate = { dest -> navController.navigate(dest) },
                    isSelected = { dest -> isSelected(dest) },
                    popBackStackAction = {},
                    onAlbumClick = {},
                    onMusicianClick = {}
                )
            }

        }
    }

    @Before
    fun setup() {
        setupContent(amount)
    }

    @Test
    fun renderCollectorDetailsScreenWithTabs(){
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.COLLECTOR_DETAIL_SCREEN.value)
                .isDisplayed()
        }
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.COLLECTOR_DETAIL_INFORMATION.value)
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.COLLECTOR_DETAIL_MUSICIANS_TAB.value)
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.COLLECTOR_DETAIL_ALBUMS_TAB.value)
    }

    @Test
    fun renderCollectorMusiciansCorrectly(){
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.COLLECTOR_DETAIL_SCREEN.value)
                .isDisplayed()
        }
        composeTestRule.onAllNodesWithContentDescription(ContentDescriptions.MUSICIAN_CARD.value).assertCountEquals(amount)
    }

    @Test
    fun renderCollectorAlbumsCorrectly(){
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithContentDescription(ContentDescriptions.COLLECTOR_DETAIL_SCREEN.value)
                .isDisplayed()
        }
        clickAlbumsTab(composeTestRule)
        composeTestRule.onAllNodesWithContentDescription(ContentDescriptions.ALBUM_CARD.value).assertCountEquals(amount)

    }


}