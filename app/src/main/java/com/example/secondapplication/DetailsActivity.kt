package com.example.secondapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Recuperar dados enviados da DashboardActivity
        val name = intent.getStringExtra("entity_name") ?: "No Name"
        val summary = intent.getStringExtra("entity_summary") ?: "No Summary"
        val description = intent.getStringExtra("entity_description") ?: "No Description"

        // Associar os TextViews do layout
        val nameTextView: TextView = findViewById(R.id.entity_name)
        val summaryTextView: TextView = findViewById(R.id.entity_summary)
        val descriptionTextView: TextView = findViewById(R.id.entity_description)

        // Definir os textos nos TextViews
        nameTextView.text = name
        summaryTextView.text = summary
        descriptionTextView.text = description
    }
}