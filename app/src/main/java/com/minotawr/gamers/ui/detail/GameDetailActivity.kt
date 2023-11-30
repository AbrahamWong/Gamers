package com.minotawr.gamers.ui.detail

import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.minotawr.gamers.R
import com.minotawr.gamers.data.base.Result
import com.minotawr.gamers.databinding.ActivityGameDetailBinding
import com.minotawr.gamers.domain.model.Game
import com.minotawr.gamers.ui.base.BaseActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class GameDetailActivity : BaseActivity<ActivityGameDetailBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityGameDetailBinding
        get() = ActivityGameDetailBinding::inflate

    override val viewModel: GameDetailViewModel by viewModel()

    companion object {
        const val RESULT_CHANGE = 99

        private const val EXTRA_ID = "id"

        fun open(
            activity: FragmentActivity,
            id: Int?,
            resultLauncher: ActivityResultLauncher<Intent>? = null
        ) {
            val intent = Intent(activity, GameDetailActivity::class.java)

            if (id != null)
                intent.putExtra(EXTRA_ID, id)

            if (resultLauncher != null)
                resultLauncher.launch(intent)
            else ActivityCompat.startActivity(activity, intent, null)
        }
    }

    override fun setup() {
        setupObserver()
        setupListener()

        loadData()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.isFavorite.collect { isFavorite ->
                changeFloatingDrawable(isFavorite)
                setResult(RESULT_CHANGE)
            }
        }
    }

    private fun setupListener() {
        binding.fabFavorite.setOnClickListener {
            val newState = !viewModel.isFavorite.value
            viewModel.isFavorite.value = newState

            val game = viewModel.game
            if (game != null) {
                if (newState)
                    setGameAsFavorite(game)
                else deleteGameFromFavorite(game)
            }
        }
    }

    private fun loadData() {
        val id = intent.getIntExtra(EXTRA_ID, -1)
        if (id < 0) {
            finish()
            return
        }

        getGame(id)
    }

    private fun getGame(id: Int) {
        viewModel.getGame(id).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showShimmer()
                }
                is Result.Success -> {
                    hideShimmer()

                    val game = result.data
                    if (game != null) {
                        viewModel.game = game
                        setGame(game)
                    }
                }
                else -> {
                    hideShimmer()
                    showError(result.message)
                }
            }
        }
    }

    private fun setGame(game: Game) {
        with (binding) {
            Glide.with(this@GameDetailActivity)
                .load(game.backgroundImage)
                .into(ivBackground)
            ivBackground.contentDescription = "Background image of ${game.name}"

            tvTitle.text = game.name
            tvDescription.text = HtmlCompat.fromHtml("<b>${game.description}</b>", HtmlCompat.FROM_HTML_MODE_LEGACY)

            tvRating.text =
                String.format(Locale.getDefault(), "%.2f out of %d", game.rating, game.ratingTop)
            tvGenres.text = game.genres?.map { it.name }?.joinToString()
            tvTags.text = game.tags?.map { it.name }?.joinToString()

            viewModel.isFavorite.value = game.isFavorite
        }
    }

    private fun changeFloatingDrawable(isFavorite: Boolean) {
        binding.fabFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                this@GameDetailActivity,
                if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        )
    }

    private fun showShimmer() {
        binding.clContent.isVisible = false
        binding.skeleton.isVisible = true
    }

    private fun hideShimmer() {
        binding.clContent.isVisible = true
        binding.skeleton.isVisible = false
    }
}