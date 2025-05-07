package ie.setu.petadoption.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
//import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
//import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppDestination {
    val icon: ImageVector
    val label: String
    val route: String
}

object Listings : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.List
    override val label = "All Listings"
    override val route = "listings"
}

object AddListing : AppDestination {
    override val icon = Icons.Filled.AddCircle
    override val label = "Add new"
    override val route = "addnew"
}

object MyListing : AppDestination {
    override val icon = Icons.Filled.Info
    override val label = "My Listings"
    override val route = "mylistings"
}

object Details : AppDestination {
    override val icon = Icons.Filled.Info
    override val label = "Details"
    const val idArg = "id"
    override val route = "details/{$idArg}"
    val arguments = listOf(
        navArgument(idArg) { type = NavType.StringType }
    )
}

object Home : AppDestination {
    override val icon = Icons.Filled.Home
    override val label = "Home"
    override val route = "Home"
}

object Profile : AppDestination {
    override val icon = Icons.Default.Person
    override val label = "Profile"
    override val route = "Profile"
}

object Login : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.List
    override val label = "Login"
    override val route = "Login"
}

object Register : AppDestination {
    override val icon = Icons.Default.AccountCircle
    override val label = "Register"
    override val route = "Register"
}

val bottomAppBarDestinations = listOf(Listings, AddListing, MyListing, Profile)
val userSignedOutDestinations = listOf(Login, Register)
val allDestinations = listOf(Listings, AddListing, MyListing, Details, Home, Profile, Login, Register)


