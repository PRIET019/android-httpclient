package es.fpsumma.dam2.api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.fpsumma.dam2.api.data.remote.RetrofitClient
import es.fpsumma.dam2.api.data.remote.dto.TareaCreateRequestDTO
import es.fpsumma.dam2.api.data.remote.dto.TareaUpdateRequestDTO
import es.fpsumma.dam2.api.model.Tarea
import es.fpsumma.dam2.api.ui.screen.tareas.TareasUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TareasRemoteViewModel : ViewModel() {

    private val api = RetrofitClient.tareaAPI

    private val _state = MutableStateFlow(TareasUIState())
    val state: StateFlow<TareasUIState> = _state

    private val _selected = MutableStateFlow<Tarea?>(null)
    val selected: StateFlow<Tarea?> = _selected


    fun loadTareas() = viewModelScope.launch {
        _state.update { it.copy(loading = true, error = null) }
        runCatching {
            val res = api.listar()
            if (!res.isSuccessful) error("HTTP ${res.code()}")
            res.body() ?: emptyList()
        }.onSuccess { listaDto ->
            val tareas = listaDto.map { dto ->
                Tarea(dto.id, dto.titulo, dto.descripcion)
            }
            _state.update { it.copy(tareas = tareas, loading = false) }
        }.onFailure { e ->
            _state.update { it.copy(error = e.message ?: "Error cargando tareas", loading = false) }
        }
    }


    fun deleteTarea(id: Int) = viewModelScope.launch {
        try {
            api.deleteTarea(id)
            loadTareas()
        } catch (e: Exception) {
            _state.update { it.copy(error = "Error al borrar la tarea: ${e.message}") }
        }
    }




    fun addTarea(titulo: String, descripcion: String) = viewModelScope.launch {
        try {
              api.createTarea(TareaCreateRequestDTO(titulo, descripcion))
            loadTareas()
          } catch (e: Exception) {
              _state.update { it.copy(error = "Error al crear tarea: ${e.message}") }
        }
    }


    fun updateTarea(id: Int, titulo: String, descripcion: String) = viewModelScope.launch {
        try {
            api.updateTarea(id, TareaUpdateRequestDTO(titulo, descripcion))
              loadTareas()
        } catch (e: Exception) {
              _state.update { it.copy(error = "Error al actualizar tarea: ${e.message}") }
        }
    }


    fun loadTarea(id: Int) = viewModelScope.launch {
        runCatching {
            val res = api.detalle(id)
            if (!res.isSuccessful) error("HTTP ${res.code()}")
            res.body() ?: error("Sin body")
        }.onSuccess { dto ->
            _selected.value = Tarea(dto.id, dto.titulo, dto.descripcion)
        }.onFailure { e ->
            _state.update { it.copy(error = e.message ?: "Error cargando detalle") }
        }
    }
}
