package br.com.digitalhouse.aula1101_firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnCadastrar.setOnClickListener {
            getDataFields()
        }
    }

    private fun getDataFields() { // password no firebase por default é 6 digitos

        val email = edEmailRegister.text.toString()
        val password = edPasswordRegister.text.toString()
        val emailEpt = email.isNotEmpty()
        val passwordEpt = password.isNotEmpty()

        if (emailEpt && passwordEpt)
            sendDataFirebase(email, password)
        else
            showMsg("Preencha todas as informações ")
    }

    //Envia dados para o firebase

    fun sendDataFirebase(email: String, password: String) {
//        showMsg("Enviando dados para o firebase")
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result?.user!!
                        showMsg("usuário cadastrado com sucesso")
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
}