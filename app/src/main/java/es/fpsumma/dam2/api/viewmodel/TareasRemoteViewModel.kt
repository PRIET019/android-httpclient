package es.fpsumma.dam2.api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fpsumma.dam2.api.data.remote.RetrofitClient
import es.fpsumma.dam2.api.model.Tarea
import es.fpsumma.dam2.api.ui.screen.tareas.TareasUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel "remoto": obtiene datos desde una API (Retrofit) y expone un estado
 * que la UI (Compose) puede observar.
 *
 * La UI NO llama a Retrofit directamente: solo observa `state`.
 */
class TareasRemoteViewModel : ViewModel() {

    // Cliente Retrofit ya configurado (baseUrl + converter).
    // Aquí obtenemos la interfaz con los endpoints (GET/POST/PUT/DELETE, etc.)
    private val api = RetrofitClient.tareaAPI

    /**
     * Estado interno (mutable) del ViewModel.
     * Lo mantenemos privado para que SOLO el ViewModel pueda modificarlo.
     */
    private val _state = MutableStateFlow(TareasUIState())

    /**
     * Estado público (solo lectura). La UI se suscribe a este StateFlow:
     * - con collectAsState() en Compose
     * - o con collect en otras capas
     */
    val state: StateFlow<TareasUIState> = _state

    /**
     * Carga el listado de tareas desde la API.
     */
    fun loadTareas() = viewModelScope.launch {
        _state.update { current ->
            current.copy(loading = true, error = null)
        }

        runCatching {
            val res = api.listar()
            if (!res.isSuccessful) error("HTTP ${res.code()}")
            res.body() ?: emptyList()
        }.onSuccess { listaDto ->
            val tareas = listaDto.map { dto ->
                Tarea(
                    id = dto.id,
                    titulo = dto.titulo,
                    descripcion = dto.descripcion
                )
            }

            _state.update { current ->
                current.copy(tareas = tareas, loading = false)
            }
        }.onFailure { e ->
            _state.update { current ->
                current.copy(
                    error = e.message ?: "Error cargando tareas",
                    loading = false
                )
            }
        }
    }

    /**
     * Elimina una tarea por id.
     */
    fun eliminarTarea(id: Int) = viewModelScope.launch {
        _state.update { current ->
            current.copy(loading = true, error = null)
        }

        runCatching {
            val res = api.eliminar(id.toLong())
            if (!res.isSuccessful) error("HTTP ${res.code()}")
        }.onSuccess {
            // Volvemos a cargar la lista para reflejar los cambios
            loadTareas()
        }.onFailure { e ->
            _state.update { current ->
                current.copy(
                    error = e.message ?: "Error eliminando la tarea",
                    loading = false
                )
            }
        }
    }
}
