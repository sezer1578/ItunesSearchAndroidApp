package com.ozaltun.myitunesapp.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.ozaltun.myitunesapp.R

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor(getString(R.color.orange))))
        navController = findNavController(R.id.fragmentContainerView)
        setupActionBarWithNavController(navController)

    }
    override fun onBackPressed() {
        if (navController.popBackStack().not()) {
            val builder = AlertDialog.Builder(this)
                .setTitle(getString(R.string.exitApp))
                .setMessage(getString(R.string.exitQuestiom))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getString(R.string.yes)) { dialogInterface, _ ->
                    dialogInterface.cancel()
                    android.os.Process.killProcess(android.os.Process.myPid())
                }
                .setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ ->
                    navController.graph = navController.navInflater.inflate(R.navigation.nav_graph)
                }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}