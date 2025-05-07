package ie.setu.petadoption.ui.components.mylistings

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import ie.setu.petadoption.data.model.PetAdoptionModel
import ie.setu.petadoption.ui.theme.PetAdoptionTheme
import java.text.DateFormat

@Composable
fun MyListingsCardList(
    listings: List<PetAdoptionModel>,
    modifier: Modifier = Modifier,
    onDeleteListing: (PetAdoptionModel) -> Unit,
    onClickListingDetails: (String) -> Unit,
    onRefreshList: () -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = listings,
            key = { listing -> listing._id }
        ) { listing ->
            ie.setu.petadoption.ui.components.mylistings.MyListingCard(
                petName = listing.petName,
                petType = listing.petType,
                details = listing.details,
                ownerName = listing.ownerName,
                email = listing.email,
                datePosted = DateFormat.getDateTimeInstance().format(listing.datePosted),
                dateModified = DateFormat.getDateTimeInstance().format(listing.dateModified),
                imageUrl = listing.imageUrl,
                onClickDelete = { onDeleteListing(listing) },
                onClickListingDetails = { onClickListingDetails(listing._id) },
                onRefreshList = onRefreshList
            )
        }
    }
}


