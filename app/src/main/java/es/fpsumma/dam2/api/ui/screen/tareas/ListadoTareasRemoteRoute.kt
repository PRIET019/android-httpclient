package es.fpsumma.dam2.api.ui.screen.tareas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import es.fpsumma.dam2.api.ui.navegation.Routes
import es.fpsumma.dam2.api.viewmodel.TareasRemoteViewModel

@Composable
fun ListadoTareasRemoteRoute(
    navController: NavHostController,
    vm: TareasRemoteViewModel
) {
    LaunchedEffect(Unit) {
        vm.loadTareas()
    }

    val state = vm.uiState.collectAsState()

    ListadoTareasContent(
        state = state.value,
        onBack = {
            navController.popBackStack()
        },
        onAdd = {
            navController.navigate(Routes.TAREA_ADD)
        },
        onOpenDetalle = { id ->
            navController.navigate(Routes.tareaView(id))
        },
        onDelete = {}, //
    )
}
