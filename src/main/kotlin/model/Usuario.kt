package com.mbougar.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "usuarios")
class Usuario(

    @Id
    @Column(name = "nombre_usuario")
    val nombreUsuario: String,

    @Column(nullable = false, length = 20)
    var password: String
) {
}