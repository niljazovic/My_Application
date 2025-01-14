class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                HomeScreen()
            }
        }
    }
}



@Composable
fun HomeScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Mental Health App",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Gumb za Mood Tracker
        Button(
            onClick = {
                context.startActivity(Intent(context, MoodTrackerActivity::class.java))
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Mood Tracker")
        }

        // Gumb za Advices and Quotes
        Button(
            onClick = {
                context.startActivity(Intent(context, TipsActivity::class.java))
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Tips and Quotes")
        }

        // Gumb za Relaxation Exercises
        Button(
            onClick = {
                context.startActivity(Intent(context, RelaxationActivity::class.java))
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Relaxation Exercises")
        }

        // Gumb za Resources and Help
        Button(
            onClick = {
                context.startActivity(Intent(context, ResourcesActivity::class.java))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Resources and Help")
        }
    }
}
