package com.fitpeo.mvvmhilt.photo.viewModel

import android.text.TextUtils
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fitpeo.mvvmhilt.base.network.model.ErrorModel
import com.fitpeo.mvvmhilt.photo.repository.PhotoListRepository
import com.fitpeo.mvvmhilt.photo.service.PhotoListApi
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(TextUtils::class)
class PhotoListViewModelTest {
    private lateinit var photoListViewModel: PhotoListViewModel

    @Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val photoListApi = Mockito.mock(PhotoListApi::class.java)
        Mockito.`when`(photoListApi.get(1, 100)).thenReturn(null)
        val photoListRepository = PhotoListRepository(photoListApi)
        photoListViewModel = PhotoListViewModel(photoListRepository)
        PowerMockito.mockStatic(TextUtils::class.java)
        PowerMockito.`when`(TextUtils.isEmpty(ArgumentMatchers.any(CharSequence::class.java))).thenAnswer { invocation ->
            val a = invocation.arguments[0] as? CharSequence
            a?.isEmpty() ?: true
        }
    }

    @Test
    fun getErrorModelMessagePassTest(){
        val errorModelMessage : String = ErrorModel().error.message
        photoListViewModel.processError(ErrorModel(), null)
        photoListViewModel.errorMessage.value.equals(errorModelMessage).let { assertTrue(it) }
    }

    @Test
    fun getErrorModelMessageFailTest(){
        val errorModelMessage : String = "Error"
        photoListViewModel.processError(ErrorModel(), null)
        photoListViewModel.errorMessage.value.equals(errorModelMessage).let { assertFalse(it) }
    }

    @Test
    fun getThrowableErrorMessagePassTest() {
        val errorModelMessage : String? = NullPointerException().localizedMessage
        photoListViewModel.processError(null, NullPointerException())
        photoListViewModel.errorMessage.value.equals(errorModelMessage).let { assertTrue(it) }
    }

    @Test
    fun getThrowableErrorMessageFailTest() {
        val errorModelMessage : String? = NullPointerException().localizedMessage
        photoListViewModel.processError(null, ArrayIndexOutOfBoundsException())
        photoListViewModel.errorMessage.value.equals(errorModelMessage).let { assertTrue(it) }
    }

    @Test
    fun checkErrorModelMessageRetrieveTest() {
        val errorModelMessage : String = ErrorModel().error.message
        photoListViewModel.processError(ErrorModel(), NullPointerException())
        photoListViewModel.errorMessage.value.equals(errorModelMessage).let { assertTrue(it) }
    }
}