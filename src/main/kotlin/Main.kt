package com.mbougar

import com.mbougar.model.Producto
import com.mbougar.model.Proveedor
import com.mbougar.output.Console
import com.mbougar.repository.EntityManagerFactoryProvider
import com.mbougar.repository.ProductoRepository
import com.mbougar.repository.ProveedorRespository
import com.mbougar.repository.UsuarioRepository
import com.mbougar.service.ProductoService
import com.mbougar.service.ProveedorService
import com.mbougar.service.UsuarioService

fun main() {
    val emf = EntityManagerFactoryProvider.getEntityManagerFactory()
    val productoRepository = ProductoRepository(emf)
    val proveedorRespository = ProveedorRespository(emf)
    val usuarioRepository = UsuarioRepository(emf)
    val console = Console()
    val productoService = ProductoService(productoRepository, proveedorRespository, console)
    val proveedorService = ProveedorService(proveedorRespository, console)
    val usuarioService = UsuarioService(usuarioRepository, console)

    val proveedor1 = Proveedor("Empresas Bouza", "Calle 1")
    val proveedor2 = Proveedor("Empresas Perez", "Calle 2")
    proveedorRespository.insertProveedor(proveedor1)
    proveedorRespository.insertProveedor(proveedor2)
    
    login(usuarioService)

    var opcion: Int
    do {
        opcion = menu()
        when(opcion) {
            1 -> productoService.darAltaProducto()
            2 -> productoService.darBajaProducto()
            3 -> productoService.modificarNombreProducto()
            4 -> productoService.modificarStockProducto()
            5 -> productoService.obtenerUnProducto()
            6 -> productoService.obtenerProductosConStock()
            7 -> productoService.obtenerProductosSinStock()
            8 -> productoService.obtenerProveedorProducto()
            9 -> proveedorService.obtenerProveedores()
        }
    } while (opcion != 0)

    EntityManagerFactoryProvider.close()
}

fun menu(): Int {
    println("""Menú:
    1. Alta Producto
    2. Baja Producto
    3. Modificar nombre producto
    4. Modificar stock producto
    5. Obtener un producto
    6. Obtener productos en stock
    7. Obtener productos sin stock
    8. Obtener proveedor de un producto
    9. Obtener proveedores
    0. Salir del programa""")
    return pedirOpcion("Seleccione una opción: ", 9)
}

fun pedirOpcion(mensaje: String = "Seleccione una opción: ", maxRango: Int): Int {

    var opcion: Int
    do {
        opcion = try {
            print(mensaje)
            readln().toInt()
        } catch (e: NumberFormatException) {
            println("Error, el valor introducido no es un número entero entre 0 y $maxRango.")
            -1
        }
    } while (opcion !in 0..maxRango)

    return opcion
}

fun login(usuarioService: UsuarioService) {

    var opcion: Int
    var login = false
    do {
        println("Login:")
        println("1. Crear Usuario")
        println("2. Entrar con mi usuario.")
        println("0. Salir.")
        opcion = pedirOpcion("Seleccione una opción: ", 2)

        when(opcion) {
            1 -> usuarioService.crearUsuario()
            2 -> login = usuarioService.comprobarUsuario()
        }

    } while (!login)
}