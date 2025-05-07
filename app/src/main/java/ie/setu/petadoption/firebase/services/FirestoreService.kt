package ie.setu.petadoption.firebase.services

import ie.setu.petadoption.data.model.PetAdoptionModel
import kotlinx.coroutines.flow.Flow

typealias PetAdoption = PetAdoptionModel
typealias PetAdoptions = Flow<List<PetAdoption>>

interface FirestoreService {

    suspend fun getAll() : PetAdoptions
    suspend fun getAllByEmail(email: String) : PetAdoptions
    suspend fun get(email: String, adoptionId: String) : PetAdoption?
    suspend fun insert(email: String, adoption: PetAdoption)
    suspend fun update(email: String, adoption: PetAdoption)
    suspend fun delete(email: String, donationId: String)
}