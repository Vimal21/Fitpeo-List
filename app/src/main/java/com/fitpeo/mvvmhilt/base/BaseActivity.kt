package com.fitpeo.mvvmhilt.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Base activity
 *
 * @constructor Create empty Base activity
 */
open class BaseActivity : AppCompatActivity() {
    /**
     * Replace fragment in activity
     *
     * @param fragment
     * @param frameId
     */
    fun replaceFragmentInActivity(fragment: Fragment, frameId: Int) {
        val tag = fragment.javaClass.name

        //If selected fragment already opened - do nothing
        val fragmentByTag = supportFragmentManager.findFragmentByTag(tag)
        if (fragmentByTag != null) {
            return
        }
        supportFragmentManager.transact {
            replace(frameId, fragment)
        }
    }


    /**
     * Fragment Manager Transact
     *
     * @param action Fragment action
     * @receiver
     */
    private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
        beginTransaction().apply {
            action()
        }.commit()
    }
}