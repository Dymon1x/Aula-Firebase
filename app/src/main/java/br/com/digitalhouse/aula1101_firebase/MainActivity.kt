package br.com.digitalhouse.aula1101_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getInformationPutExtra()

        btnLogout.setOnClickListener {
            logout()
            callLogin()
        }
    }

    //obtém informações do put extra
    fun getInformationPutExtra(){
        val extras = intent.extras
        if(extras != null){
            val id = extras.getString("id")
            val email = extras.getString("email")

            showMsg(id.toString())
            showMsg(email.toString())

        }
    }

    //realiza logout
    fun logout(){
        FirebaseAuth.getInstance().signOut()
    }

    fun callLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    //Exibe informações
    fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
