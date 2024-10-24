package com.mbougar.repository

import com.mbougar.model.Usuario
import jakarta.persistence.EntityManagerFactory

class UsuarioRepository(private val emf: EntityManagerFactory) {

    fun insertUsuario(usuario: Usuario) {
        val em = emf.createEntityManager()
        try {
            em.transaction.begin()
            em.persist(usuario)
            em.transaction.commit()
        } catch (e: Exception) {
            if (em.transaction.isActive) {
                em.transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            em.close()
        }
    }

    fun readUsuario(nombre: String): Usuario? {
        val em = emf.createEntityManager()
        return try {
            em.find(Usuario::class.java, nombre)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            em.close()
        }
    }

    fun readAll(): List<Usuario> {
        val em = emf.createEntityManager()
        return try {
            // En JPQL el Select * from se representa como Select a from aTable a
            val query = em.createQuery("SELECT u FROM Usuario u", Usuario::class.java)
            query.resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            em.close()
        }
    }

    fun updateUsuario(usuario: Usuario) {
        val em = emf.createEntityManager()
        try {
            em.transaction.begin()
            em.merge(usuario)
            em.transaction.commit()
        } catch (e: Exception) {
            if (em.transaction.isActive) {
                em.transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            em.close()
        }
    }

    fun deleteUsuario(usuario: Usuario) {
        val em = emf.createEntityManager()
        try {
            em.transaction.begin()
            val managedDpto = em.find(Usuario::class.java, usuario.nombreUsuario)
            if (managedDpto != null) {
                em.remove(managedDpto)
            }
            em.transaction.commit()
        } catch (e: Exception) {
            if (em.transaction.isActive) {
                em.transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            em.close()
        }
    }
}