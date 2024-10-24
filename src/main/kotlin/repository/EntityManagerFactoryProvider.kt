package com.mbougar.repository

import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence

object EntityManagerFactoryProvider {
    private val emf: EntityManagerFactory = Persistence.createEntityManagerFactory("unidadMySQL")

    fun getEntityManagerFactory(): EntityManagerFactory {
        return emf
    }

    fun close() {
        if (emf.isOpen) {
            emf.close()
        }
    }
}