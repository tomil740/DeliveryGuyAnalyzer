import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import dataAnalyzer.data.localDb.MongoDB
import dataAnalyzer.data.repository.RepositoryImpl
import dataAnalyzer.domain.repository.Repository
import dataAnalyzer.domain.useCase.GetAllWorkDeclares
import dataAnalyzer.domain.useCase.GetCurrentMonthDeclares
import dataAnalyzer.domain.useCase.InsertWorkDeclare
import dataAnalyzer.domain.useCase.screenUsecases.ObjectItemUseCases
import dataAnalyzer.domain.useCase.SumDomainData
import dataAnalyzer.domain.useCase.screenUsecases.SummariseBuilderUseCases
import dataAnalyzer.presentation.objectItemScreen.ObjectItemScreenClass
import dataAnalyzer.presentation.objectItemScreen.ObjectItemViewmodel
import dataAnalyzer.presentation.summariseDeclareBuilderScreen.SummariseDeclareBuilderViewmodel
import org.example.deliveryguyanalyzer.core.presentation.AppTheme
import org.koin.core.context.startKoin
import org.koin.dsl.module


@Composable
fun App(
    darkTheme: Boolean =false,
    dynamicColor: Boolean=true
) {
    initializeKoin()

    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            println("the color IOS is ${MaterialTheme.colorScheme.background}")
            Navigator(ObjectItemScreenClass())
        }


    }
}
    val mongoModule = module {
        single { MongoDB() }
        single<Repository> { RepositoryImpl(get()) }
        single {
            ObjectItemUseCases(
                getAllWorkDeclares = GetAllWorkDeclares(get()),
                sumDomainData = SumDomainData(),
                getCurrentMonthDeclares = GetCurrentMonthDeclares(get())
            )
        }
        single {
            SummariseBuilderUseCases(
                insertWorkDeclare = InsertWorkDeclare(get()),
            )
        }
        factory { ObjectItemViewmodel(get()) }
        factory { SummariseDeclareBuilderViewmodel(get()) }


    }

    fun initializeKoin() {
        startKoin {
            modules(mongoModule)
        }
    }
