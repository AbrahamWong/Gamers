package com.minotawr.gamers.ui.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.minotawr.gamers.data.base.Result
import com.minotawr.gamers.domain.model.Game
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity<V: ViewBinding>: AppCompatActivity() {

    private var _binding: ViewBinding? = null

    protected open val viewModel: GamersViewModel by viewModel()

    @Suppress("UNCHECKED_CAST")
    protected val binding: V
        get() = _binding as V

    abstract val bindingInflater: (LayoutInflater) -> V

    abstract fun setup()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)

        setup()
    }

    protected fun showError(message: String?) {
        Snackbar.make(
            binding.root,
            message ?: "Something is wrong, please try again later",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    protected fun setGameAsFavorite(game: Game) {
        viewModel.setGameAsFavorite(game).observe(this) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success ->
                    Toast.makeText(this, "${game.name} succesfully added as favorite.", Toast.LENGTH_SHORT).show()

                else ->
                    Toast.makeText(this, "Save as favorite failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    protected fun deleteGameFromFavorite(game: Game) {
        viewModel.deleteGameFromFavorite(game).observe(this) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success ->
                    Toast.makeText(this, "${game.name} succesfully deleted from favorite.", Toast.LENGTH_SHORT).show()

                else ->
                    Toast.makeText(this, "Delete from favorite failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}