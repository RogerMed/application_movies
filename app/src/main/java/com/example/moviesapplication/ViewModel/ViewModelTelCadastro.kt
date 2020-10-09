package com.example.moviesapplication.ViewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapplication.model.Pessoa
import com.example.moviesapplication.repository.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ViewModelTelCadastro (repository: RegisterRepository) : ViewModel() {

    var pessoa = MutableLiveData<Pessoa>()
    val error :MutableLiveData<Throwable> = MutableLiveData()
    var repository = repository


    fun saveFirebase(id: String,name:String, email :String, senha: String,pessoaObj: Pessoa){

    repository.initializeFirebase()

    repository.mAuth!!.createUserWithEmailAndPassword(email, senha)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
            }

        }


      val currentUserDb = repository.nDatabaseReference!!.child(id)
      currentUserDb.setValue(pessoaObj)
}





}


