
package com.pocketpro

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {

    private val bg = Color.rgb(18, 20, 28)
    private val panel = Color.rgb(35, 39, 52)
    private val blue = Color.rgb(65, 145, 255)
    private val buttonColor = Color.rgb(55, 62, 79)

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
            setPadding(8, 8, 8, 8)
        }
    }

    private fun button(
        label: String,
        action: () -> Unit = {}
    ): Button {
        return Button(this).apply {
            text = label
            textSize = 16f
            isAllCaps = false
            backgroundTintList =
                ColorStateList.valueOf(buttonColor)
            setTextColor(Color.WHITE)
            minHeight = 58
            setOnClickListener { action() }
        }
    }

    private fun setup(title: String) {
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(12, 12, 12, 12)
            setBackgroundColor(bg)
        }

        val scroll = ScrollView(this)
        scroll.addView(root)
        setContentView(scroll)

        root.addView(text(title, 28f, Color.WHITE))
    }

    private fun addMode(
        title: String,
        description: String,
        mode: String
    ) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(8, 8, 8, 8)
            setBackgroundColor(panel)
        }

        card.addView(text(title, 21f, Color.WHITE))
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
                setMargins(0, 8, 0, 8)
            }
        )
    }

    private fun showMenu() {
        requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        showMenuContent()
    }

    private fun showMenuContent() {
        setup("PocketPro")

        root.addView(
            text("ELIGE TU MANDO", 18f, blue)
        )
        root.addView(
            text("Selecciona cómo quieres jugar", 15f, Color.LTGRAY)
        )

        addMode(
            "Joy-Con horizontal",
            "Diseño compacto para jugar de lado",
            "Joy-Con horizontal"
        )

        addMode(
            "Joy-Con vertical",
            "Diseño compacto en vertical",
            "Joy-Con vertical"
        )

        addMode(
            "Mando Pro",
            "Cruceta, ABXY, sticks y gatillos",
            "Mando Pro"
        )

        root.addView(
            text(
                "Prototipo visual · Bluetooth pendiente",
                13f,
                blue
            )
        )
    }

    private fun showController(mode: String) {
        requestedOrientation = when (mode) {
            "Joy-Con vertical" ->
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            else ->
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        setup(mode)

        root.addView(
            text("POCKETPRO", 24f, blue)
        )
        root.addView(
            text(mode, 16f, Color.LTGRAY)
        )

        when (mode) {
            "Joy-Con vertical" -> showVerticalJoyCon()
            "Joy-Con horizontal" -> showHorizontalJoyCon()
            else -> showProController()
        }

        root.addView(
            text(
                "Controles visuales: aún sin conexión a la Switch",
                12f,
                Color.LTGRAY
            )
        )

        root.addView(button("← Volver al menú") {
            showMenu()
        })
    }

    private fun showVerticalJoyCon() {
        addSection("SL                         SR")
        addRow("↑", "＋", "X")
        addRow("←", "●", "→")
        addRow("↓", "−", "A")
        addRow("L", "B", "R")
    }

    private fun showHorizontalJoyCon() {
        addSection("L                       SL / SR")
        addRow("↑", "X", "Y")
        addRow("←", "●", "→")
        addRow("↓", "B", "A")
        addSection("ZL                         ZR")
    }

    private fun showProController() {
        addSection("L       ZL                      ZR       R")

        val controls = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        val left = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(panel)
        }

        left.addView(text("CRUCETA", 13f, blue))
        addRowTo(left, "↑", "•", "X")
        addRowTo(left, "←", "●", "→")
        addRowTo(left, "↓", "•", "Y")

        val right = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setBackgroundColor(panel)
        }

        right.addView(text("ABXY", 13f, blue))
        addRowTo(right, "•", "X", "•")
        addRowTo(right, "Y", "A", "B")
        addRowTo(right, "•", "B", "•")

        controls.addView(
            left,
            LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        )

        controls.addView(
            right,
            LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        )

        root.addView(
            controls,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )

        addSection("STICKS ANALÓGICOS")

        addRow("Stick L", "−", "Stick R")
        addRow("L3", "HOME", "R3")
        addRow("CAPTURE", "+", "OPTIONS")
    }

    private fun addSection(label: String) {
        root.addView(text(label, 14f, blue))
    }

    private fun addRow(
        a: String,
        b: String,
        c: String
    ) {
        addRowTo(root, a, b, c)
    }

    private fun addRowTo(
        parent: LinearLayout,
        a: String,
        b: String,
        c: String
    ) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        for (label in listOf(a, b, c)) {
            row.addView(
                button(label),
                LinearLayout.LayoutParams(
                    0,
                    58,
                    1f
                ).apply {
                    setMargins(2, 3, 2, 3)
                }
            )
        }

        parent.addView(
            row,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
    }
}
