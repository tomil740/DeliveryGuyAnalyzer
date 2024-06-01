package dataAnalyzer.presentation.util

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class Dimnations(
    val small:Dp,val medium:Dp,val large:Dp,val huge:Dp
) {
    Spacer(8.dp,16.dp,24.dp,32.dp),
    IconSize(22.dp,32.dp,42.dp,52.dp)
}