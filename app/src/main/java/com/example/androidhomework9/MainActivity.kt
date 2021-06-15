package com.example.androidhomework9

import android.R.attr.password
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidhomework9.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mAuth = FirebaseAuth.getInstance()



        binding.submitButton.setOnClickListener {
            if(!isValidEmail(binding.emailEditText.text.toString())){
                binding.emailEditText.error = "Enter Valid Email"
            }
            else if(!isValidPassword(binding.passwordEditText.text.toString())){
                binding.passwordEditText.error = "Password Must Contain 1 Special Symbol and Numbers"
            }

            else if(binding.passwordEditText.text.toString() != binding.passwordConfirmEditText.text.toString()){
                binding.passwordConfirmEditText.error = "Password doesn't match"
            } else{
                mAuth.createUserWithEmailAndPassword(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
                Toast.makeText(this,"Authentified Successfully",Toast.LENGTH_LONG).show()
                binding.apply {
                    emailEditText.setText("")
                    passwordEditText.setText("")
                    passwordConfirmEditText.setText("")
                }
            }


        }

    }


    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target!!).matches()
    }


    private fun isValidPassword(password: String?) : Boolean {
        password?.let {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$).{9,}$"
            val passwordMatcher = Regex(passwordPattern)

            return passwordMatcher.find(password) != null
        } ?: return false
    }

}