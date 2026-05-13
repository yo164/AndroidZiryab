package com.alanturin.primerbocetoui.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.alanturin.primerbocetoui.R

@Composable
fun UserMenu(
    userName: String,
    userEmail: String,
    onAboutClick: () -> Unit,
    onLogout: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        // Mini avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable { expanded = true },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = userName.firstOrNull()?.uppercase() ?: stringResource(id = R.string.menu_user_name_default),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        // Menú desplegable
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(200.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = userName, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = userEmail, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = {
                        expanded = false
                        onAboutClick()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.menu_about))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        expanded = false
                        onLogout()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(id = R.string.menu_logout))
                }
            }
        }
    }
}