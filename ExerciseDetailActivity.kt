package com.example.myapplication.ui.theme

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.myapplication.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource


class ExerciseDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra("title") ?: "No Title"
        val description = intent.getStringExtra("description") ?: "No Description"
        val duration = intent.getStringExtra("duration") ?: "No Duration"

        setContent {
            ExerciseDetailScreen(title, description, duration)
        }
    }
}

@Composable
fun ExerciseDetailScreen(title: String, description: String, duration: String) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("completed_tasks", Context.MODE_PRIVATE)
    var isTaskCompleted by remember { mutableStateOf(sharedPrefs.getBoolean(title, false)) }
    var isTimerRunning by remember { mutableStateOf(false) }

    // Odabir pozadinske slike
    val backgroundRes = when (title) {
        "DEEP BREATHING" -> R.drawable.deep_breathing_bg
        "PROGRESSIVE MUSCLE RELAXATION" -> R.drawable.muscle_relaxation_bg
        "MINDFUL OBSERVATION" -> R.drawable.mindful_observation_bg
        else -> R.drawable.default_bg
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Pozadinska slika
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Box za naslov
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(text = title, fontSize = 24.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Box za opis
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(text = description, fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Box za trajanje
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(text = "Duration: $duration", fontSize = 14.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Gumb za pokretanje/pauziranje timera
            Button(onClick = { isTimerRunning = !isTimerRunning }) {
                Text(if (isTimerRunning) "Stop Timer" else "Start Timer")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Specifični timer unutar Boxa
            if (isTimerRunning) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    when (title) {
                        "DEEP BREATHING" -> DeepBreathingTimer()
                        "PROGRESSIVE MUSCLE RELAXATION" -> MuscleRelaxationTimer()
                        "MINDFUL OBSERVATION" -> MindfulObservationTimer()
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Gumb za označavanje zadatka kao završenog/nezavršenog
            Button(onClick = {
                isTaskCompleted = !isTaskCompleted
                sharedPrefs.edit().putBoolean(title, isTaskCompleted).apply()
            }) {
                Text(if (isTaskCompleted) "Mark as Uncompleted" else "Mark as Completed")
            }

            if (isTaskCompleted) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("✔ Task Completed!", color = Color.Green, fontSize = 18.sp)
            }
        }
    }
}

//timer za disanje
@Composable
fun DeepBreathingTimer() {
    var timerState by remember { mutableStateOf("Inhale") }
    var timeLeft by remember { mutableStateOf(4) }

    LaunchedEffect(timerState) {
        while (true) {
            delay(1000L)
            if (timeLeft > 1) {
                timeLeft -= 1
            } else {
                when (timerState) {
                    "Inhale" -> {
                        timerState = "Hold"
                        timeLeft = 7
                    }
                    "Hold" -> {
                        timerState = "Exhale"
                        timeLeft = 8
                    }
                    "Exhale" -> {
                        timerState = "Inhale"
                        timeLeft = 4
                    }
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Current Action: $timerState", fontSize = 20.sp, color = Color.White, modifier = Modifier.padding(16.dp))
        Text("Time Left: $timeLeft seconds", fontSize = 18.sp, color = Color.White)
    }
}

//timer za opuštanje mišića
@Composable
fun MuscleRelaxationTimer() {
    var timerState by remember { mutableStateOf("Tense") }
    var timeLeft by remember { mutableStateOf(5) }

    LaunchedEffect(timerState) {
        while (true) {
            delay(1000L)
            if (timeLeft > 1) {
                timeLeft -= 1
            } else {
                timerState = if (timerState == "Tense") "Relax" else "Tense"
                timeLeft = 5
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Current Action: $timerState", fontSize = 20.sp, color = Color.White, modifier = Modifier.padding(16.dp))
        Text("Time Left: $timeLeft seconds", fontSize = 18.sp, color = Color.White)
    }
}

//timer za vježbu promatranja
@Composable
fun MindfulObservationTimer() {
    var timeLeft by remember { mutableStateOf(120) } // 2 minute

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Time Left: ${timeLeft / 60} min ${timeLeft % 60} sec", fontSize = 18.sp, color = Color.White, modifier = Modifier.padding(16.dp))
        Text("Focus on your surroundings and observe.", fontSize = 16.sp, color = Color.White)
    }
}
