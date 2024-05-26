package dataAnalyzer.presentation.uiComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dataAnalyzer.presentation.util.valuePresentation

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

    if (!isMainObj)
        offset = 2.dp

    else{
        offset = 8.dp
    }
    


    Row(modifier = modifier.clickable { onItemClick() }) {
        if (unitText != ""){
            Text(
                text = unitText,
                //style = MaterialTheme.typography.,
                style = amountTextStyle,
                color = amountColor,
                modifier = Modifier
            )
        }
        Text(
            text = valuePresentation(amount),
            //style = MaterialTheme.typography.,
            style = amountTextStyle,
            color = amountColor,
            modifier = Modifier
        )
        //Spacer(modifier = Modifier.width(8.dp))//spacing.spaceExtraSmall))

        Icon(imageVector = unitIcon, contentDescription = null,modifier= Modifier
            //.offset(y = offset)
            .size(amountTextStyle.fontSize.value.dp+14.dp),
            tint = iconColor

        )


    }

}