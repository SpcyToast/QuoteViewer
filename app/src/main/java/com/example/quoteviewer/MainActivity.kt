package com.example.quoteviewer

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.quoteviewer.ui.quotes.QuoteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        showFragment()
    }

    private fun showFragment() {
        val quoteFragment = QuoteFragment()
        // Show QuoteFragment inside MainActivity, to replace 'rootContentPanel'
        supportFragmentManager.commit {
            replace(R.id.rootContentPanel, quoteFragment)
        }
    }

}
