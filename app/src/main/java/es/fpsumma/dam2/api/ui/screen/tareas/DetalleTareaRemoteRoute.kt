package es.fpsumma.dam2.api.ui.screen.tareas


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import es.fpsumma.dam2.api.viewmodel.TareasRemoteViewModel
@Composable
fun DetalleTareaRemoteRoute(
    navController: NavController,
    id: Int,
    vm: TareasRemoteViewModel,
    modifier: Modifier = Modifier
) {
    val tarea by vm.selected.collectAsState()

    LaunchedEffect(id) {
        vm.loadTarea(id)
    }

    if (tarea == null) return

    DetalleTareaContent(
        tarea = tarea,
        onBack = { navController.popBackStack() },
        onSave = { titulo, descripcion ->
            vm.updateTarea(id, titulo, descripcion)
            navController.popBackStack()
        },
        modifier = modifier
    )
}

