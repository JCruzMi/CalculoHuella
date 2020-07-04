package com.example.huellaco

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidnetworking.AndroidNetworking

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        AndroidNetworking.initialize(applicationContext)

        //setup

        val bundle = intent.extras
        val correo = bundle?.getString("correo").toString()
        val nombre= bundle?.getString("nombre").toString()
        val rol= bundle?.getString("rol").toString()

        //se envian a setup(corre,nombre,etc..)
        setup(nombre,correo,rol)

        // Guardado de datos para persistencia

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("correo",correo)
        prefs.putString("nombre",nombre)
        prefs.putString("rol",rol)
        prefs.apply()

    }
    private fun setup(nombre:String,correo:String,rol:String){

        //actualizarVista(nombre, correo, rol)
        //añadir ir a
    }
}
