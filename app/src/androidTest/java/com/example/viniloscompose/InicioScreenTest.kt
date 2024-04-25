package com.example.viniloscompose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.viniloscompose.pageobjects.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InicioScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun openLoginScreenTest() {
        composeTestRule.waitUntil(30000) {
            composeTestRule.onNodeWithText("Bienvenido").isDisplayed()
        }
        composeTestRule.onNodeWithText("Visitante").assertIsDisplayed()
        composeTestRule.onNodeWithText("Coleccionista").assertIsDisplayed()
    }

    @Test
    fun clickOnVisitor() {
        composeTestRule.waitUntil(30000) {
            composeTestRule.onNodeWithText("Bienvenido").isDisplayed()
        }
        clickVisitor(composeTestRule)
        composeTestRule.onNodeWithText("Bienvenido Albumes").assertIsDisplayed()
    }

    @Test
    fun clickOnCollector() {
        composeTestRule.waitUntil(30000) {
            composeTestRule.onNodeWithText("Bienvenido").isDisplayed()
        }
        clickCollector(composeTestRule)
        composeTestRule.onNodeWithText("Bienvenido Coleccionistas").assertIsDisplayed()
    }
}