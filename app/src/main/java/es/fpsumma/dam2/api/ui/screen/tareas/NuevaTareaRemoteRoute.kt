package es.fpsumma.dam2.api.ui.screen.tareas


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import es.fpsumma.dam2.api.ui.navegation.Routes
import es.fpsumma.dam2.api.viewmodel.TareasRemoteViewModel

@Composable
fun NuevaTareaRemoteRoute(
    navController: NavController,
    vm: TareasRemoteViewModel,
    modifier: Modifier = Modifier
) {
    NuevaTareaContent(
        onBack = {
            navController.popBackStack()
        },
        onSave = { titulo, descripcion ->
            vm.addTarea(titulo, descripcion)
            navController.navigate(Routes.TAREA_LISTADO) {
                popUpTo(Routes.TAREA_LISTADO) { inclusive = true }
            }
        },
        modifier = modifier
    )
}
