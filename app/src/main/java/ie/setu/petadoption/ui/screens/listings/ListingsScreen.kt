package ie.setu.petadoption.ui.screens.listings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.petadoption.R
import ie.setu.petadoption.ui.components.general.ShowLoader
import ie.setu.petadoption.ui.components.listings.DropdownMenuBox
import ie.setu.petadoption.ui.components.listings.ListingCardList
import timber.log.Timber

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingsScreen(modifier: Modifier = Modifier,
    listingViewModel: ListingsViewModel = hiltViewModel(),
) {

    val listings = listingViewModel.uiListings.collectAsState().value
    val isLoading = listingViewModel.isloading.value
    val isError = listingViewModel.iserror.value
    val error = listingViewModel.error.value

    var selectedPetType by remember { mutableStateOf("All") }

    // Get unique pet types and add "All"
    val petTypes = remember(listings) {
        listOf("All") + listings.map { it.petType }.distinct()
    }

    val filteredListings = remember(selectedPetType, listings) {
        if (selectedPetType == "All") listings
        else listings.filter {
            // Normalize both sides for comparison (trim whitespace and ignore case)
            it.petType.trim().equals(selectedPetType.trim(), ignoreCase = true)
        }
    }

    Timber.i("RS : List = $listings")

    Column {
        Column(
            modifier = modifier
                .padding(start = 24.dp, end = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Pet type filter dropdown
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Filter by Pet Type:", style = MaterialTheme.typography.bodyLarge)
                DropdownMenuBox(
                    options = petTypes,
                    selectedOption = selectedPetType,
                    onOptionSelected = { selectedPetType = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                ShowLoader("Loading Listings...")
            }

//            ReportText()

            if (listings.isEmpty() && !isError) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(id = R.string.empty_list),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 34.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            if (!isError && listings.isNotEmpty()) {
                ListingCardList(
                    listings = filteredListings,
//                    onDeleteListing = { listing ->
//                        listingViewModel.updateListing(listing.copy(petName = "[Deleted]"))
//                        listings = listings.filter { it._id != listing._id }
//                    },
//                    onClickListingDetails = { id ->
//                        onListingClicked(id)
//                    },
//                    onRefreshList = {
//                        listings = listOf(viewModel.listing.value)
//                    }
                )
            }

            if (isError) {
                Text(
                    text = "Error: ${error.message}",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
            }
        }
    }



}
