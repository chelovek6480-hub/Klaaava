package com.example.keyboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class DictionaryFragment : Fragment() {

    private val words = listOf(
        Word("кодовость", 126),
        Word("мамность", 98),
        Word("программистость", 76),
        Word("друннствость", 64),
        Word("язьность", 58),
        Word("галлюцинационность", 44),
        Word("делачность", 38),
        Word("возможность", 36),
        Word("отказность", 32),
        Word("даость", 30),
        Word("бабность", 28),
        Word("красотость", 26)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dictionary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerWords)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = WordAdapter(words)
    }
}
