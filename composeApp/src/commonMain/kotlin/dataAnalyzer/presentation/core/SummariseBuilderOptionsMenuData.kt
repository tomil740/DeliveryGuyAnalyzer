package dataAnalyzer.presentation.core

data class SummariseBuilderOptionsMenuData(
    var extraOptions : List<String>,
    val deliversOptions : List<String>
){

}

fun getOptionsMenu():SummariseBuilderOptionsMenuData{
    val extraMenu : MutableList<String> = mutableListOf("0")
    for (i in 1..300){
        extraMenu.add("${(i*5)}$")
    }

    val deliversMenu : MutableList<String> = mutableListOf()
    for (i in 1..300){
        deliversMenu.add("$i")
    }

    return SummariseBuilderOptionsMenuData(extraMenu,deliversMenu)

}

