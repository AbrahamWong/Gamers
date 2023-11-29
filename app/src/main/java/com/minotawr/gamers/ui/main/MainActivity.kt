package com.minotawr.gamers.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minotawr.gamers.data.base.Result
import com.minotawr.gamers.databinding.ActivityMainBinding
import com.minotawr.gamers.domain.model.Game
import com.minotawr.gamers.ui.base.BaseActivity
import com.minotawr.gamers.ui.detail.GameDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val listAdapter = MainAdapter()
    override val viewModel: MainViewModel by viewModel()

    private lateinit var listLayoutManager: LinearLayoutManager

    private val reloadLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == GameDetailActivity.RESULT_CHANGE) {
                viewModel.page = 1
                loadData()
            }
        }

    override fun setup() {
        setupView()
        setupListener()

        loadData()
    }

    private fun setupView() {
        binding.recyclerView.apply {
            listLayoutManager = LinearLayoutManager(this@MainActivity)
            layoutManager = listLayoutManager
            adapter = listAdapter
        }
    }

    private fun setupListener() {
        binding.run {
            srlHome.setOnRefreshListener {
                viewModel.page = 1
                srlHome.isRefreshing = false
                getAllGames(1, true)
            }

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (listLayoutManager.findLastVisibleItemPosition() == listAdapter.itemCount - 1 &&
                        newState == RecyclerView.SCROLL_STATE_IDLE
                    ) {
                        val nextPage = viewModel.page + 1
                        viewModel.page = nextPage
                        getAllGames(nextPage, false)
                    }
                }
            })

            floatingActionButton.setOnClickListener {
                try {
                    reloadLauncher.launch(
                        Intent(
                            this@MainActivity,
                            Class.forName("com.minotawr.favoritegame.ui.FavoriteActivity")
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@MainActivity, "Module not available", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        listAdapter.delegate = object : MainListDelegate {
            override fun onItemClick(item: Game) {
                GameDetailActivity.open(
                    this@MainActivity,
                    item.id,
                    reloadLauncher
                )
            }

            override fun onFavorite(item: Game) {
                if (item.isFavorite) {
                    setGameAsFavorite(item)
                } else deleteGameFromFavorite(item)
            }
        }
    }

    private fun loadData() {
        getAllGames(viewModel.page, true)
    }

    private fun getAllGames(page: Int, reload: Boolean) {
        viewModel.getGameList(page).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showShimmer()
                }

                is Result.Unauthorized -> {
                    hideShimmer()
                    showError(result.message)
                }

                is Result.Failed -> {
                    hideShimmer()
                    showError(result.message)
                }

                is Result.Success -> {
                    hideShimmer()
                    binding.skeleton.isVisible = false
                    result.data?.let { games ->
                        if (reload)
                            listAdapter.setList(games)
                        else listAdapter.addList(games)
                    }
                }
            }
        }
    }

    private fun showShimmer() {
        binding.recyclerView.isVisible = false
        binding.skeleton.isVisible = true
    }

    private fun hideShimmer() {
        binding.recyclerView.isVisible = true
        binding.skeleton.isVisible = false
    }
}