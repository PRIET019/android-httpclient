package es.fpsumma.dam2.api.ui.navegation

object Routes {
    const val HOME = "home"
    const val TAREA_LISTADO = "tareas_listado"
    const val TAREA_LISTADO_API = "tarea_listado_api"
    const val TAREA_ADD = "tareas_nueva"
    const val TAREA_VIEW = "tareas_detalle/{id}"

    const val NUEVA_TAREA_REMOTE = "nueva_tarea_remote"

    const val DETALLE_TAREA_REMOTE = "detalle_tarea_remote/{id}"

    fun tareaView(id: Int): String {
        return "tareas/detalle/$id"
    }

}

