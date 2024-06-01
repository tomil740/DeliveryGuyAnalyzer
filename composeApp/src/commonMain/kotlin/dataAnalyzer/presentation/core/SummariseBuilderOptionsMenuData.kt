package dataAnalyzer.presentation.core


/*
SummariseBuilderOptionsMenuData :
this is an helper object to get both UI menus at once , this will be used only as an return object
from the function below getOptionsMenu that is making the matched options menus
* this is const data that should be implemented one on initialization of the app
 */
data class SummariseBuilderOptionsMenuData(
    var extraOptions : List<String>,
    val deliversOptions : List<String>
)
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

