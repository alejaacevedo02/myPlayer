package com.example.myplayeraleja

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.myplayeraleja.data.Filter
import com.example.myplayeraleja.data.MediaItem
import com.example.myplayeraleja.data.MediaItem.*
import com.example.myplayeraleja.ui.Event
import com.example.myplayeraleja.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class MainActivityTest {
    private lateinit var mainViewModel: MainViewModel
    private val fakeMediaProvider = FakeMediaProviderImpl()
   // private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        //Dispatchers.setMain(testDispatcher)
        mainViewModel = MainViewModel(fakeMediaProvider,coroutinesTestRule.testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
       // testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test everything Works`() {
        assert(true)
    }

    @Test
    fun `progress is set visible when progressVisible value changes`() =
        runTest {
            val observer = mock<Observer<Boolean>>()
            mainViewModel.progressVisible.observeForever(observer)
            mainViewModel.onFilterClicked()
            verify(observer).onChanged(true)
        }

    @Test
    fun `navigates to detail when onMediaItemClicked`() = runTest {
        val observer = mock<Observer<Event<Int>>>()
        mainViewModel.navigateToDetail.observeForever(observer)
        mainViewModel.onMediaItemClicked(MediaItem(1, "", "", Type.PHOTO))
        verify(observer).onChanged(Event(1))
    }

    @Test
    fun `updates items onFilterClicked`() {
        val observer = mock<Observer<List<MediaItem>>>()
        mainViewModel.items.observeForever(observer)
        mainViewModel.onFilterClicked()
        verify(observer).onChanged(fakeMediaProvider.getItems())
    }
}