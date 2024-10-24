package com.mbougar.repository

import com.mbougar.model.Producto
import com.mbougar.model.Proveedor
import jakarta.persistence.EntityManagerFactory

class ProveedorRespository(private val emf: EntityManagerFactory) {

    fun insertProveedor(proveedor: Proveedor) {
        val em = emf.createEntityManager()
        try {
            em.transaction.begin()
            em.persist(proveedor)
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

    fun readProveedor(id: Long?): Proveedor? {
        val em = emf.createEntityManager()
        return try {
            em.find(Proveedor::class.java, id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            em.close()
        }
    }

    fun readAll(): List<Proveedor> {
        val em = emf.createEntityManager()
        return try {
            // En JPQL el Select * from se representa como Select a from aTable a
            val query = em.createQuery("SELECT p FROM Proveedor p", Proveedor::class.java)
            query.resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            em.close()
        }
    }

    fun updateProveedor(proveedor: Proveedor) {
        val em = emf.createEntityManager()
        try {

            em.transaction.begin()
            em.merge(proveedor)
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

    fun deleteProveedor(proveedor: Proveedor) {
        val em = emf.createEntityManager()
        try {
            em.transaction.begin()
            val managedDpto = em.find(Proveedor::class.java, proveedor.id)
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