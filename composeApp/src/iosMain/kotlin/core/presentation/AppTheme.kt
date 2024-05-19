package org.example.deliveryguyanalyzer.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTheme
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.Theme
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import io.github.alexzhirkevich.cupertino.theme.darkColorScheme
import io.github.alexzhirkevich.cupertino.theme.lightColorScheme

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
){
    /*
    Theme.MaterialTheme(
        colorScheme = if(darkTheme) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        shapes = Shapes,
        content = content
    )

     */
    AdaptiveTheme(
        material = {
            MaterialTheme(
                colorScheme = if (darkTheme) {
                    androidx.compose.material3.darkColorScheme()
                } else {
                    androidx.compose.material3.lightColorScheme()
                },
                content = it
            )
        },
        cupertino = {
            CupertinoTheme(
                colorScheme = if (darkTheme) {
                    darkColorScheme()
                } else {
                    lightColorScheme()
                },
                content = it
            )

        },
        target =  Theme.Cupertino,
        content = content
    )
}
/*

}

 */