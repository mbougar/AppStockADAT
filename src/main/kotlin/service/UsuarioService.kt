package com.mbougar.service

import com.mbougar.model.Usuario
import com.mbougar.output.Console
import com.mbougar.repository.UsuarioRepository

class UsuarioService(val repository: UsuarioRepository, val console: Console) {

    fun crearUsuario() {
        val nombre = console.pedirString("Introduzca el nuevo nombre de usuario: ", false, 0, 20)
        val password = console.pedirString("Introduzca la contraseña de usuario: ", false, 0, 20)
        val usuario = repository.readUsuario(nombre)
        if (usuario == null) {
            repository.insertUsuario(
                Usuario(nombre, password)
            )
        }
    }

    fun comprobarUsuario(): Boolean {
        val nombre = console.pedirString("Introduzca el nuevo nombre de usuario: ", false, 0, 20)
        val password = console.pedirString("Introduzca la contraseña de usuario: ", false, 0, 20)
        val usuario = repository.readUsuario(nombre)
        return if (usuario != null) {
            usuario.password == password
        } else {
            console.mostrarMensaje("Usuario o contraseña incorrectos.", false)
            false
        }
    }
}