package ie.setu.petadoption.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.petadoption.data.model.PetAdoptionModel
import ie.setu.petadoption.firebase.services.AuthService
import ie.setu.petadoption.firebase.database.FirestoreRepository
import ie.setu.petadoption.firebase.services.FirestoreService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject
constructor(private val repository: FirestoreService,
            private val authService: AuthService,
            savedStateHandle: SavedStateHandle
) : ViewModel() {

    var listing = mutableStateOf(PetAdoptionModel())
    val id: String = checkNotNull(savedStateHandle["id"])
    var isErr = mutableStateOf(false)
    var error = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)


    init {
        viewModelScope.launch {
            try {
                isLoading.value = true
                listing.value = repository.get(authService.email!!, id)!!
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
        }
    }

    fun updateListing(listing: PetAdoptionModel) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                repository.update(authService.email!!, listing)
                isErr.value = false
                isLoading.value = false
            } catch (e: Exception) {
                isErr.value = true
                error.value = e
                isLoading.value = false
            }
        }
    }
}