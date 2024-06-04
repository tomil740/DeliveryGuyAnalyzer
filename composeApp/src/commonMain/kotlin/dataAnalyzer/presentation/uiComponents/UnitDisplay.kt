package dataAnalyzer.presentation.uiComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dataAnalyzer.presentation.util.Dimnations
import dataAnalyzer.presentation.util.valuePresentation

/*
UnitDisplay :
this function in general will represent our value text , we have couple of varints to this function according
to its arguments :
* with preFix text to the unit , includes also an callback specific to clicking on it
* basic value presentation with its matched unit Icon...

the flag that is used to define on which state the function is , is the unitText : String argument
* when its default "" we will use the behaviour 2 and when isnt option 1

 */
@Composable
fun UnitDisplay(
    amount: Float,
    unitIcon: ImageVector,
    modifier: Modifier = Modifier,
    unitText : String = "",
    isMainObj: Boolean = true,
    amountTextStyle: TextStyle = MaterialTheme.typography.displayMedium,
    amountColor: Color = MaterialTheme.colorScheme.onBackground,
    iconColor:Color = Color.Green,
    onItemClick : ()->Unit={}) {

    var offset by remember { mutableStateOf(8.dp) }
    val isOption1 = unitText != ""

    if (!isMainObj)
        offset = 2.dp

    else{
        offset = 8.dp
    }
    val theModifier = if (isOption1){modifier.clickable { onItemClick() }}else{modifier}
    Row(modifier = theModifier){
        if (isOption1){
            Text(
                text = unitText,
                style = amountTextStyle,
                color = amountColor,
                modifier = Modifier
            )
        }
        Text(
            text = valuePresentation(amount),
            style = amountTextStyle,
            color = amountColor,
            modifier = Modifier
        )

        Icon(imageVector = unitIcon, contentDescription = null,modifier= Modifier
            //the icons should be a bit bigger then the font by that we keep it relative to our text
            .size(amountTextStyle.fontSize.value.dp + Dimnations.IconSize.tiny),
            tint = iconColor

        )


    }

}