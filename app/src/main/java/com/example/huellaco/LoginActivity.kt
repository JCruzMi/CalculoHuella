package com.example.huellaco

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        AndroidNetworking.initialize(applicationContext)

        sesion()
        settup()
    }

    private fun sesion(){
        //Saber si ya inicio sesion
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val correo = prefs.getString("correo","").toString()
        val nombre = prefs.getString("nombre","").toString()
        val rol = prefs.getString("rol","").toString()

        if (correo.isNotEmpty()){
            authlayout.visibility = View.INVISIBLE
            irAHome(nombre,correo,rol)
        }

    }

    private fun settup(){

        btniniciar.setOnClickListener {
            if (txtcorreo.text.isNotEmpty() && txtcontrasena.text.isNotEmpty()) {
                val datos = HashMap<String, String>()
                datos.put("correo",txtcorreo.text.toString())
                datos.put("contrasena",txtcontrasena.text.toString())

                val jdatos = JSONObject(datos as Map<*, *>)

                AndroidNetworking.post("url").addJSONObjectBody(jdatos)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {

                            val validar = response.get("respuesta").toString()
                            if(validar == "200"){
                                println("valido")
                                val usuario = response.getJSONObject("data")

                                //llama irAHome(valores que traen la consulta, es decir: nombreusuario, correo)

                                irAHome(usuario.getString("nombre"),usuario.getString("correo"),usuario.getString("rol"))
                            }else{
                                AlertPop()
                            }

                        }
                        override fun onError(error: ANError) {
                            //
                        }
                    })
            }
        }
    }

    private fun AlertPop(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error al autentificar el usuario")
        builder.setPositiveButton("Acceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    //recibe todos los valores necesarios con metodo get del webservice
    private fun irAHome(nombre:String,correo:String,rol:String){

        val homeIntent = Intent(this, MainActivity::class.java).apply {
            //pasar valores a la otra actividad
            //putExtra()
            putExtra("nombre",nombre)
            putExtra("correo",correo)
            putExtra("rol",rol)
        }
        startActivity(homeIntent)

    }

}
