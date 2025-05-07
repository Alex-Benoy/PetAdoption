package ie.setu.petadoption.ui.screens.listnew

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.petadoption.data.model.PetAdoptionModel
import ie.setu.petadoption.ui.components.general.ShowLoader
import ie.setu.petadoption.ui.components.listnew.PetTypeDropdown
import java.util.*

@Composable
fun ListNewScreen(
    modifier: Modifier = Modifier,
    viewModel: ListNewViewModel = hiltViewModel()
) {
    var petName by remember { mutableStateOf("") }
    var petType by remember { mutableStateOf("Dog") }
    var details by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading
    val isError by viewModel.isErr
    val error by viewModel.error

    if (isLoading) {
        ShowLoader("Saving Listing...")
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Add New Listing", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = petName,
            onValueChange = { petName = it },
            label = { Text("Pet Name") },
            modifier = Modifier.fillMaxWidth()
        )

        PetTypeDropdown(
            selectedType = petType,
            onTypeSelected = { petType = it }
        )

        OutlinedTextField(
            value = details,
            onValueChange = { details = it },
            label = { Text("Details") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = ownerName,
            onValueChange = { ownerName = it },
            label = { Text("Owner Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val listing = PetAdoptionModel(
                    petName = petName,
                    petType = petType,
                    details = details,
                    ownerName = ownerName,
                    imageUrl = imageUrl,
                    datePosted = Date(),
                    dateModified = Date()
                )
                viewModel.insert(listing)
            },
            modifier = Modifier.align(Alignment.End),
            enabled = petName.isNotBlank() && details.isNotBlank()
        ) {
            Icon(Icons.Filled.Save, contentDescription = "Save")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Save Listing")
        }

        if (isError) {
            Text(
                text = "Error: ${error.message}",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
