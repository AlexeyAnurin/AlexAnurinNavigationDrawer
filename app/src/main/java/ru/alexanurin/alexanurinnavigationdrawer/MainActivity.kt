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

    private fun setDrawerToggle() {
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

    //  Динамическое создание элементов. Вызывается в OnCreate().
    private fun addItem(count: Int) {
        val menu: Menu = binding.navView.menu
        for (item in 1..count) {
            //  Создаём динамически нужное количество элементов меню.
            menu.add(Menu.NONE, item, Menu.NONE, "Item $item")
            //  устанавливаем им при создании случайный цвет для каждого элемента.
            val color = argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            //  mapColor.set(item, color) - аналогичная запись.
            mapColor[item] = color

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val itemNumber = item.itemId
        //  Получаем из mapColor цвет, соответствующий нажатому элементу.
        val color = mapColor[itemNumber]

        if (color != null) {
            //  Создаём экземпляр фрагмета через вызов метода из компаньон-объекта в классе фрагмента,
            //  передаём аргументов цвет, соответствующий нажатому элементу.
            val fragInst = MyFragment.createMyFragmentInstance(itemNumber, color)
            //  Используется один фрагмент, поэтому он будет перетираться через replace().
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, fragInst).commit()
        }

        //  Закрытие DrawerLayout, схлопывается влево.
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}