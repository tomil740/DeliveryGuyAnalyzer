package dataAnalyzer.presentation.util

import androidx.compose.ui.text.intl.Locale
import deliveryguyanalyzer.composeapp.generated.resources.Res
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.format.MonthNames

fun DayOfWeek.toPresent():String{

    if (Locale.current.language == "iw" ||Locale.current.language == "he") {
        return when (this) {
            DayOfWeek.MONDAY ->  "שני"
            DayOfWeek.TUESDAY -> "שלישי"
            DayOfWeek.WEDNESDAY -> "רביעי"
            DayOfWeek.THURSDAY -> "חמישי"
            DayOfWeek.FRIDAY -> "שישי"
            DayOfWeek.SATURDAY -> "שבת"
            DayOfWeek.SUNDAY -> "ראשון"
            else -> "לא ידוע"
        }
    }else{
        return this.name
    }
}

fun Month.toPresent():String{
    if (Locale.current.language == "iw" ||Locale.current.language == "he") {
         return when (this) {
             Month.JANUARY -> "ינואר"
             Month.FEBRUARY ->  "פבואר"
             Month.MARCH ->  "מרץ"
             Month.APRIL ->  "אפריל"
             Month.MAY ->  "מאי"
             Month.JUNE ->  "יוני"
             Month.JULY ->  "יולי"
             Month.AUGUST ->  "אוגאוסט"
             Month.SEPTEMBER -> "ספטמבר"
             Month.OCTOBER ->  "אוקטובר"
             Month.NOVEMBER -> "נובמבר"
             Month.DECEMBER ->  "דצמבר"
             else ->  "לא ידוע"
         }
    }else{
        return this.name
    }
}