import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import dataAnalyzer.data.localDb.MongoDB
import dataAnalyzer.data.repository.RepositoryImpl
import dataAnalyzer.domain.repository.Repository
import dataAnalyzer.domain.useCase.GetAllWorkDeclares
import dataAnalyzer.domain.useCase.InsertWorkDeclare
import dataAnalyzer.domain.useCase.ObjectItemUseCases
import dataAnalyzer.domain.useCase.SumDomainData
import dataAnalyzer.domain.useCase.SummariseBuilderUseCases
import dataAnalyzer.presentation.objectItemScreen.ObjectItemScreenClass
import dataAnalyzer.presentation.objectItemScreen.ObjectItemViewmodel
import dataAnalyzer.presentation.summariseDeclareBuilderScreen.SummariseDeclareBuilderViewmodel
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveScaffold
import org.example.deliveryguyanalyzer.core.presentation.AppTheme
import org.koin.dsl.module
import org.koin.core.context.startKoin



@Composable
fun App(
    darkTheme: Boolean =false,
    dynamicColor: Boolean=true
) {
   // initializeKoin()

    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        AdaptiveScaffold(containerColor = Color.Red,topBar = { Text("wrewrwefrewf") }) {



                Text("working")
            }
        }
/*
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Navigator(ObjectItemScreenClass())
        }

 */
    }
val mongoModule = module {
    single { MongoDB() }
    single<Repository> { RepositoryImpl(get()) }
    single { ObjectItemUseCases(
        getAllWorkDeclares = GetAllWorkDeclares(get()),
        sumDomainData = SumDomainData()
    ) }
    single { SummariseBuilderUseCases(
        insertWorkDeclare = InsertWorkDeclare(get()),
        ) }
    factory { ObjectItemViewmodel(get()) }
    factory { SummariseDeclareBuilderViewmodel(get()) }


}

fun initializeKoin() {
    startKoin {
        modules(mongoModule)
    }
}