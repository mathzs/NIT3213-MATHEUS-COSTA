package com.example.secondapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var entityAdapter: EntityAdapter
    private val entities = mutableListOf<Entity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val keypass = intent.getStringExtra("keypass") ?: ""
        fetchEntities(keypass)

        entityAdapter = EntityAdapter(this, entities) { entity ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("entity_name", entity.name)
            intent.putExtra("entity_summary", entity.summary)
            intent.putExtra("entity_description", entity.description) // Incluído
            startActivity(intent)
        }

        recyclerView.adapter = entityAdapter
    }

    private fun fetchEntities(keypass: String) {
        val url = "https://nit3213-api-h2b3-latest.onrender.com/dashboard/{keypass}"
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $keypass")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@DashboardActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                    Log.e("DashboardActivity", "Error: ${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val jsonArray = JSONArray(responseBody.string())
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val name = jsonObject.getString("name")
                        val summary = jsonObject.getString("summary")
                        val description = jsonObject.getString("description")

                        entities.add(Entity(name, summary, description))
                    }
                    runOnUiThread {
                        entityAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}