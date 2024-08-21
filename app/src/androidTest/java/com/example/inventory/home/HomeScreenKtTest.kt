package com.example.inventory.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.IdlingRegistry
import com.example.inventory.MainCoroutineRule
import com.example.inventory.data.Item
import com.example.inventory.ui.home.HomeScreen
import com.example.inventory.ui.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random


class HomeScreenKtTest {

    @get:Rule
    val composeTestRule =  createComposeRule()

    private lateinit var viewModel: HomeViewModel
    private val fakeItemRepository = FakeItemRepository()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(coroutineRule.testDispatcher)

    private val navigateToItemEntry: () -> Unit = {
        println("\n\n\tnavigateToItemEntry clicked")
        println("\t\tnavigateToItemEntry TEST thread: ${Thread.currentThread().name}")

        val numId = Random.nextInt()
        testScope.launch {
            println("\t\tnavigateToItemEntry calling insert")
            println("\t\tnavigateToItemEntry coroutineScope thread: ${Thread.currentThread().name}")
            fakeItemRepository.insertItem(
                Item(
                    id = numId,
                    name = "moo",
                    price = 1.0,
                    quantity = 1
                )
            )
            println("\n\n\t\tnavigateToItemEntry insert complete")
        }
        println("\n\n\tnavigateToItemEntry clicked EXIT")
    }
    private val navigateToItemUpdate: (Int) -> Unit = { _ -> }

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().resources.forEach {
            if (it.name == "Compose-Espresso link") {
                IdlingRegistry.getInstance().unregister(it)
            }
        }

        viewModel = HomeViewModel(fakeItemRepository)
        composeTestRule.setContent {
            HomeScreen(
                viewModel = viewModel,
                navigateToItemEntry = navigateToItemEntry,
                navigateToItemUpdate = navigateToItemUpdate
            )
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testHomeScreen_items_listVisible() {
        composeTestRule
            .onNodeWithText("moo") // "Oops! No items in the inventory. Tap + to add.").assertIsDisplayed()
            .assertExists()
    }

    @Test
    fun testHomeScreen_emptyList_listNotVisible() {
        // list not visible if there are no items
        composeTestRule
            .onNodeWithTag("inventory_list")
            .assertDoesNotExist()
    }

    @Test
    fun testHomeScreen_emptyList_addButtonVisible() {
        composeTestRule
            .onNodeWithTag("fab")
            .assertExists()
    }

    @Test
    fun testHomeScreen_emptyList_addButtonClicked() {
        println("testHomeScreen_emptyList_addButtonClicked TEST thread: ${Thread.currentThread().name}")
        composeTestRule.onNodeWithTag("fab").performClick()
//        composeTestRule.waitForIdle()
//        composeTestRule
//            .onNodeWithTag("fab")
//            .assertDoesNotExist()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
//    fun testHomeScreen_oneItem_listVisible() = runTest {
    fun testHomeScreen_oneItem_listVisible()  {
        println("\ntestHomeScreen_oneItem_listVisible TEST thread: ${Thread.currentThread().name}")

        composeTestRule.onNodeWithTag("fab").performClick()
        composeTestRule.mainClock.advanceTimeBy(1000)

        // list visible if there is one item
        composeTestRule
            .onNodeWithTag("inventory_list")
            .onChildren()
            .assertCountEquals(1)
//        composeTestRule.waitUntil(timeoutMillis = 5000L) {
//        }
//        coroutineRule.testDispatcher.scheduler.advanceUntilIdle()
//        composeTestRule.waitForIdle()

    }

    @Test
    fun testHomeScreen_fiveItems_listVisible()  {
        println("\ntestHomeScreen_oneItem_listVisible TEST thread: ${Thread.currentThread().name}")

        composeTestRule.onNodeWithTag("fab").performClick()
        composeTestRule.onNodeWithTag("fab").performClick()
        composeTestRule.onNodeWithTag("fab").performClick()
        composeTestRule.onNodeWithTag("fab").performClick()
        composeTestRule.onNodeWithTag("fab").performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        // list visible if there is one item
        composeTestRule
            .onNodeWithTag("inventory_list")
            .onChildren()
            .assertCountEquals(5)
//        composeTestRule.waitUntil(timeoutMillis = 5000L) {
//        }
//        coroutineRule.testDispatcher.scheduler.advanceUntilIdle()
//        composeTestRule.waitForIdle()

    }
}