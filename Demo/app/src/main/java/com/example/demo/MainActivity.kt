package com.example.demo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.demo.data.AuthVM
import com.example.demo.data.User
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.databinding.HeaderLoginBinding
import com.example.demo.util.setImageBlob

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val nav by lazy { supportFragmentManager.findFragmentById(R.id.host)!!.findNavController() }
    private lateinit var abc: AppBarConfiguration

    // (A) Load view models
    private val auth: AuthVM by viewModels()
    // private val userVM: UserVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // (B) Initialize view models (for early data loading)
        // auth.init()
        // userVM.init()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        abc = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.loginFragment,
                R.id.registerFragment,
                R.id.page1Fragment,
                R.id.page2Fragment,
                R.id.page3Fragment,
                R.id.page4Fragment
            ),
            binding.root
        )

        setupActionBarWithNavController(nav, abc)
        binding.nv.setupWithNavController(nav)

        // -----------------------------------------------------------------------------------------

        // TODO(5): Observe login status -> userLiveData
        auth.getUserLD().observe(this) { user ->
            // TODO(5A): Clear menu + remove header


            // TODO(5B): Inflate menu + header (based on login status)
            if (user == null) {


            }
            else {


            }

            // TODO(5C): Handle logout menu item


            binding.nv.menu.findItem(R.id.exit)?.setOnMenuItemClickListener {
                finishAndRemoveTask()
                true
            }
        }

        // TODO(8): Auto login -> auth.loginFromPreferences(...)


    }

    private fun setHeader(user: User) {
        val h = binding.nv.getHeaderView(0)
        val b = HeaderLoginBinding.bind(h)
        b.imgPhoto.setImageBlob(user.photo)
        b.txtName.text  = user.name
        b.txtEmail.text = user.email
    }

    private fun logout(): Boolean {
        // TODO(4): Logout -> auth.logout(...)
        //          Clear navigation backstack


        binding.root.close()
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp(abc)
    }

}