package com.sv.edu.ufg.amb.fis.almacenamientoexternointerno

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private  val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 101
    lateinit var boton: Button
    lateinit var texto: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boton = findViewById<Button>(R.id.btn_guardar)
        texto = findViewById<EditText>(R.id.txt_data)

        escrituraArchivosAlmacenamietoInterno(this, "archivo_almacenamiento_interno.txt", "este es un contenido de almacenamiento interno.")
        escrituraArchivosAlmacenamientoExterno(this, "archivo_almacenamiento_externo.txt", "este es un contenido de almacenamiento externo.")



        boton.setOnClickListener {
            escrituraArchivosAlmacenamietoInterno(this, "archivo_con_valor_campo_texto.txt", texto.text.toString())
        }

    }

    fun escrituraArchivosAlmacenamietoInterno(
        context: Context,
        fileName: String,
        content: String
    ) {
        val filePath = context.filesDir.absolutePath+"/$fileName"
        val file = File(filePath)

        try {
            file.writeText(content)
            Log.v("ESCRITURA EN ALMACENAMIENTO LOCAL", "RUTA: '${filePath}'")
        } catch (e: Exception) {
            e.printStackTrace()
        }



    }

    fun escrituraArchivosAlmacenamientoExterno(
        context: Context,
        fileName: String,
        content: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val filePath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)!!.absolutePath+"/$fileName"
            val file = File(filePath)

            try {
                file.writeText(content)
                Log.v("ESCRITURA EN ALMACENAMIENTO EXTERNO", "RUTA: '${filePath}'")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            val filePath = context.getExternalFilesDir(null)!!.absolutePath+"/$fileName"
            val file = File(filePath)

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                try {
                    file.writeText(content)
                    Log.v("ESCRITURA EN ALMACENAMIENTO EXTERNO", "RUTA: '${filePath}'")
                } catch (e: Exception) {
                    e.printStackTrace()            }
            } else {
                ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
            }
        }
    }
}