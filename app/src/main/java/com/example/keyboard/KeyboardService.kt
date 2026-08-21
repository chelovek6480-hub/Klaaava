package com.example.keyboard

import android.inputmethodservice.InputMethodService
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity

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
            setBackgroundColor(Color.parseColor("#D1D5DB")) // Светло-серый фон клавиатуры
            setPadding(0, dpToPx(8), 0, dpToPx(8))
        }

        rows.forEachIndexed { index, row ->
            when (index) {
                1 -> root.addView(makeRow(listOf("⇧") + row.map { it.toString() } + listOf("⌫")))
                3 -> root.addView(makeRow(listOf("🌐") + row.map { it.toString() } + listOf("↵")))
                else -> root.addView(makeRow(row.map { it.toString() }))
            }
        }

        root.addView(makeRow(listOf(" ")))

        return root
    }

    private fun makeRow(keys: List<String>): LinearLayout {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, dpToPx(4), 0, dpToPx(4))
            }
        }
        keys.forEach { key ->
            row.addView(makeKey(key))
        }
        return row
    }

    private fun makeKey(label: String): Button {
        val isSpecial = label in listOf("⇧", "⌫", "🌐", "↵")
        val isSpace = label == " "
        
        return Button(this).apply {
            text = if (label.length == 1 && label[0].isLowerCase() && capsLock) label.uppercase() else label
            textSize = 20f
            isAllCaps = false
            setTextColor(Color.parseColor(if (isSpecial) "#FFFFFF" else "#000000"))
            
            setBackgroundResource(if (isSpecial) R.drawable.key_bg_special else R.drawable.key_bg)
            
            val weight = if (isSpace) 1f else 0f
            val width = if (isSpace) 0 else if (isSpecial) dpToPx(48) else dpToPx(36)
            
            layoutParams = LinearLayout.LayoutParams(width, dpToPx(54), weight).apply {
                setMargins(dpToPx(3), 0, dpToPx(3), 0)
            }
            
            setPadding(0, 0, 0, 0)
            setOnClickListener { onKeyPressed(label) }
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

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}
