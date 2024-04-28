package com.example.viniloscompose.activityTests

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.viniloscompose.MainActivity
import com.example.viniloscompose.pageobjects.clickSectionNavigationBar
import com.example.viniloscompose.pageobjects.login
import com.example.viniloscompose.ui.shared.BottomNavigationSection
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.shared.LoginType
import org.junit.Rule
import org.junit.Test

class AlbumScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    @OptIn(ExperimentalTestApi::class)
    fun renderAlbumTestScreen() {
        login(composeTestRule, LoginType.COLLECTOR)
        clickSectionNavigationBar(composeTestRule, BottomNavigationSection.ALBUM)
        composeTestRule.waitUntilNodeCount(hasContentDescription(ContentDescriptions.ALBUM_SCREEN_TITLE.value), 1, 2000)
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_SCREEN_BODY.value).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_SCREEN_SEARCHBAR.value).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_SCREEN_TITLE.value).assertIsDisplayed()
    }
}