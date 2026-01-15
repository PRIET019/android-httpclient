package es.fpsumma.dam2.api.ui.screen.tareas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import es.fpsumma.dam2.api.model.Tarea
import es.fpsumma.dam2.api.viewmodel.TareasViewModel
@Composable
fun DetalleTareaRoomRoute(
    id: Int,
    navController: NavHostController,
    vm: TareasViewModel
) {
    val tareaState = vm.getTarea(id).collectAsState()

    tareaState.value?.let {
        val tarea = Tarea(
            it.id,
            it.titulo,
            it.descripcion
        )

        DetalleTareaContent(
            tarea = tarea,
            onSave = { titulo, descripcion ->
                vm.updateTarea(id, titulo, descripcion)
                navController.popBackStack()
            },
            onBack = {
                navController.popBackStack()
            }
        )
    }
}
