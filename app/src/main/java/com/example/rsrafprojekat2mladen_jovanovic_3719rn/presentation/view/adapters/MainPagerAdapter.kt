package com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.R
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.fragments.ListFragment
import com.example.rsrafprojekat2mladen_jovanovic_3719rn.presentation.view.fragments.NotesFragment

class MainPagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 2
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_1 -> ListFragment()
            else -> NotesFragment() // TODO izmeniti u beleskeFragment
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            FRAGMENT_1 -> "Classes"
            else -> "Notes"
        }
    }

}