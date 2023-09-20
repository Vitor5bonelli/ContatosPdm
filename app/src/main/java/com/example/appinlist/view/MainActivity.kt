package com.example.appinlist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.appinlist.R
import com.example.appinlist.databinding.ActivityMainBinding
import com.example.appinlist.model.Constant.EXTRA_CONTACT
import com.example.appinlist.model.Contact

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    //Data Source
    private val contactList: MutableList<Contact> = mutableListOf()

    //Adapter
    private val contactAdapter: ArrayAdapter<String> by lazy {
        ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            contactList.map { contact ->
                contact.name}
        )
    }

    private lateinit var contactActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
        //fillContact()
        amb.ContatosLv.adapter = contactAdapter
        contactActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) {
            result ->
            if(result.resultCode == RESULT_OK){
                val contact = result.data?.getParcelableExtra<Contact>(EXTRA_CONTACT)
                contact?.let {
                    _contact ->
                    contactList.add(_contact)
                    contactAdapter.add(_contact.name)
                    contactAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addContactMi -> {
                //Abrir a tela ContactActivity para adicionar um novo contato
                contactActivityResultLauncher.launch(Intent(this, ContactActivity::class.java))
                true
            }
            else -> false
        }
    }

    private fun fillContact(){
        for(i in 1..50){
            contactList.add(
                Contact(
                    i,
                    "Nome: $i",
                    "Endereço: $i",
                    "Telefone: $i",
                    "Email: $i"
                )
            )
        }
    }
}