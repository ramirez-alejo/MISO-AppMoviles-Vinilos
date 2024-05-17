package com.example.viniloscompose.pageobjects

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.viniloscompose.MainActivity
import com.example.viniloscompose.model.dto.CreateTrackDto
import com.example.viniloscompose.ui.shared.BottomNavigationSection
import com.example.viniloscompose.ui.shared.ContentDescriptions
import com.example.viniloscompose.ui.shared.LoginType


fun clickCollector(composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
    composeTestRule.onNodeWithContentDescription(ContentDescriptions.LOGIN_COLLECTOR.value)
        .performClick()
    composeTestRule.waitUntil(1000) {
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.COLLECTOR_SCREEN.value)
            .isDisplayed()
    }
}

fun clickVisitor(composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>) {
    composeTestRule.onNodeWithContentDescription(ContentDescriptions.LOGIN_VISITOR.value)
        .performClick()
    composeTestRule.waitUntil(1000) {
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_SCREEN.value)
            .isDisplayed()
    }
}

fun login(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    tipo: LoginType
) {
    when (tipo) {
        LoginType.COLLECTOR -> clickCollector(composeTestRule)
        LoginType.VISITOR -> clickVisitor(composeTestRule)
    }
}

fun clickSectionNavigationBar(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    section: BottomNavigationSection
) {
    composeTestRule.onNodeWithText(section.value).performClick()
}

fun searchForMusicianCardByText(composeTestRule: ComposeContentTestRule, text: String) {
    composeTestRule.onAllNodesWithContentDescription(ContentDescriptions.MUSICIAN_CARD.value)
        .assertAny(
            hasAnyChild(hasText(text))
        )
}

fun searchForAllNodesWithDescription(
    composeTestRule: ComposeContentTestRule, contentDescription: ContentDescriptions
): SemanticsNodeInteractionCollection {
    return composeTestRule.onAllNodesWithContentDescription(contentDescription.value)
}

fun searchForAllMusicianCards(composeTestRule: ComposeContentTestRule): SemanticsNodeInteractionCollection {
    return searchForAllNodesWithDescription(composeTestRule, ContentDescriptions.MUSICIAN_CARD)
}

fun searchForFirstMusicianCard(composeTestRule: ComposeContentTestRule): SemanticsNodeInteraction {
    return searchForAllMusicianCards(composeTestRule).onFirst()
}

fun searchForLastMusicianCard(composeTestRule: ComposeContentTestRule): SemanticsNodeInteraction {
    return searchForAllMusicianCards(composeTestRule).onLast()
}

fun addTrack(
    composeTestRule: ComposeContentTestRule,
    track: CreateTrackDto
) {
    composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_TRACK_CREATE.value)
        .performClick()
    composeTestRule.waitUntil {
        composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_FORM_TITLE.value)
            .isDisplayed()
    }
    setTrackName(composeTestRule, track.name)
    setTrackDuration(composeTestRule, track.duration)
    clickContentDescription(composeTestRule, ContentDescriptions.ALBUM_DETAIL_TRACK_FORM_CREATE)
}

fun setTrackName(composeTestRule: ComposeContentTestRule, name: String) {
    composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_TRACK_FORM_NAME.value)
        .performTextInput(name)
    composeTestRule.waitUntil { composeTestRule.onNodeWithText(name).isDisplayed() }
}

fun setTrackDuration(composeTestRule: ComposeContentTestRule, duration: String) {
    composeTestRule.onNodeWithContentDescription(ContentDescriptions.ALBUM_DETAIL_TRACK_FORM_DURATION.value)
        .performClick()
        .performTextInput(duration)
    composeTestRule.waitUntil { composeTestRule.onNodeWithText(duration).isDisplayed() }
}

fun clickContentDescription(
    composeTestRule: ComposeContentTestRule, description: ContentDescriptions
) {
    composeTestRule.onNodeWithContentDescription(description.value).performClick()
}
