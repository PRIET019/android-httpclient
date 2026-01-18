package es.fpsumma.dam2.api.ui.navegation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import es.fpsumma.dam2.api.ui.screen.tareas.DetalleTareaRemoteRoute
import es.fpsumma.dam2.api.ui.screen.tareas.DetalleTareaScreen
import es.fpsumma.dam2.api.ui.screen.tareas.ListadoTareasRoomRoute
import es.fpsumma.dam2.api.ui.screen.tareas.ListadoTareasScreen
import es.fpsumma.dam2.api.ui.screen.tareas.NuevaTareaRemoteRoute
import es.fpsumma.dam2.api.ui.screen.tareas.NuevaTareaScreen

import es.fpsumma.dam2.api.viewmodel.TareasViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    tareasViewModel: TareasViewModel,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.TAREA_LISTADO_API
    ) {
        composable(Routes.TAREA_LISTADO) {
            ListadoTareasScreen(navController, tareasViewModel )
        }
        composable(Routes.TAREA_LISTADO_API) {
            ListadoTareasRoomRoute(
                navController = navController,
                tareasViewModel
            )
        }


        composable(Routes.TAREA_ADD) {
            NuevaTareaScreen(navController, tareasViewModel)
        }

        composable(route = Routes.TAREA_VIEW,arguments = listOf(navArgument("id")
        { type = NavType.IntType })
        ) { backStackEntry ->
            DetalleTareaScreen(
                id = backStackEntry.arguments?.getInt("id") ?: 0,
                navController = navController,
                tareasViewModel
            )
        }

        composable(Routes.NUEVA_TAREA_REMOTE) {
            NuevaTareaRemoteRoute(
                navController = navController,
                vm = viewModel(),
                modifier
            )
        }

        composable(route = Routes.DETALLE_TAREA_REMOTE,arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments!!.getInt("id")

            DetalleTareaRemoteRoute(
                navController = navController,
                id = id,
                vm = viewModel(),
                modifier = modifier
            )
        }
    }
}
