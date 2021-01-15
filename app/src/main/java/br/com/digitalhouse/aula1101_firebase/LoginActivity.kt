package br.com.digitalhouse.aula1101_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            callMain(user.uid, user.email.toString())
        }

        btnEntrar.setOnClickListener {
            getDataFields()
        }
        tvCreateRegister.setOnClickListener {
            callRegister()

        }

    }

     fun getDataFields() { // password no firebase por default é 6 digitos

        val email = edEmailRegister.text.toString()
        val password = edPasswordRegister.text.toString()
        val emailEpt = email.isNotEmpty()
        val passwordEpt = password.isNotEmpty()

        if (emailEpt && passwordEpt)
            sendDataFirebase(email, password)
        else
            showMsg("Preencha todas as informações ")
    }

    fun sendDataFirebase(email: String, password: String) {
//        showMsg("Enviando dados para o firebase")
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password) // só ira muda o . depois da instance
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result?.user!!
                        val idUser = firebaseUser.uid
                        val emailUser = firebaseUser.email.toString()

                        callMain(idUser, emailUser)

                    } else {
                        showMsg(task.exception?.message.toString())
                    }
                }
    }

    //Chama Activity main
    fun callMain(idUser: String, emailUser: String) {
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra("id", idUser)
        intent.putExtra("email", emailUser)

        startActivity(intent)
    }

    //Exibe informação
    fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    //Chama a activity de cadastro
    fun callRegister(){
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}