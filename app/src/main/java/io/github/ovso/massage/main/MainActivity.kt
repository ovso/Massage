package io.github.ovso.massage.main

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import io.github.ovso.massage.R.*
import io.github.ovso.massage.ad.MyAdView
import io.github.ovso.massage.framework.SystemUtils
import io.github.ovso.massage.framework.customview.BaseActivity
import io.github.ovso.massage.main.f_acupoints.AcupointsFragment
import io.github.ovso.massage.main.f_symptom.SymptomFragment
import io.github.ovso.massage.main.f_theme.ThemeFragment.Companion.newInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

@SuppressLint("NonConstantResourceId")
class MainActivity : BaseActivity(), MainPresenter.View {
    private val presenter: MainPresenter = MainPresenterImpl(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(savedInstanceState)
    }

    override fun setListener() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, string.navigation_drawer_open,
            string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navigation_view.setNavigationItemSelectedListener { item: MenuItem ->
            presenter.onNavItemSelected(
                item.itemId
            )
        }
        bottom_navigation_view.setOnNavigationItemSelectedListener { item: MenuItem ->
            val isChecked = bottom_navigation_view.menu.findItem(item.itemId).isChecked
            presenter.onBottomNavItemSelected(item.itemId, isChecked)
        }
        val versionTextView =
            navigation_view.getHeaderView(0).findViewById<TextView>(id.version_textview)
        versionTextView.text = SystemUtils.getVersionName(
            applicationContext
        )
    }

    override fun closeDrawer() {
        drawer_layout!!.closeDrawer(GravityCompat.START)
    }

    override fun showSymptomFragment() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                animator.enter_animation, animator.exit_animation,
                animator.enter_animation, animator.exit_animation
            )
            .replace(id.fragment_container, SymptomFragment.newInstance())
            .commit()
    }

    override fun showThemeFrgament() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                animator.enter_animation, animator.exit_animation,
                animator.enter_animation, animator.exit_animation
            )
            .replace(id.fragment_container, newInstance())
            .commit()
    }

    override fun showAcupoints() {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                animator.enter_animation, animator.exit_animation,
                animator.enter_animation, animator.exit_animation
            )
            .replace(id.fragment_container, AcupointsFragment.newInstance())
            .commit()
    }

    override fun navigateToOssLicensesMenu() {
        startActivity(Intent(this, OssLicensesMenuActivity::class.java))
    }

    override fun getLayoutResId(): Int {
        return layout.activity_main
    }

    override fun onBackPressed() {
        presenter.onBackPressed(drawer_layout.isDrawerOpen(GravityCompat.START))
    }

    override fun showMessage(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showHelpAlert(resId: Int) {
        AlertDialog.Builder(this).setMessage(resId)
            .setPositiveButton(android.R.string.ok) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
            .setNeutralButton(string.review) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
                navigateToStore()
            }
            .show()
    }

    private fun navigateToStore() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(
            "https://play.google.com/store/apps/details?id=io.github.ovso.massage"
        )
        intent.setPackage("com.android.vending")
        startActivity(intent)
    }

    override fun showNativeAdsDialog() {
/*
        NativeAdsDialog2(this)
            .setUnitId(getString(string.ads_native_unit_id))
            .setPositiveButton(string.exit) { dialog, i ->
                dialog.dismiss()
                finish()
            }
            .setNeutralButton(string.review) { dialog, i ->
                dialog.dismiss()
                navigateToStore()
                finish()
            }
            .setTitle(string.do_you_want_to_quit)
            .show()
*/
    }

    override fun showAd() {
        ad_container.addView(MyAdView.getAdmobAdView(applicationContext))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}