package com.example.quoteviewer

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
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
        supportFragmentManager.beginTransaction()
            .replace(R.id.rootContentPanel, quoteFragment)
            .commit()
    }

}
