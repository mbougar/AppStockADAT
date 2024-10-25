package com.mbougar.model

import jakarta.persistence.*
import java.time.Instant
import java.util.Date


@Entity
@Table(name = "productos")
class Producto(

    @Column(nullable = false)
    val categoria: String,

    @Column(nullable = false)
    var nombre: String,

    @Column(nullable = false)
    val descripcion: String,

    @Column(nullable = false, name = "precio_sin_iva")
    val precioSinIva: Float,

    @Column(nullable = false)
    var stock: Int,

    @ManyToOne(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinColumn(name = "proveedor_id", nullable = false)
    var proveedor: Proveedor?,

    @Column(name = "fecha_alta")
    @Temporal(TemporalType.DATE)
    val fechaAlta: Date = Date.from(Instant.now()),

    @Id
    @Column(unique = true)
    var id: String
) {
    constructor(categoria: String, nombre: String, descripcion: String, precioSinIva: Float, stock: Int, proveedor: Proveedor)
            : this(categoria, nombre, descripcion, precioSinIva, stock, proveedor, Date.from(Instant.now()), "${categoria.take(3)}${nombre.take(3)}${proveedor.nombre.take(3)}")

    val precioConIva: Float
        get() = precioSinIva * 1.21f

    override fun toString(): String {
        return "[$nombre] Precio con IVA: %.2f, Categoría: $categoria Stock: $stock, Id: $id, Fecha Alta: $fechaAlta".format(precioConIva)
    }
}