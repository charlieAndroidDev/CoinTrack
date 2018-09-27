package com.cniekirk.coinbook.activites

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.transition.*
import com.cniekirk.coinbook.R
import com.cniekirk.coinbook.core.extensions.inTransaction
import com.cniekirk.coinbook.ui.BottomNavigationDrawerFragment
import com.cniekirk.coinbook.ui.DashboardFragment
import com.cniekirk.coinbook.ui.TestFragment
import com.cniekirk.coinbook.utils.CustomInterpolator
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var fragmentManager : FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fragmentManager = supportFragmentManager
        initDashboard()

        setSupportActionBar(bottom_app_bar)
        add_transaction.setOnClickListener {
            // Navigate to new fragment
        }
    }

    private fun initDashboard() {
        val dashboardFragment = DashboardFragment.newInstance()
        fragmentManager.inTransaction {
            replace(R.id.main_navigation_fragment, dashboardFragment)
        }
    }

    fun nextFragmentAnim() {

        if(isDestroyed) {
            return
        }

        val previous = fragmentManager.findFragmentById(R.id.main_navigation_fragment)
        val next = TestFragment.newInstance()

        /*val enter = Slide()
        enter.startDelay = 350
        enter.duration = 300
        next.enterTransition = enter*/

        val interpolator = CustomInterpolator(350f)
        val set = TransitionSet()
        set.addTransition(ChangeBounds().setInterpolator(interpolator).setDuration(350))
        set.addTransition(ChangeTransform().setInterpolator(interpolator).setDuration(350))
        next.sharedElementEnterTransition = set

        val sharedView = previous?.view?.findViewById<View>(R.id.anim_view)

        fragmentManager.inTransaction {
            addSharedElement(sharedView!!, sharedView.transitionName)
            addToBackStack(null)
            replace(R.id.main_navigation_fragment, next)
        }

        bottom_app_bar.visibility = View.GONE
        add_transaction.hide()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            android.R.id.home -> {
                val bottomNavDrawerFragment = BottomNavigationDrawerFragment()
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
