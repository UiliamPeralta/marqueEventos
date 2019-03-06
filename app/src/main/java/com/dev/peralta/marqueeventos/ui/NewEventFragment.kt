package com.dev.peralta.marqueeventos.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.dev.peralta.marqueeventos.R
import com.dev.peralta.marqueeventos.TAG
import com.dev.peralta.marqueeventos.model.Estado
import com.dev.peralta.marqueeventos.util.getLocal
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_new_event.*

class NewEventFragment : Fragment() {
    private var state = ""
    private var city = ""
    private var category = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_event, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity.getLocal(Gson()) { local ->
            if (local != null) {
                createSpinnerState(local.estados, this)
                createSpinnerCat(this)
            }
        }
    }

    private fun createSpinnerCity(cidades: List<String>, context: Context) {
        val adapter = ArrayAdapter(context,
            R.layout.support_simple_spinner_dropdown_item, cidades)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinnerCity.adapter = adapter
        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                city = cidades[position]
                Log.i(TAG, city)
            }

        }
    }

    private fun createSpinnerCat(context: Context) {
        val cats = resources.getStringArray(R.array.categorias).filter { it != "Todas"}

        val adapter = ArrayAdapter(
            context,
            R.layout.support_simple_spinner_dropdown_item,
            cats
        )

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinnerCat.adapter = adapter
        spinnerCat.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = cats[position]
                Log.i(TAG, category)
            }
        }
    }

    private fun createSpinnerState(estados: List<Estado>, context: Context) {
        val siglas = estados.asSequence().map { it.sigla }.toList()
        val adapter = ArrayAdapter( context,
            R.layout.support_simple_spinner_dropdown_item, siglas)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinnerState.adapter = adapter
        spinnerState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val cidades = estados[position].cidades
                state = siglas[position]
                createSpinnerCity(cidades, context)
                Log.i(TAG, state)
            }

        }
    }
}
