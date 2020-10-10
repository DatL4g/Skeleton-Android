package de.datlag.skeleton

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.snackbar.Snackbar
import de.datlag.skeleton.adapter.RecyclerAdapter
import de.datlag.skeleton.commons.copyOf
import de.datlag.skeleton.commons.mutableCopyOf
import de.datlag.skeleton.data.RecyclerItem
import de.datlag.skeleton.databinding.ActivityMainBinding
import de.datlag.skeleton.viewmodel.MainViewModel

// include for Hilt
// @AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = RecyclerAdapter()
    private val viewModel: MainViewModel by viewModels()

    private val bottomNavigationListener: (item: MenuItem) -> Unit = {
        when (it.itemId) {
            R.id.activityBottomDelete -> {
                viewModel.listData.value = mutableListOf()
            }
            R.id.activityBottomReload -> {
                viewModel.reload()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        viewModel.listData.observe(this@MainActivity) {
            adapter.submitList(it.copyOf())
        }
    }

    private fun initViews() = with(binding) {
        setSupportActionBar(toolbar)
        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false

        adapter.setOnClickListener { _, position ->
            Snackbar.make(root, "Click: $position", Snackbar.LENGTH_SHORT).setAnchorView(bottomAppBar).show()
        }

        adapter.setOnLongClickListener { _, position ->
            Snackbar.make(root, "LongClick: $position", Snackbar.LENGTH_SHORT).setAnchorView(bottomAppBar).show()
            true
        }

        fab.setOnClickListener {
            viewModel.listData.value?.let {
                val list = it.mutableCopyOf()
                list.add(RecyclerItem("Added ${list.size}"))
                viewModel.listData.value = list
            }
        }

        bottomNavigation.setOnNavigationItemReselectedListener {
            bottomNavigationListener.invoke(it)
        }

        bottomNavigation.setOnNavigationItemSelectedListener {
            bottomNavigationListener.invoke(it)
            true
        }
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
