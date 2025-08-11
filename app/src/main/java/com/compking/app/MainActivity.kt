package com.compking.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CardAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CardAdapter()
        recyclerView.adapter = adapter

        fetchCards()
    }

    private fun fetchCards() {
        db.collection("sales")
            .limit(20)
            .get()
            .addOnSuccessListener { documents ->
                val cards = documents.map { it.toObject(Card::class.java) }
                adapter.submitList(cards)
            }
            .addOnFailureListener {
                // Handle failure
            }
    }
}
