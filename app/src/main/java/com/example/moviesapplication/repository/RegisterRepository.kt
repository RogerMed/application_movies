package com.example.moviesapplication.repository

import android.widget.Toast
import com.example.moviesapplication.model.Pessoa
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterRepository {
     var nDatabaseReference: DatabaseReference? = null
     var mDataBase: FirebaseDatabase? = null
     var mAuth: FirebaseAuth? = null


    fun initializeFirebase() {
        mDataBase = FirebaseDatabase.getInstance()
        nDatabaseReference = mDataBase!!.getReference("Users")
        mAuth = FirebaseAuth.getInstance()
    }

}




