package com.mbobiosio.currencyconverter.presentation

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.os.BuildCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mbobiosio.currencyconverter.R
import com.mbobiosio.currencyconverter.databinding.ActivityMainBinding
import com.mbobiosio.currencyconverter.util.NavManager
import com.mbobiosio.currencyconverter.util.navigateSafe
import com.mbobiosio.currencyconverter.util.observeOnce
import com.mbobiosio.currencyconverter.util.visibleNavElements
import com.mbobiosio.currencyconverter.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author Mbuodile Obiosio
 * https://linktr.ee/mbobiosio
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val navController get() = findNavController(R.id.navHostContainer)

    private val navManager by lazy {
        NavManager()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashExitAnimation()
        }
        setScreenWait()

        setupNav()

        checkFirstLaunch()
    }

    private fun checkFirstLaunch() {
        viewModel.isFirstLaunch.observeOnce(this) {
            if (it.not()) {
                findNavController(R.id.navHostContainer).navigate(R.id.homeFragment)
            }
        }
    }

    private fun setupNav() = with(binding) {
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnItemReselectedListener { }

        visibleNavElements(navController, bottomNavigationView)

        navManager.setOnNavEvent {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostContainer)
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
            currentFragment?.navigateSafe(it)
        }
    }

    private fun setScreenWait() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean =
                    when {
                        viewModel.getLoadingStatus() -> {
                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            true
                        }
                        else -> {
                            false
                        }
                    }
            }
        )
    }

    @SuppressLint("Recycle")
    @RequiresApi(Build.VERSION_CODES.S)
    private fun splashExitAnimation() {
        if (!BuildCompat.isAtLeastS()) {
            return
        }
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            /** Exit immediately **/
            // splashScreenView.remove()
            /** Exit using animation after particular duration **/
            ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.width.toFloat()
            ).apply {
                interpolator = AnticipateInterpolator()
                duration = 200L
                doOnEnd {
                    splashScreenView.remove()
                }
            }.also {
                it.start()
            }
        }
    }
}
