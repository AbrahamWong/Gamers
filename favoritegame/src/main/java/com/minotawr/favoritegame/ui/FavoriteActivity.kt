package com.minotawr.favoritegame.ui

import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.minotawr.favoritegame.databinding.ActivityFavoriteBinding
import com.minotawr.favoritegame.di.favoriteViewModelModule
import com.minotawr.gamers.data.base.Result
import com.minotawr.gamers.domain.model.Game
import com.minotawr.gamers.ui.base.BaseActivity
import com.minotawr.gamers.ui.detail.GameDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityFavoriteBinding
        get() = ActivityFavoriteBinding::inflate

    private val favoriteAdapter = FavoriteAdapter()
    override val viewModel: FavoriteViewModel by viewModel()

    private val reloadLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == GameDetailActivity.RESULT_CHANGE) {
                setResult(GameDetailActivity.RESULT_CHANGE)
                loadData()
            }
        }

    override fun setup() {
        loadKoinModules(favoriteViewModelModule)

        setupView()
        setupListener()

        loadData()
    }

    private fun setupView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = favoriteAdapter
        }
    }

    private fun setupListener() {
        favoriteAdapter.delegate = object : FavoriteAdapter.FavoriteListDelegate {
            override fun onItemClick(item: Game) {
                GameDetailActivity.open(
                    this@FavoriteActivity,
                    item.id,
                    reloadLauncher
                )
            }
        }
    }

    private fun loadData() {
        viewModel.getFavoriteGame().observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    // do nothing
                }

                is Result.Unauthorized -> {
                    // do nothing
                }

                is Result.Failed -> {
                    Snackbar.make(
                        binding.root,
                        result.message ?: "Something is wrong, please try again later",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Result.Success -> {
                    binding.tvEmpty.isVisible = result.data.isNullOrEmpty()

                    result.data?.let { games ->
                        favoriteAdapter.setList(games)
                    }
                }
            }
        }
    }

}