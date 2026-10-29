
package com.pocketpro

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

    private val bg = Color.rgb(18, 20, 28)
    private val panel = Color.rgb(35, 39, 52)
    private val blue = Color.rgb(65, 145, 255)
    private lateinit var root: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showMenu()
    }

    private fun text(
        value: String,
        size: Float,
        color: Int
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            gravity = Gravity.CENTER
            setPadding(12, 12, 12, 12)
        }
    }

    private fun button(
        label: String,
        action: () -> Unit
    ): Button {
        return Button(this).apply {
            text = label
            textSize = 16f
            setOnClickListener { action() }
        }
    }

    private fun setup(title: String) {
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(18, 18, 18, 18)
            setBackgroundColor(bg)
        }

        val scroll = ScrollView(this)
        scroll.addView(root)
        setContentView(scroll)

        root.addView(text(title, 30f, Color.WHITE))
    }

    private fun addMode(
        title: String,
        description: String,
        mode: String
    ) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(12, 12, 12, 12)
            setBackgroundColor(panel)
        }

        card.addView(text(title, 22f, Color.WHITE))
        card.addView(text(description, 14f, Color.LTGRAY))
        card.addView(button("Elegir este modo") {
            showController(mode)
        })

        root.addView(
            card,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 12, 0, 12)
            }
        )
    }

    private fun showMenu() {
        setup("PocketPro")
        root.addView(
            text("ELIGE TU MANDO", 18f, blue)
        )
        root.addView(
            text(
                "Selecciona cómo quieres jugar",
                16f,
                Color.LTGRAY
            )
        )

        addMode(
            "🎮 Joy-Con horizontal",
            "Teléfono en horizontal · Mando compacto",
            "Joy-Con horizontal"
        )

        addMode(
            "🎮 Joy-Con vertical",
            "Teléfono en vertical · Mando compacto",
            "Joy-Con vertical"
        )

        addMode(
            "🕹️ Mando Pro",
            "Cruceta, ABXY y dos sticks",
            "Mando Pro"
        )

        root.addView(
            text(
                "Prototipo: Bluetooth aún no conectado",
                13f,
                Color.rgb(120, 200, 255)
            )
        )
    }

    private fun showController(mode: String) {
        setup(mode)

        root.addView(
            text("POCKETPRO", 24f, blue)
        )
        root.addView(
            text("Modo seleccionado: $mode", 16f, Color.WHITE)
        )

        if (mode == "Joy-Con vertical") {
            addRow("↑", "SL", "SR")
            addRow("←", "●", "→")
            addRow("↓", "−", "+")
        } else {
            addRow("L", "↑", "X")
            addRow("←", "●", "→")
            addRow("↓", "Y", "A")
            addRow("ZL", "B", "ZR")
        }

        if (mode == "Mando Pro") {
            root.addView(
                text("STICKS", 18f, blue)
            )
            addRow("Stick L", "L3", "R3")
            addRow("Stick R", "−", "+")
        }

        root.addView(
            text(
                "Botones de demostración; sin conexión Bluetooth",
                13f,
                Color.LTGRAY
            )
        )

        root.addView(button("← Volver al menú") {
            showMenu()
        })
    }

    private fun addRow(a: String, b: String, c: String) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        for (label in listOf(a, b, c)) {
            row.addView(
                button(label) { },
                LinearLayout.LayoutParams(
                    0,
                    76
                ).apply {
                    weight = 1f
                    setMargins(3, 5, 3, 5)
                }
            )
        }

        root.addView(
            row,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
    }
}
