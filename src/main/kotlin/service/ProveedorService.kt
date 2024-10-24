package com.mbougar.service

import com.mbougar.model.Proveedor
import com.mbougar.output.Console
import com.mbougar.repository.ProveedorRespository

class ProveedorService(private val repository: ProveedorRespository, val console: Console) {

    fun obtenerProveedores() {
        val proveedores = repository.readAll()

        if (proveedores.isEmpty()) {
            console.mostrarMensaje("No hay proveedores.", true)
        } else {
            for (proveedor in proveedores) {
                console.mostrarMensaje("$proveedor", true)
            }
        }
    }
}