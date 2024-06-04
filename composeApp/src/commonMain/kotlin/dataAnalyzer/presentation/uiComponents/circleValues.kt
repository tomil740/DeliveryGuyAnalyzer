package dataAnalyzer.presentation.uiComponents

import androidx.compose.foundation.clickable
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

/*
CircleValues :
* arguments : the values to present,the needed colors to each and the overAll size (because it will be implemented in a canvas)
outcome : representing the values inside our canvas objects

 */
@Composable
fun CircleValues(theHeight:Dp,barSize:Int,barValue:Float,textStyle: TextStyle = MaterialTheme.typography.displayLarge,
                 textColor:Color=MaterialTheme.colorScheme.onPrimary
) {
//todo need to figure out soloution for the offSet , can cause problem at competabelty
    //todo CircleValues in general include way to mauch offsets need to implement new UI ...
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
                    unitIcon =Icons.Default.AttachMoney,
                    amountColor = textColor,
                    iconColor = textColor,
                    amountTextStyle = MaterialTheme.typography.titleLarge
                )
            }

            Box(modifier = Modifier.offset(x = 56.dp)) {
                UnitDisplay(
                    amount = barSize.toFloat(),
                    unitIcon = Icons.Default.AttachMoney,
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