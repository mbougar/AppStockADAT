package com.mbougar.repository

import com.mbougar.model.Producto
import com.mbougar.model.Proveedor
import jakarta.persistence.EntityManagerFactory

class ProductoRepository(private val emf: EntityManagerFactory) {

    fun insertProducto(producto: Producto) {
        val em = emf.createEntityManager()
        try {
            em.transaction.begin()
            em.persist(producto)
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

    fun readProducto(id: String): Producto? {
        val em = emf.createEntityManager()
        return try {
            em.find(Producto::class.java, id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            em.close()
        }
    }

    fun readProveedor(id: String): Proveedor? {
        val em = emf.createEntityManager()
        return try {
            val producto = em.find(Producto::class.java, id)
            producto.proveedor
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            em.close()
        }
    }

    fun readProductoSinStock(): List<Producto> {
        val em = emf.createEntityManager()
        return try {
            // JPQL query to select products with stock greater than 0
            val query = em.createQuery("SELECT p FROM Producto p WHERE p.stock = 0", Producto::class.java)
            query.resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            em.close()
        }
    }

    fun readProveedorProducto(id: String): Proveedor? {
        val em = emf.createEntityManager()
        return try{
            val producto = em.find(Producto::class.java, id)
            producto.proveedor
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            em.close()
        }
    }

    fun readProductoConStock(): List<Producto> {
        val em = emf.createEntityManager()
        return try {
            // JPQL query to select products with stock greater than 0
            val query = em.createQuery("SELECT p FROM Producto p WHERE p.stock > 0", Producto::class.java)
            query.resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            em.close()
        }
    }

    fun readAll(): List<Producto> {
        val em = emf.createEntityManager()
        return try {
            // En JPQL el Select * from se representa como Select a from aTable a
            val query = em.createQuery("SELECT p FROM Producto p", Producto::class.java)
            query.resultList
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        } finally {
            em.close()
        }
    }

    fun updateProductoNombre(id: String, nuevoNombre: String) {
        val em = emf.createEntityManager()
        try {
            val producto = readProducto(id)
            if (producto != null) {
                producto.nombre = nuevoNombre
                em.transaction.begin()
                em.merge(producto)
                em.transaction.commit()
            }
        } catch (e: Exception) {
            if (em.transaction.isActive) {
                em.transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            em.close()
        }
    }

    fun updateProductoStock(id: String, nuevoStock: Int) {
        val em = emf.createEntityManager()
        try {
            val producto = readProducto(id)
            if (producto != null) {
                producto.stock = nuevoStock
                em.transaction.begin()
                em.merge(producto)
                em.transaction.commit()
            }
        } catch (e: Exception) {
            if (em.transaction.isActive) {
                em.transaction.rollback()
            }
            e.printStackTrace()
        } finally {
            em.close()
        }
    }

    fun deleteProducto(producto: Producto) {
        val em = emf.createEntityManager()
        try {
            em.transaction.begin()
            val managedDpto = em.find(Producto::class.java, producto.id)
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