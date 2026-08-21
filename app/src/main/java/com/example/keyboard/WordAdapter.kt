package com.example.keyboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordAdapter(
    private val words: List<Word>
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtWord: TextView = view.findViewById(R.id.txtWord)
        val txtCount: TextView = view.findViewById(R.id.txtCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = words[position]
        holder.txtWord.text = item.word
        holder.txtCount.text = item.count.toString()
    }

    override fun getItemCount(): Int = words.size
}
