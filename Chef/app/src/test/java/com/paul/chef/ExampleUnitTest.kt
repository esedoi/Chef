package com.paul.chef


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.paul.chef.data.Menu
import com.paul.chef.data.User
import com.paul.chef.data.source.DefaultChefRepository
import com.paul.chef.data.source.Result
import com.paul.chef.ui.book.BookFragment
import com.paul.chef.ui.menu.MenuListViewModel
import com.paul.chef.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {


//    @Test
//    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//    }


    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockRepository: DefaultChefRepository

    lateinit var viewModel: MainViewModel

    @Mock
    lateinit var mockApplication: ChefApplication

    @Mock
    lateinit var mockArticlesObserver: Observer<Boolean>

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(testDispatcher)

        ChefApplication.instance = mockApplication
        Mockito.`when`(ChefApplication.instance.isLiveDataDesign()).thenReturn(true)
//        Mockito.`when`(PublisherApplication.instance.isLiveDataDesign()).thenReturn(false)
//        if return false, viewModel will call getArticlesResult() when init()
//        it will change 2 times in verify of getArticles_isNull()
        viewModel = MainViewModel(mockRepository)
        viewModel.newUser.observeForever(mockArticlesObserver)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun getArticles_isNotNull() = testDispatcher.runBlockingTest {

//        Mockito.`when`(repository.getArticles()).thenReturn(Result.Fail(""))

        Mockito.`when`(mockRepository.getUserByEmail("printfootya@gmail.com")).thenReturn(Result.Success(User()))
        viewModel.getUser("printfootya@gmail.com")

        Mockito.verify(mockArticlesObserver, Mockito.times(1)).onChanged(ArgumentMatchers.isNotNull())
//        Mockito.verify(articlesObserver, times(1)).onChanged(ArgumentMatchers.isNull())

    }

    @Test
    fun getArticles_isNull() = testDispatcher.runBlockingTest {


        Mockito.`when`(mockRepository.getUserByEmail("printfootya@gmail.com")).thenReturn(Result.Fail(""))
        viewModel.getUser("printfootya@gmail.com")

        Mockito.verify(mockArticlesObserver, Mockito.times(2)).onChanged(ArgumentMatchers.isNull())

    }


}
