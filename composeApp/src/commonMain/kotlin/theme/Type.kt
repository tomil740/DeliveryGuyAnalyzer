package com.example.deliveryguyincomeanalyzer.android.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp



val typography1 = Typography(
    headlineSmall = TextStyle(
       // fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.sp
    ),
    displayLarge= TextStyle(
        //fontWeight = FontWeight.SemiBold,
        fontSize = 110.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.0.sp
    ),
    displaySmall =
    TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 25.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.0.sp
    ),
    titleLarge = TextStyle(
      //  fontWeight = FontWeight.SemiBold,
        fontSize = 21.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.0.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 19.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.0.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.0.sp
    )
)