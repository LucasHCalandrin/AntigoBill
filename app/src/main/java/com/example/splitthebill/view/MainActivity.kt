package com.example.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.splitthebill.R
import com.example.splitthebill.adapter.IntegranteAdapter
import com.example.splitthebill.databinding.ActivityMainBinding
import com.example.splitthebill.model.Constante.EXTRA_INTEGRANTE
import com.example.splitthebill.model.Constante.VIEW_INTEGRANTE
import com.example.splitthebill.model.Integrante

class MainActivity : AppCompatActivity() {

    private val amb : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val integranteList: MutableList<Integrante> = mutableListOf()

    // Adapter
    private lateinit var integranteAdapter : IntegranteAdapter

    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        integranteAdapter = IntegranteAdapter(this, integranteList)
        amb.integrantesLv.adapter = integranteAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK){
                val integrante = result.data?.getParcelableExtra<Integrante>(EXTRA_INTEGRANTE)
                integrante?.let { _integrante ->
                    val position = integranteList.indexOfFirst { it.nome == _integrante.nome }
                    if (position != -1) {
                        // Alterar Na Posição
                        integranteList[position] = _integrante
                    }
                    else{
                        integranteList.add(_integrante)
                    }
                    integranteList.sortBy { it.nome }
                    integranteAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(amb.integrantesLv)

        amb.integrantesLv.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                ṕosition: Int,
                id: Long
            ) {
                val integrante = integranteList[ṕosition]
                val integranteIntent = Intent(this@MainActivity, IntegranteActivity::class.java)
                integranteIntent.putExtra(EXTRA_INTEGRANTE, integrante)
                integranteIntent.putExtra(VIEW_INTEGRANTE, true)
                startActivity(integranteIntent)
            }
        }

    }

    // Adicionar um Contato

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addIntegranteMi -> {
                carl.launch(Intent(this, IntegranteActivity::class.java))
                true
            }
            else -> { false }
        }
    }

    // Remover e Editar um Contato

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        return when (item.itemId) {
            R.id.removeIntegranteMi -> {
                //Remove o Contato
                integranteList.removeAt(position)
                integranteAdapter.notifyDataSetChanged()
                true
            }
            R.id.editaIntegranteMi -> {
                //Chama a tela para editar o Contato
                val integrante = integranteList[position]
                val integranteIntent = Intent(this, IntegranteActivity::class.java)
                integranteIntent.putExtra(EXTRA_INTEGRANTE, integrante)
                integranteIntent.putExtra(VIEW_INTEGRANTE, false)
                carl.launch(integranteIntent)
                true
            }
            else -> { false }
        }
    }
}