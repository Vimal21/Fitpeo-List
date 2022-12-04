package com.fitpeo.mvvmhilt.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fitpeo.mvvmhilt.config.Constants

/**
 * Base fragment
 *
 * @constructor Create Base fragment
 */
open class BaseFragment : Fragment() {

    /**
     * Apply the viewModelProviders to the particular viewModel class
     *
     * @param T
     * @param viewModelClass
     */
    fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
        ViewModelProvider(this).get(viewModelClass)

    /**
     * Display error message to the user using toast
     *
     * @param message  the error message
     */
    fun displayError(message: String?) {
        if (message.isNullOrEmpty()) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), Constants.UNKNOWN_ERROR, Toast.LENGTH_LONG).show()
        }
    }
}