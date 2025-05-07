package ie.setu.petadoption.ui.screens.mylistings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.petadoption.data.model.PetAdoptionModel
import ie.setu.petadoption.firebase.database.FirestoreRepository
import ie.setu.petadoption.firebase.services.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyListingsViewModel @Inject constructor(
    private val repository: FirestoreRepository,
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //    var listing = mutableStateOf(PetAdoptionModel())
//    val id: String = checkNotNull(savedStateHandle["id"])
//    var isErr = mutableStateOf(false)
//    var error = mutableStateOf(Exception())
//    var isLoading = mutableStateOf(false)
    private val _listings
            = MutableStateFlow<List<PetAdoptionModel>>(emptyList())
    val uiListings: StateFlow<List<PetAdoptionModel>>
            = _listings.asStateFlow()
    var iserror = mutableStateOf(false)
    var isloading = mutableStateOf(false)
    var error = mutableStateOf(Exception())

    init { getListings() }

    fun getListings() {
        viewModelScope.launch {
            try {
                isloading.value = true
                authService.email?.let {
                    repository.getAllByEmail(it).collect{ items ->
                        _listings.value = items
                        iserror.value = false
                        isloading.value = false
                    }
                }
                Timber.i("DVM RVM = : ${_listings.value}")
            }
            catch (e: Exception) {
                iserror.value = true
                isloading.value = false
                error.value = e
                Timber.i("RVM Error ${e.message}")
            }
        }
    }

//    fun updateListing(updatedListing: PetAdoptionModel) {
//        viewModelScope.launch {
//            try {
//                isLoading.value = true
//                repository.update(authService.email!!, updatedListing)
//                isErr.value = false
//            } catch (e: Exception) {
//                isErr.value = true
//                error.value = e
//            } finally {
//                isLoading.value = false
//            }
//        }
//    }
}