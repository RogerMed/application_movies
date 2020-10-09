package com.example.moviesapplication.view.adapter.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.moviesapplication.R
import com.example.moviesapplication.model.Pessoa
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class TelaCadastroActivity : AppCompatActivity() {

    var nDatabaseReference: DatabaseReference? = null
    var mDataBase: FirebaseDatabase? = null
    var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val buttonCadastro = findViewById<Button>(R.id.button_cadastro_criar_conta)

        val name = findViewById<EditText>(R.id.cadastro_edit_Nome)
        val email = findViewById<EditText>(R.id.cadastro_edit_email)
        val senha = findViewById<EditText>(R.id.cadastro_edit_senha)
        val senhaConfirmacao = findViewById<EditText>(R.id.cadastro_edit_senha_confirmacao)


        initializeFirebase()

        buttonCadastro.setOnClickListener {
            createNewAccount(name.text.toString(), email.text.toString(), senha.text.toString(), senhaConfirmacao.text.toString())

        }
    }




    @SuppressLint("SimpleDateFormat")
    private fun createNewAccount(name: String, email: String, senha: String, senhaConfirmacao: String) {

        if(name == "" || email == "" || senha == "" || senhaConfirmacao == ""){
            Toast.makeText(applicationContext,"Preencha todos campos.",Toast.LENGTH_LONG).show()
        }
         else if(senha != senhaConfirmacao){
            Toast.makeText(applicationContext,"Senha de confirmção diferente da senha. COLOQUE A MESMA SENHA NOS DOIS CAMPOS",Toast.LENGTH_LONG).show()
        }
        else if(senha.count() < 6){
          val toast =   Toast.makeText(applicationContext,"Digite uma senha maior Do que 6 digitos",Toast.LENGTH_LONG)
            toast.show()
        }
      else{

            val id = UUID.randomUUID().toString()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val date = sdf.format(Date())
          val pessoa = Pessoa(name,email,date)
            mAuth!!.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext,"Conta criada com sucesso.",Toast.LENGTH_LONG).show()

                        verifyEmail()


                    }

                }

            val currentUserDb = nDatabaseReference!!.child(id)
            currentUserDb.setValue(pessoa)
            updateUserInfoAndUi()
        }

    }

    fun initializeFirebase() {
        mDataBase = FirebaseDatabase.getInstance()
        nDatabaseReference = mDataBase!!.getReference("Users")
        mAuth = FirebaseAuth.getInstance()
    }
    private fun updateUserInfoAndUi() {
        val intent = Intent(this@TelaCadastroActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


    private  fun  verifyEmail(){
        val mUser = mAuth!!.currentUser



        mUser!!.sendEmailVerification().addOnCompleteListener(this){
            task ->
            if(task.isSuccessful){
                Toast.makeText(this@TelaCadastroActivity, "Autentique no seu  e-mail" + mUser.getEmail(),
                      Toast.LENGTH_SHORT
                    ).show()

            }
        }

    }



    
}