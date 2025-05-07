package ie.setu.petadoption.data.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class PetAdoptionModel(
    @DocumentId val _id: String = "N/A",
    val id: Int = 0,
    val petName: String = "Unknown",
    val petType: String = "Other", // e.g., Dog, Cat, Bird
    val details: String = "No details provided.",
    val ownerName: String = "Anonymous",
    val datePosted: Date = Date(),
    val dateModified: Date = Date(),
    val email: String = "unknown@example.com",
    val imageUrl: String = "" // Add image URL or Firebase Storage link
)