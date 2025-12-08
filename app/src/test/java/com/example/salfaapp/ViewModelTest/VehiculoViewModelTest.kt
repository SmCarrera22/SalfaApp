package com.example.salfaapp.ViewModelTest

import com.example.salfaapp.domain.model.EstadoVehiculo
import com.example.salfaapp.domain.model.Sucursal
import com.example.salfaapp.domain.model.TipoVehiculo
import com.example.salfaapp.domain.model.data.entities.VehiculoEntity
import com.example.salfaapp.domain.model.data.repository.VehiculoRepository
import com.example.salfaapp.network.repository.VehiculoRemoteRepository
import com.example.salfaapp.ui.viewModel.VehiculoViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VehiculoViewModelTest {

    private val localRepo: VehiculoRepository = mockk()
    private val remoteRepo: VehiculoRemoteRepository = mockk()
    private lateinit var viewModel: VehiculoViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val fakeVehiculo = VehiculoEntity(
        id = 1,
        marca = "Toyota",
        modelo = "Corolla",
        anio = 2020,
        tipo = TipoVehiculo.SEDAN,
        patente = "ABC123",
        estado = EstadoVehiculo.Pendiente_Lavado,
        sucursal = Sucursal.Movicenter,
        tallerAsignado = null,
        observaciones = null
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = VehiculoViewModel(localRepo, remoteRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    // ----------------- Test Listado -----------------
    @Test
    fun `vehiculos list is emitted correctly`() = runTest {
        val fakeVehiculos = listOf(fakeVehiculo)
        coEvery { localRepo.getAllVehiculos() } returns flow { emit(fakeVehiculos) }

        val collected = mutableListOf<List<VehiculoEntity>>()
        val job = launch {
            viewModel.vehiculos.collect { collected.add(it) }
        }

        testDispatcher.scheduler.advanceUntilIdle()
        job.cancel()

        assertEquals(1, collected.size)
        assertEquals(fakeVehiculos, collected[0])
    }

    // ----------------- Test Insertar -----------------
    @Test
    fun `insertarVehiculo success calls onComplete`() = runTest {
        coEvery { remoteRepo.createOnServer(fakeVehiculo) } returns fakeVehiculo
        coEvery { localRepo.guardarVehiculo(fakeVehiculo) } just Awaits

        var completed = false
        viewModel.insertarVehiculo(fakeVehiculo, onComplete = { completed = true })

        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(completed)
        coVerify { localRepo.guardarVehiculo(fakeVehiculo) }
    }

    @Test
    fun `insertarVehiculo failure calls onError`() = runTest {
        val error = Exception("Network error")
        coEvery { remoteRepo.createOnServer(fakeVehiculo) } throws error

        var caught: Throwable? = null
        viewModel.insertarVehiculo(fakeVehiculo, onError = { caught = it })

        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(error, caught)
    }

    // ----------------- Test Actualizar -----------------
    @Test
    fun `actualizarVehiculo success calls onComplete`() = runTest {
        coEvery { remoteRepo.updateOnServer(fakeVehiculo) } returns fakeVehiculo
        coEvery { localRepo.guardarVehiculo(fakeVehiculo) } just Awaits

        var completed = false
        viewModel.actualizarVehiculo(fakeVehiculo, onComplete = { completed = true })

        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(completed)
        coVerify { localRepo.guardarVehiculo(fakeVehiculo) }
    }

    @Test
    fun `actualizarVehiculo failure calls onError`() = runTest {
        val error = Exception("Update error")
        coEvery { remoteRepo.updateOnServer(fakeVehiculo) } throws error

        var caught: Throwable? = null
        viewModel.actualizarVehiculo(fakeVehiculo, onError = { caught = it })

        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(error, caught)
    }

    // ----------------- Test Eliminar -----------------
    @Test
    fun `eliminarVehiculo success calls onComplete`() = runTest {
        coEvery { remoteRepo.deleteOnServer(fakeVehiculo.id) } just Runs
        coEvery { localRepo.eliminarVehiculo(fakeVehiculo) } just Runs

        var completed = false
        viewModel.eliminarVehiculo(fakeVehiculo, onComplete = { completed = true })

        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(completed)
        coVerify { localRepo.eliminarVehiculo(fakeVehiculo) }
    }

    @Test
    fun `eliminarVehiculo failure calls onError`() = runTest {
        val error = Exception("Delete error")
        coEvery { remoteRepo.deleteOnServer(fakeVehiculo.id) } throws error

        var caught: Throwable? = null
        viewModel.eliminarVehiculo(fakeVehiculo, onError = { caught = it })

        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(error, caught)
    }
}