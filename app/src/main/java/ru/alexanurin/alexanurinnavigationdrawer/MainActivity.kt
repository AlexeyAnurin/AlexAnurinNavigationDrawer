package ru.alexanurin.alexanurinnavigationdrawer

import android.graphics.Color.*
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import ru.alexanurin.alexanurinnavigationdrawer.databinding.ActivityMainBinding
import kotlin.random.Random

//  кол-во элементов меню
private const val ITEM_COUNT = 12
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //  view binding
    private lateinit var binding: ActivityMainBinding

    private var mapColor = mutableMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarView.toolbar)
        //  кнопка для открытия Drawer
        setDrawerToggle()

        binding.navView.setNavigationItemSelectedListener(this)

        addItem(ITEM_COUNT)
        binding.navView.invalidate()
    }

    private fun setDrawerToggle(){
        val drawerToggle = ActionBarDrawerToggle(
            this,                  //  host Activity
            binding.drawerLayout,         //  instance DrawerLayout
            binding.appBarView.toolbar,    //  toolbar же
            R.string.open_drawer,  //   open drawer
            R.string.close_drawer  //  close drawer
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    //  Динамическое создание элементов.
    private fun addItem(count: Int) {
        val menu: Menu = binding.navView.menu
        for (item in 1..count) {
            //  Создаём динамически нужное количество элементов меню.
            menu.add(Menu.NONE, item, Menu.NONE, "Item $item")
            //  устанавливаем им при создании случайный цвет.
            val color = argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            mapColor[item] = color
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val color = mapColor[item.itemId]
        if (color != null) {
            binding.drawerLayout.setBackgroundColor(color)

        }
        binding.textView.text = item.title

        //  Закрытие DrawerLayout, схлопывается влево.
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState")
    }

}