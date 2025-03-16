package org.ceballos.mayantour

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mayantourapp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Mayantourapp() {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var adults by remember { mutableStateOf("1") }
    var kids by remember { mutableStateOf("0") }
    var selectedDestination by remember { mutableStateOf("Select Destination") }
    var date by remember { mutableStateOf("Select Date") }
    var time by remember { mutableStateOf("Select Time") }
    var comments by remember { mutableStateOf(TextFieldValue("")) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val destinations = listOf("Chichén Itzá", "Tulum", "Uxmal", "Kabah", "Ek Balam")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mayan Tour") },
                actions = {
                    IconButton(onClick = { Log.d("MayanTourApp", "Contacting Tour Operator...") }) {
                        Icon(Icons.Default.Call, contentDescription = "Contact Operator")
                    }
                    IconButton(onClick = {
                        name = TextFieldValue("")
                        adults = "1"
                        kids = "0"
                        selectedDestination = "Select Destination"
                        date = "Select Date"
                        time = "Select Time"
                        comments = TextFieldValue("")
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset Form")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (kids.toInt() > 0 && adults.toInt() == 0) {
                    Log.d("MayanTourApp", "Kids cannot travel alone!")
                } else {
                    Log.d("MayanTourApp", "Booking Confirmed: $name, $adults adults, $kids kids, Destination: $selectedDestination, Date: $date, Time: $time, Comments: $comments")
                }
            }) {
                Icon(Icons.Default.Check, contentDescription = "Submit Form")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Your Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = adults, onValueChange = { adults = it }, label = { Text("Number of Adults") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = kids, onValueChange = { kids = it }, label = { Text("Number of Kids") }, modifier = Modifier.fillMaxWidth())
            DropdownMenu(expanded = false, onDismissRequest = {}) {
                destinations.forEach { destination ->
                    DropdownMenuItem(text = { Text(destination) }, onClick = { selectedDestination = destination })
                }
            }
            Button(onClick = { showDatePicker = true }) { Text(date) }
            Button(onClick = { showTimePicker = true }) { Text(time) }
            OutlinedTextField(value = comments, onValueChange = { comments = it }, label = { Text("Additional Comments") }, modifier = Modifier.fillMaxWidth())
        }
    }

    if (showDatePicker) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            LocalContext.current,
            { _, year, month, day ->
                date = "$day/${month + 1}/$year"
                showDatePicker = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    if (showTimePicker) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            LocalContext.current,
            { _, hour, minute ->
                time = "$hour:$minute"
                showTimePicker = false
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }
}