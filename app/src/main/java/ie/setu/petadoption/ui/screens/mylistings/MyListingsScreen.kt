package ie.setu.petadoption.ui.screens.mylistings

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
import ie.setu.petadoption.data.model.PetAdoptionModel
import ie.setu.petadoption.ui.components.general.ShowLoader
import ie.setu.petadoption.ui.components.listings.ListingCardList
import ie.setu.petadoption.firebase.services.AuthService
import ie.setu.petadoption.ui.components.mylistings.MyListingsCardList
import ie.setu.petadoption.ui.screens.listings.ListingsViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyListingsScreen(modifier: Modifier = Modifier,
                     myListingsViewModel: MyListingsViewModel = hiltViewModel()

) {

    var listings = myListingsViewModel.uiListings.collectAsState().value
    val isLoading = myListingsViewModel.isloading.value
    val isError = myListingsViewModel.iserror.value
    val error = myListingsViewModel.error.value

    Timber.i("RS : List = $listings")

    Column {
        Column(
            modifier = modifier
                .padding(start = 24.dp, end = 24.dp)
        ) {
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
                MyListingsCardList(
                    listings = listings,
                    onDeleteListing = { adoption: PetAdoptionModel ->
                        myListingsViewModel.deleteListing(adoption)
                    },
                    onClickListingDetails = {
//                        id ->
//                        onListingClicked(id)
                    },
                    onRefreshList = {
//                        listings = listOf(viewModel.listing.value)
                    }
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