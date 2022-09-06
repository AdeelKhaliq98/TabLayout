package com.example.tablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.Fragments.Documents
import com.example.Fragments.Music
import com.example.Fragments.Pictures
import com.example.Fragments.Videos

class FragmentAdapter(fragment: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragment, lifecycle) {
    override fun getItemCount(): Int {
        return 4;
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 1) {
            return Videos()
        } else if (position == 2) {
            return Music()
        } else if (position == 3) {
            return Documents()
        } else
            return Pictures()
    }
}