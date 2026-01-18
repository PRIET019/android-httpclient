package es.fpsumma.dam2.api.data.remote.api

import es.fpsumma.dam2.api.data.remote.dto.TareaCreateRequestDTO
import es.fpsumma.dam2.api.data.remote.dto.TareaDTO
import es.fpsumma.dam2.api.data.remote.dto.TareaUpdateRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TareaAPI {

    @GET("api/tareas")
    suspend fun listar(): Response<List<TareaDTO>>


        @DELETE("api/tareas/{id}")
        suspend fun deleteTarea(
            @Path("id") id: Int
        )

    @GET("tareas/{id}")
    suspend fun detalle(@Path("id") id: Int): Response<TareaDTO>



    @POST("api/tareas")
        suspend fun createTarea(
            @Body request: TareaCreateRequestDTO
        )


        @PUT("api/tareas/{id}")
        suspend fun updateTarea(
            @Path("id") id: Int,
            @Body request: TareaUpdateRequestDTO
        )




}
