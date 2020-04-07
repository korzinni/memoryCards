package com.korz.memorycards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.korz.memorycards.databinding.ActivityMainBinding
import com.korz.memorycards.ui.folders.FolderListFragment

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityFoldersContainer, FolderListFragment.newInstance())
            .commit()
    }
}
