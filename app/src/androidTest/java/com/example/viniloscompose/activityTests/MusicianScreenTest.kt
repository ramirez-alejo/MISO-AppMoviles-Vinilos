package com.example.viniloscompose.activityTests

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.viniloscompose.MainActivity
import com.example.viniloscompose.pageobjects.clickSectionNavigationBar
import com.example.viniloscompose.pageobjects.login
import com.example.viniloscompose.ui.shared.BottomNavigationSection
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.shared.LoginType
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MusicianScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    @OptIn(ExperimentalTestApi::class)
    fun renderMusicianScreen() {
        login(composeTestRule, LoginType.COLLECTOR)
        clickSectionNavigationBar(composeTestRule, BottomNavigationSection.MUSICIAN)
        composeTestRule.waitUntilNodeCount(hasContentDescription(ContentDescriptions.MUSICIANS_SCREEN_TITLE.value), 1)
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.MUSICIANS_SCREEN_BODY.value).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.MUSICIANS_SCREEN_TITLE.value).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.MUSICIANS_SCREEN_SEARCHBAR.value).assertIsDisplayed()
    }
    @Test
    @OptIn(ExperimentalTestApi::class)
    fun renderMusicianScreenWithMusicians() {
        clickSectionNavigationBar(composeTestRule, BottomNavigationSection.MUSICIAN)
        composeTestRule.waitUntilNodeCount(hasContentDescription(ContentDescriptions.MUSICIANS_SCREEN_TITLE.value), 1)
        (ContentDescriptions.MUSICIAN_CARD.value)
    }


}