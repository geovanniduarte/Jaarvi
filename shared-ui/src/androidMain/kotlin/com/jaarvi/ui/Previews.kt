import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaarvi.ui.linda.components.level1.ButtonGlow
import com.jaarvi.ui.linda.components.level1.ButtonVariant
import com.jaarvi.ui.linda.components.level1.LindaButton

@Composable
@Preview(showBackground = false)
fun DefaultPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
        LindaButton(text = "Primary", onClick = {}, variant = ButtonVariant.PRIMARY)
        LindaButton(text = "Gold",    onClick = {}, variant = ButtonVariant.GLASS, glow = ButtonGlow.GOLD)
        LindaButton(text = "Lime",    onClick = {}, variant = ButtonVariant.GLASS, glow = ButtonGlow.LIME)
    }
}
