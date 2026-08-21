package com.example.keyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editInput = view.findViewById<EditText>(R.id.editInput)
        val txtResult = view.findViewById<TextView>(R.id.txtResult)
        val btnApply = view.findViewById<Button>(R.id.btnApply)
        val switchMode = view.findViewById<Switch>(R.id.switchMode)

        btnApply.setOnClickListener {
            val input = editInput.text.toString().trim()
            if (input.isNotEmpty()) {
                txtResult.text = murinsky(input)
            } else {
                txtResult.text = ""
            }
        }

        switchMode.setOnCheckedChangeListener { _, isChecked ->
            btnApply.isEnabled = isChecked
        }
    }

    private fun murinsky(input: String): String {
        val words = input.split(" ")
        return words.joinToString(" ") { w ->
            val clean = w.lowercase()
            when {
                clean in listOf("ч", "батч", "друнн") -> clean
                clean.endsWith("ость") || clean.endsWith("ность") || clean.endsWith("ствость") -> clean
                else -> clean + "ость"
            }
        }
    }
}
