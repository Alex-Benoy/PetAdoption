package ie.setu.petadoption.ui.screens.details

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import ie.setu.petadoption.data.model.PetAdoptionModel
import ie.setu.petadoption.ui.components.details.ReadOnlyTextField
import ie.setu.petadoption.ui.theme.PetAdoptionTheme
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val pet = viewModel.listing.value
    val context = LocalContext.current
    val isError = viewModel.isErr.value
    val isLoading = viewModel.isLoading.value

    var detailsText by rememberSaveable { mutableStateOf("") }
    var onDetailsChanged by rememberSaveable { mutableStateOf(false) }
    var isEmptyError by rememberSaveable { mutableStateOf(false) }
    var isShortError by rememberSaveable { mutableStateOf(false) }

    val errorEmptyMessage = "Details cannot be empty."
    val errorShortMessage = "Details must be at least 5 characters."

    // Update detailsText when pet changes
    LaunchedEffect(pet) {
        detailsText = pet.details
    }

    fun validate(text: String) {
        isEmptyError = text.isBlank()
        isShortError = text.length < 5
        onDetailsChanged = !(isEmptyError || isShortError)
    }

    if (isError) {
        Toast.makeText(context, "Failed to load pet details", Toast.LENGTH_SHORT).show()
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Optional: Display image if available
        if (pet.imageUrl.isNotBlank()) {
            Image(
                painter = rememberAsyncImagePainter(pet.imageUrl),
                contentDescription = "Pet Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        ReadOnlyTextField(value = pet.petName, label = "Pet Name")
        ReadOnlyTextField(value = pet.petType, label = "Pet Type")
        ReadOnlyTextField(
            value = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(pet.datePosted),
            label = "Date Posted"
        )

        OutlinedTextField(
            value = detailsText,
            onValueChange = {
                detailsText = it
                validate(it)
            },
            label = { Text("Details") },
            isError = isEmptyError || isShortError,
            maxLines = 3,
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                when {
                    isEmptyError -> Text(errorEmptyMessage, color = MaterialTheme.colorScheme.error)
                    isShortError -> Text(errorShortMessage, color = MaterialTheme.colorScheme.error)
                }
            },
            trailingIcon = {
                if (isEmptyError || isShortError)
                    Icon(Icons.Filled.Warning, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                else
                    Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.Black)
            },
            keyboardActions = KeyboardActions { validate(detailsText) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            )
        )

//        Spacer(modifier = Modifier.height(1.dp))

        Button(
            onClick = {
                viewModel.updateListing(pet.copy(details = detailsText))
                onDetailsChanged = false
            },
            enabled = onDetailsChanged,
            elevation = ButtonDefaults.buttonElevation(8.dp)
        ) {
            Icon(Icons.Default.Save, contentDescription = "Save")
            Spacer(Modifier.width(8.dp))
            Text("Save", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}
