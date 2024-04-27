package com.example.viniloscompose.activityTests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.viniloscompose.MainActivity
import com.example.viniloscompose.ui.shared.BottomNavigationSection
import com.example.viniloscompose.ui.shared.LoginType
import com.example.viniloscompose.pageobjects.clickSectionNavigationBar
import com.example.viniloscompose.pageobjects.login
import com.example.viniloscompose.ui.shared.ContentDescriptions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        login(composeTestRule, LoginType.COLLECTOR)
    }

    @Test
    fun validateNavigationBarIsDisplayed() {
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.BOTTOM_NAVIGATION.value).assertIsDisplayed()
        composeTestRule.onNodeWithText(BottomNavigationSection.ALBUM.value).assertIsDisplayed()
        composeTestRule.onNodeWithText(BottomNavigationSection.MUSICIAN.value).assertIsDisplayed()
        composeTestRule.onNodeWithText(BottomNavigationSection.COLLECTOR.value).assertIsDisplayed()
    }

    @Test
    fun validateNavigateToAlbums() {
        clickSectionNavigationBar(composeTestRule, BottomNavigationSection.ALBUM)
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_SCREEN.value).assertIsDisplayed()
    }

    @Test
    fun validateNavigateToArtists() {
        clickSectionNavigationBar(composeTestRule, BottomNavigationSection.MUSICIAN)
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.MUSICIAN_SCREEN.value).assertIsDisplayed()
    }

    @Test
    fun validateNavigateToCollectors() {
        clickSectionNavigationBar(composeTestRule, BottomNavigationSection.COLLECTOR)
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.COLLECTOR_SCREEN.value).assertIsDisplayed()
    }
}