package ie.setu.petadoption.firebase.database

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import ie.setu.petadoption.firebase.services.FirestoreService
import ie.setu.petadoption.firebase.services.PetAdoption
import ie.setu.petadoption.firebase.services.PetAdoptions
import ie.setu.petadoption.data.rules.Constants.ADOPTION_COLLECTION
import ie.setu.petadoption.data.rules.Constants.USER_EMAIL
import ie.setu.petadoption.firebase.services.AuthService
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject


class FirestoreRepository
@Inject constructor(private val auth: AuthService,
                    private val firestore: FirebaseFirestore
) : FirestoreService {

    override suspend fun getAll(): PetAdoptions {

        return firestore.collection(ADOPTION_COLLECTION)
//            .orderBy("dateModified",
//                if(sortAsending)
//                    Query.Direction.ASCENDING
//                else
//                    Query.Direction.DESCENDING)
            .dataObjects()
    }

    override suspend fun getAllByEmail(email: String): PetAdoptions {

        return firestore.collection(ADOPTION_COLLECTION)
            .whereEqualTo("email", email)
            .dataObjects()
    }

    override suspend fun get(email: String,
                             adoptionId: String): PetAdoption? {
        return firestore.collection(ADOPTION_COLLECTION)
            .document(adoptionId).get().await().toObject()
    }

    override suspend fun insert(email: String,
                                petAdoption: PetAdoption)
    {
        val listingWithEmail = petAdoption.copy(email = email)

        firestore.collection(ADOPTION_COLLECTION)
            .add(listingWithEmail)
            .await()

    }

    override suspend fun update(email: String,
                                petAdoption: PetAdoption) {
//        val donationWithModifiedDate =
//            donation.copy(dateModified = Date())
//
//        firestore.collection(DONATION_COLLECTION)
//            .document(donation._id)
//            .set(donationWithModifiedDate).await()
    }

    override suspend fun delete(email: String,
                                adoptionId: String) {
        firestore.collection(ADOPTION_COLLECTION)
            .document(adoptionId)
            .delete().await()
    }
}