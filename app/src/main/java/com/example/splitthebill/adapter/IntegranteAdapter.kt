package com.example.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.splitthebill.R
import com.example.splitthebill.model.Integrante

class IntegranteAdapter (
    context: Context,
    private val integranteList: MutableList<Integrante>
) : ArrayAdapter<Integrante>(context, R.layout.celula_integrante, integranteList) {

    private data class CelulaIntegranteHolder(val nomeTv: TextView, val gastouTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val integrante = integranteList[position]
        var celulaIntegranteView = convertView

        if(celulaIntegranteView == null) {
            // Inflo uma nova célula
            celulaIntegranteView =
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.celula_integrante,
                    parent,
                    false
                )

            val celulaIntegranteHolder = CelulaIntegranteHolder(
                celulaIntegranteView.findViewById(R.id.nomeTv),
                celulaIntegranteView.findViewById(R.id.gastouTv),
            )
            celulaIntegranteView.tag = celulaIntegranteHolder
        }

        with(celulaIntegranteView?.tag as CelulaIntegranteHolder) {
            nomeTv.text = integrante.nome
            gastouTv.text = integrante.gastou
        }

        return celulaIntegranteView
    }
}
