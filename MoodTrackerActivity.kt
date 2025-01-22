package com.example.myapplication.ui.theme

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

class MoodTrackerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoodTrackerScreen()
        }
    }
}

@Composable
fun MoodTrackerScreen() {
    val db = FirebaseFirestore.getInstance()
    var mood by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Mood Tracker", fontSize = 24.sp, modifier = Modifier.padding(bottom = 24.dp))

        
        TextField(
            value = mood,
            onValueChange = { mood = it },
            label = { Text("Enter your mood") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

       
        TextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Enter a note") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

       
        Button(
            onClick = {
                if (mood.isNotEmpty()) {
                    val moodEntry = hashMapOf(
                        "mood" to mood,
                        "note" to note,
                        "timestamp" to System.currentTimeMillis()
                    )

                    db.collection("moodEntries")
                        .add(moodEntry)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Mood saved!", Toast.LENGTH_SHORT).show()
                            mood = ""  
                            note = ""
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Failed to save mood: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(context, "Please enter your mood.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Mood")
        }
    }
}
