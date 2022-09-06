package com.example.tablayout

import android.Manifest
import android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.tablayout.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val viewPager = binding.viewPager1

        val tab = binding.tabLayout
        tab.addTab(tab.newTab().setText("Pictures"))
        tab.addTab(tab.newTab().setText("Videos"))
        tab.addTab(tab.newTab().setText("Music"))
        tab.addTab(tab.newTab().setText("Documents"))

        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let { viewPager.setCurrentItem(it.position) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        }
        )
        binding.viewPager1.adapter =
            FragmentAdapter(supportFragmentManager, lifecycle)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tab.selectTab(tab.getTabAt(position))
            }
        }
        )
        if (hasPermission())
            Toast.makeText(this, "Permission Successful", Toast.LENGTH_SHORT)
        else
            requestStoragePermission()
    }

    private fun hasPermission(): Boolean {

        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager()
        } else {
            val result1: Int =
                ActivityCompat.checkSelfPermission(this, MANAGE_EXTERNAL_STORAGE)
            return ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestStoragePermission() {

        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", applicationContext.packageName))
                startActivityForResult(intent, 1)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, 1)
            }
        } else {
            if (SDK_INT >= Build.VERSION_CODES.Q) {
                Environment.isExternalStorageLegacy()
            }
        }
        val permission = mutableListOf<String>()
        permission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permission.toTypedArray(), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT)
                .show()
        } else if (requestCode == 1) {
            binding.viewPager1.adapter =
                FragmentAdapter(supportFragmentManager, lifecycle)
        } else {
            binding.viewPager1.adapter =
                FragmentAdapter(supportFragmentManager, lifecycle)
        }
    }
}