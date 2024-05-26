package dataAnalyzer.presentation.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircleValues(theHeight:Dp,barSize:Int,barValue:Float,textStyle: TextStyle = MaterialTheme.typography.displayLarge,
                 textColor:Color=MaterialTheme.colorScheme.onPrimary
) {

    Box(
        modifier = Modifier
            .height(theHeight)
            .offset(x = -22.dp,y=-4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Box(modifier = Modifier.rotate(-9f).offset(x = 28.dp)) {
                UnitDisplay(
                    amount = barValue,
                    unitIcon =Icons.Default.AttachMoney,//"hours /",//stringResource(id = R.string.grams),
                    amountColor = textColor,
                    iconColor = textColor,
                    amountTextStyle = MaterialTheme.typography.titleLarge
                )
            }

            Box(modifier = Modifier.offset(x = 56.dp)) {
                UnitDisplay(
                    amount = barSize.toFloat(),
                    unitIcon = Icons.Default.AttachMoney,//stringResource(id = R.string.grams),
                    amountColor = textColor,
                    iconColor = textColor,
                    amountTextStyle = MaterialTheme.typography.titleSmall
                )
            }
        }

        Text(
            text = " | ",
            color = Color.Black,
            style = textStyle,
            modifier = Modifier
                .offset(x = 24.dp,y=-4.dp)
                .rotate(78f)
        )



    }
}