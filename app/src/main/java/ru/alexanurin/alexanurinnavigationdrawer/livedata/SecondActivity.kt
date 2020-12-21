package ru.alexanurin.alexanurinnavigationdrawer.livedata

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
import com.google.android.material.navigation.NavigationView
import ru.alexanurin.alexanurinnavigationdrawer.MyFragment
import ru.alexanurin.alexanurinnavigationdrawer.MyFragment.Companion.createMyFragmentInstance
import ru.alexanurin.alexanurinnavigationdrawer.R
import ru.alexanurin.alexanurinnavigationdrawer.databinding.ActivityMainBinding
import kotlin.random.Random

private const val ITEM_COUNT = 12
private const val TAG = "SecondActivity"

class SecondActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private var mapColor = mutableMapOf<Int, Int>()

    //  экземпляр SharedViewModel.
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  создать фрагмент без параметров при запуске активити.
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, createMyFragmentInstance()).commit()

        setSupportActionBar(binding.appBarView.toolbar)
        title = "LiveData example"
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
            val color =
                Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
            //  mapColor.set(item, color) - аналогичная запись.
            mapColor[item] = color

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val itemNumber = item.itemId
        //  Получаем из mapColor цвет, соответствующий нажатому элементу.
        val color = mapColor[itemNumber]

        if (color != null) {
            //  Передать пару itemNumber to color в контейнер LiveData во ViewModel по нажатию на
            // элемент списка в Drawer.
            sharedViewModel.refreshData(itemNumber to color)
        }

        //  Закрытие DrawerLayout, схлопывается влево.
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}

