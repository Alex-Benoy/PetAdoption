package ie.setu.petadoption.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.petadoption.ui.screens.listings.ListingsScreen
import ie.setu.petadoption.ui.screens.listnew.ListNewScreen
import ie.setu.petadoption.ui.screens.login.LoginScreen
import ie.setu.petadoption.ui.screens.mylistings.MyListingsScreen
import ie.setu.petadoption.ui.screens.profile.ProfileScreen
import ie.setu.petadoption.ui.screens.register.RegisterScreen

//import ie.setu.donationx.ui.screens.about.AboutScreen
//import ie.setu.donationx.ui.screens.details.DetailsScreen
//import ie.setu.donationx.ui.screens.donate.DonateScreen
//import ie.setu.donationx.ui.screens.home.HomeScreen
//import ie.setu.donationx.ui.screens.login.LoginScreen
//import ie.setu.donationx.ui.screens.profile.ProfileScreen
//import ie.setu.donationx.ui.screens.register.RegisterScreen
//import ie.setu.donationx.ui.screens.report.ReportScreen

@Composable
fun NavHostProvider(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: AppDestination,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = Modifier.padding(paddingValues = paddingValues)) {

        composable(route = AddListing.route) {
            //call our 'Donate' Screen Here
            ListNewScreen(modifier = modifier)
        }

        composable(route = Home.route) {
            //call our 'Home' Screen Here
//            HomeScreen(modifier = modifier)
        }
        composable(route = Listings.route) {
            //call our 'Report' Screen Here
            ListingsScreen(modifier = modifier,
//                onClickDonationDetails = {
//                        donationId : String ->
//                    navController.navigateToDonationDetails(donationId)
//                },
            )
        }
        composable(route = MyListing.route) {
            //call our 'About' Screen Here
            MyListingsScreen(modifier = modifier)
        }

        composable(route = Login.route) {
            //call our 'Login' Screen Here
            LoginScreen(
                navController = navController,
                onLogin = { navController.popBackStack() }
            )
        }

        composable(route = Register.route) {
            //call our 'Register' Screen Here
            RegisterScreen(
                navController = navController,
                onRegister = { navController.popBackStack() }
            )
        }

        composable(
            route = Details.route,
            arguments = Details.arguments
        )
        { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString(Details.idArg)
            if (id != null) {
                ListingsScreen(modifier = modifier)
            }
        }

        composable(route = Profile.route) {
            ProfileScreen(
                onSignOut = {
                    navController.popBackStack()
                    navController.navigate(Login.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                },
            )
        }
    }
}

private fun NavHostController.navigateToDonationDetails(donationId: String) {
    this.navigate("details/$donationId")
}

