package dataAnalyzer.presentation.util

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class Dimnations(
   val tiny:Dp, val small:Dp,val medium:Dp,val large:Dp,val huge:Dp
) {
    Spacer(4.dp,8.dp,16.dp,24.dp,32.dp),
    IconSize(12.dp,22.dp,32.dp,42.dp,52.dp),
    ActionBut(34.dp,44.dp,54.dp,64.dp,74.dp),
    Padding(4.dp,8.dp,12.dp,16.dp,20.dp),
    BigPaddings(25.dp,35.dp,12.dp,45.dp,55.dp)
}