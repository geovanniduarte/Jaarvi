package com.jaarvi.ui.components.cards

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.theme.JaarviColors
import com.jaarvi.ui.theme.Spacing

/**
 * Status card variants for displaying different states.
 */
enum class StatusCardVariant {
    SUCCESS,
    ERROR,
    WARNING
}

/**
 * Card component for displaying status information.
 *
 * Shows icon, title, and message with color-coded variant styles.
 *
 * @param variant Visual style variant (success, error, warning)
 * @param title Status title
 * @param message Status message text
 * @param modifier Optional modifier for customization
 */
@Composable
fun StatusCard(
    variant: StatusCardVariant,
    title: String,
    message: String,
    modifier: Modifier = Modifier
) {
    val (color, icon) = when (variant) {
        StatusCardVariant.SUCCESS -> JaarviColors.Success to Icons.Default.CheckCircle
        StatusCardVariant.ERROR -> JaarviColors.Error to Icons.Default.Close
        StatusCardVariant.WARNING -> JaarviColors.Warning to Icons.Default.Info
    }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .padding(Spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(Spacing.medium))
            
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface
                )
                
                Spacer(modifier = Modifier.height(Spacing.extraSmall))
                
                Text(
                    text = message,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
                )
            }
        }
    }
}
