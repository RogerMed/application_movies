package com.example.moviesapplication.view.adapter.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.moviesapplication.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val buttonCriarConta = findViewById<Button>(R.id.button_entrar_tela_login)

        buttonCriarConta.setOnClickListener {
            val abrirTelaCriarConta = Intent(this, TelaCadastroActivity::class.java)
            startActivity(abrirTelaCriarConta)
        }

        val buttunLogin = findViewById<Button>(R.id.button_login_entrar)
        val email = findViewById<EditText>(R.id.edit_email)
        val senha = findViewById<EditText>(R.id.edit_senha)
        buttunLogin.setOnClickListener {
            if (email.text.trim().toString().isNotEmpty() || senha.text.trim().toString()
                    .isNotEmpty()) {
                signInUser(email.text.toString(), senha.text.toString())
            } else {
                Toast.makeText(this, "Insira seu E-mail e Senha.", Toast.LENGTH_LONG).show()
            }

        }


    }

    fun signInUser(email: String, senha: String){
     auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener { task ->
         if(task.isSuccessful){
             val intent = Intent(this , MovieActivity:: class.java)
             startActivity(intent)
         }
         else{
          Toast.makeText(this, "E-mail ou Senha errados!", Toast.LENGTH_LONG).show()
         }
     }

    }


}