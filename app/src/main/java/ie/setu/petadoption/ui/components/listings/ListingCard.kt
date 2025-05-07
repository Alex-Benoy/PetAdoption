package ie.setu.petadoption.ui.components.listings

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ie.setu.petadoption.R
import ie.setu.petadoption.ui.theme.endGradientColor
import ie.setu.petadoption.ui.theme.startGradientColor
import java.text.DateFormat
import java.util.Date

@Composable
fun ListingCard(
    petName: String,
    petType: String,
    details: String,
    ownerName: String,
    email: String,
    datePosted: String,
    dateModified: String,
    imageUrl: String,
//    onClickDelete: () -> Unit,
//    onClickDetails: () -> Unit,
//    onRefreshList: () -> Unit
) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 2.dp)
    ) {
        ListingCardContent(
            petName,
            petType,
            details,
            ownerName,
            email,
            datePosted,
            dateModified,
            imageUrl,
//            onClickDelete,
//            onClickDetails,
//            onRefreshList
        )
    }
}

@Composable
private fun ListingCardContent(
    petName: String,
    petType: String,
    details: String,
    ownerName: String,
    email: String,
    datePosted: String,
    dateModified: String,
    imageUrl: String,
//    onClickDelete: () -> Unit,
//    onClickDetails: () -> Unit,
//    onRefreshList: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(2.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .background(
                brush = Brush.horizontalGradient(
                    listOf(startGradientColor, endGradientColor)
                )
            )
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "$petName image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = petName,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "($petType)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(text = "Posted: $datePosted", style = MaterialTheme.typography.labelSmall)
            Text(text = "Modified: $dateModified", style = MaterialTheme.typography.labelSmall)

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Details: $details", style = MaterialTheme.typography.bodySmall)
                Text(text = "Owner: $ownerName", style = MaterialTheme.typography.bodySmall)
                Text(text = "Email: $email", style = MaterialTheme.typography.bodySmall)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
//                    FilledTonalButton(onClick = onClickDetails) {
//                        Text("Show More")
//                    }
//                    FilledTonalIconButton(onClick = { showDeleteConfirmDialog = true }) {
//                        Icon(Icons.Filled.Delete, contentDescription = "Delete Listing")
//                    }
//
//                    if (showDeleteConfirmDialog) {
//                        showDeleteAlert(
//                            onDismiss = { showDeleteConfirmDialog = false },
//                            onDelete = onClickDelete,
//                            onRefresh = onRefreshList
//                        )
//                    }
                }
            }
        }

        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded)
                    stringResource(R.string.show_less)
                else
                    stringResource(R.string.show_more)
            )
        }
    }
}

@Composable
fun showDeleteAlert(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onRefresh: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.confirm_delete)) },
        text = { Text(stringResource(id = R.string.confirm_delete_message)) },
        confirmButton = {
            Button(
                onClick = {
                    onDelete()
                    onRefresh()
                }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}