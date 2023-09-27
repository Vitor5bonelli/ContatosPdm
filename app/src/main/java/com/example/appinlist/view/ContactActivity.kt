package com.example.appinlist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appinlist.R
import com.example.appinlist.databinding.ActivityContactBinding
import com.example.appinlist.databinding.ActivityMainBinding
import com.example.appinlist.model.Constant.EXTRA_CONTACT
import com.example.appinlist.model.Contact
import kotlin.random.Random

class ContactActivity : AppCompatActivity() {

    private val acb: ActivityContactBinding by lazy{
        ActivityContactBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)

        val receivedContact = intent.getParcelableExtra<Contact>(EXTRA_CONTACT)
        receivedContact?.let{_receivedContact ->
            with (acb) {
                nameEt.setText(_receivedContact.name)
                addressEt.setText(_receivedContact.address)
                phoneEt.setText(_receivedContact.phone)
                emailEt.setText(_receivedContact.email)
            }
        }

        acb.saveBt.setOnClickListener{
            val contact: Contact = Contact(
                id = receivedContact?.id?:generateId(),
                acb.nameEt.text.toString(),
                acb.addressEt.text.toString(),
                acb.phoneEt.text.toString(),
                acb.emailEt.text.toString()
            )

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_CONTACT, contact)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun generateId(): Int = Random(System.currentTimeMillis()).nextInt()
}