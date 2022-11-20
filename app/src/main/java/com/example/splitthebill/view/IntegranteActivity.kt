package com.example.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.splitthebill.databinding.ActivityIntegranteBinding
import com.example.splitthebill.model.Constante.EXTRA_INTEGRANTE
import com.example.splitthebill.model.Constante.VIEW_INTEGRANTE
import com.example.splitthebill.model.Integrante

class IntegranteActivity : AppCompatActivity() {

    private val aib : ActivityIntegranteBinding by lazy {
        ActivityIntegranteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(aib.root)

        val integranteRecebido = intent.getParcelableExtra<Integrante>(EXTRA_INTEGRANTE)

        integranteRecebido?.let{ _integranteRecebido ->
            with(aib) {
                with(_integranteRecebido) {
                    nomeEt.setText(nome)
                    gastouEt.setText(gastou)
                    comprouEt.setText(comprou)
                }
            }
        }

        val viewContact = intent.getBooleanExtra(VIEW_INTEGRANTE, false)
        if(viewContact) {
            aib.nomeEt.isEnabled = false
            aib.gastouEt.isEnabled = false
            aib.comprouEt.isEnabled = false
            aib.salvarBt.visibility = View.GONE
        }

        aib.salvarBt.setOnClickListener {
            val integrante = Integrante(
                nome = aib.nomeEt.text.toString(),
                gastou = aib.gastouEt.text.toString(),
                comprou = aib.comprouEt.text.toString(),
            )
            val intent = Intent()
            intent.putExtra(EXTRA_INTEGRANTE, integrante)
            setResult(RESULT_OK, intent)
            finish()
        }

    }
}