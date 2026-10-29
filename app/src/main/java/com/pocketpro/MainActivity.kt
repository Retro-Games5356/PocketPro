
package com.pocketpro

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(24, 24, 24, 24)
            setBackgroundColor(Color.rgb(18, 20, 28))
        }

        val title = TextView(this).apply {
            text = "PocketPro"
            textSize = 32f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "Tu mando empieza aquí"
            textSize = 18f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
        }

        val status = TextView(this).apply {
            text = "Estado: prototipo inicial"
            textSize = 14f
            setTextColor(Color.rgb(120, 200, 255))
            gravity = Gravity.CENTER
        }

        val connect = Button(this).apply {
            text = "Bluetooth: próximamente"
            isEnabled = false
        }

        root.addView(title)
        root.addView(subtitle)
        root.addView(status)

        root.addView(
            connect,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 24
            }
        )

        setContentView(root)
    }
}

