package com.example.keyboard

import android.inputmethodservice.InputMethodService
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout

class KeyboardService : InputMethodService() {

    private val rows: List<List<Char>> = listOf(
        listOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'),
        listOf('q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p'),
        listOf('a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l'),
        listOf('z', 'x', 'c', 'v', 'b', 'n', 'm')
    )

    private var capsLock = false

    override fun onCreateInputView(): View {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        rows.forEachIndexed { index, row ->
            if (index == 1) root.addView(makeRow(listOf("⇧") + row.map { it.toString() } + listOf("⌫")))
            else if (index == 3) root.addView(makeRow(listOf("🌐") + row.map { it.toString() } + listOf("↵")))
            else root.addView(makeRow(row.map { it.toString() }))
        }

        root.addView(makeRow(listOf(" " )).apply { addView(makeSpaceKey()) })

        return root
    }

    private fun makeRow(keys: List<String>): LinearLayout {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }
        keys.forEach { key ->
            row.addView(makeKey(key))
        }
        return row
    }

    private fun makeKey(label: String): Button {
        return Button(this).apply {
            text = if (label.length == 1 && label[0].isLowerCase() && capsLock) label.uppercase() else label
            textSize = 18f
            setPadding(0, 16, 0, 16)
            setOnClickListener { onKeyPressed(label) }
        }
    }

    private fun makeSpaceKey(): Button {
        return Button(this).apply {
            text = "Space"
            textSize = 18f
            setPadding(0, 16, 0, 16)
            setOnClickListener {
                val ic = currentInputConnection ?: return@setOnClickListener
                ic.commitText(" ", 1)
            }
        }
    }

    private fun onKeyPressed(key: String) {
        val ic = currentInputConnection ?: return
        when (key) {
            "⌫" -> {
                val selected = ic.getSelectedText(0)
                if (TextUtils.isEmpty(selected)) {
                    ic.deleteSurroundingText(1, 0)
                } else {
                    ic.commitText("", 1)
                }
            }
            "↵" -> ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
            "⇧" -> {
                capsLock = !capsLock
                onStartInputView(currentInputEditorInfo, false)
            }
            "🌐" -> switchToNextInputMethod(false)
            else -> {
                val toSend = if (capsLock) key.uppercase() else key.lowercase()
                ic.commitText(toSend, 1)
            }
        }
    }
}
