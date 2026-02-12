package edu.ucne.luis_gabriel_ap2_p1.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.luis_gabriel_ap2_p1.presentation.edit.EditCervezaScreen
import edu.ucne.luis_gabriel_ap2_p1.presentation.list.ListCervezaScreen
import kotlinx.coroutines.launch

@Composable
fun CervezaNavHost(
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.CervezaList
        ) {
            composable<Screen.CervezaList> {
                ListCervezaScreen(
                    onDrawer = {
                        scope.launch { drawerState.open() }
                    },
                    onCreate = {
                        navHostController.navigate(Screen.CervezaEdit(0))
                    },
                    onEdit = { cervezaId ->
                        navHostController.navigate(Screen.CervezaEdit(cervezaId))
                    }
                )
            }

            composable<Screen.CervezaEdit> { backStackEntry ->
                val args = backStackEntry.toRoute<Screen.CervezaEdit>()
                EditCervezaScreen(
                    cervezaId = if (args.cervezaId == 0) null else args.cervezaId,
                    onDrawer = {
                        scope.launch { drawerState.open() }
                    },
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }
        }
    }
}