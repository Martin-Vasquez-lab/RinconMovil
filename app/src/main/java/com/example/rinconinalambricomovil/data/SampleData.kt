package com.example.rinconinalambricomovil.data

import com.example.rinconinalambricomovil.model.Producto
import com.example.rinconinalambricomovil.model.Categoria
import com.example.rinconinalambricomovil.R

object SampleData {

    val productos = listOf(
        // ===== MANGA =====
        Producto(1, "One Piece Vol. 1", 8990.0, "Edición en español", Categoria.MANGA, R.drawable.onepiece),
        Producto(2, "Jujutsu Kaisen Vol. 2", 8990.0, "Papel premium", Categoria.MANGA, R.drawable.jujutsukaisen),
        Producto(3, "Tokyo Ghoul Vol. 1", 7990.0, "Edición estándar", Categoria.MANGA, R.drawable.tokyoghoul),
        Producto(4, "Chainsaw Man Vol. 3", 8990.0, "Incluye sobrecubierta", Categoria.MANGA, R.drawable.chainsawman),
        Producto(5, "My Hero Academia Vol. 37", 8490.0, "Versión coleccionista", Categoria.MANGA, R.drawable.bokunocaca),
        Producto(6, "Demon Slayer Vol. 7", 8990.0, "Edición en español", Categoria.MANGA, R.drawable.kimetsunopapu),
        Producto(7, "Hunter x Hunter Vol. 10", 8990.0, "Papel blanco", Categoria.MANGA, R.drawable.cunterxhunter),
        Producto(8, "Spy x Family Vol. 1", 8990.0, "Primera edición", Categoria.MANGA, R.drawable.spyequis),
        Producto(9, "Fullmetal Alchemist Vol. 3", 8990.0, "Edición tankōbon", Categoria.MANGA, R.drawable.fullmetal),
        Producto(10, "Blue Lock Vol. 2", 8990.0, "Edición en español", Categoria.MANGA, R.drawable.bluecock),

        // ===== AUDÍFONOS =====
        Producto(11, "Audífonos BT Pro", 29990.0, "ANC + 30h batería", Categoria.AUDIFONOS, R.drawable.aduifonosbt),
        Producto(12, "Headset Gamer X", 49990.0, "7.1 virtual RGB", Categoria.AUDIFONOS, R.drawable.audifonosgameequis),
        Producto(13, "Auriculares In-Ear Sport", 19990.0, "Resistentes al sudor", Categoria.AUDIFONOS, R.drawable.audifonosearsport),
        Producto(14, "Audífonos Studio Monitor", 59990.0, "Respuesta plana", Categoria.AUDIFONOS, R.drawable.audifonosstudiomonitor),
        Producto(15, "Audífonos Gamer con Micrófono", 34990.0, "Micrófono desmontable", Categoria.AUDIFONOS, R.drawable.audifonosgamerconaudifono),
        Producto(16, "Earbuds True Wireless Lite", 24990.0, "Estuche compacto", Categoria.AUDIFONOS, R.drawable.audifonostruewireless),
        Producto(17, "Audífonos Over-Ear Classic", 27990.0, "Diseño retro", Categoria.AUDIFONOS, R.drawable.audfionosoverearclassic),
        Producto(18, "Headset Streaming Pro", 54990.0, "Optimizado para streaming", Categoria.AUDIFONOS, R.drawable.audifonosstreamingpro),
        Producto(19, "Audífonos BT con NFC", 32990.0, "Emparejamiento rápido", Categoria.AUDIFONOS, R.drawable.audifonosbtnfc),
        Producto(20, "Auriculares In-Ear Básicos", 9990.0, "Buena relación calidad/precio", Categoria.AUDIFONOS, R.drawable.audifonosinearbasicos),

        // ===== FIGURAS =====
        Producto(21, "Figura Tanjiro 18cm", 24990.0, "PVC pintado a mano", Categoria.FIGURAS, R.drawable.tanjirofigura),
        Producto(22, "Figura Zelda 20cm", 29990.0, "Edición coleccionista", Categoria.FIGURAS, R.drawable.zeldafigura),
        Producto(23, "Figura Luffy", 34990.0, "Base incluida", Categoria.FIGURAS, R.drawable.elunopieza),
        Producto(24, "Figura Gojo Satoru", 32990.0, "Detalles premium", Categoria.FIGURAS, R.drawable.elmenosfuerte),
        Producto(25, "Figura Mikasa Ackerman", 31990.0, "Pose dinámica", Categoria.FIGURAS, R.drawable.figuramickasa),
        Producto(26, "Figura Deku 16cm", 21990.0, "My Hero Academia", Categoria.FIGURAS, R.drawable.dekufigura),
        Producto(27, "Figura Nezuko Box", 28990.0, "Incluye caja de transporte", Categoria.FIGURAS, R.drawable.figuranezuko),
        Producto(28, "Figura Zoro Roronoa", 30990.0, "Con tres espadas", Categoria.FIGURAS, R.drawable.figurazoro),
        Producto(29, "Figura Kirito SAO", 25990.0, "Con espada Elucidator", Categoria.FIGURAS, R.drawable.figurakitio),
        Producto(30, "Figura Naruto Sage Mode", 33990.0, "Base temática", Categoria.FIGURAS, R.drawable.figuranaruto),

        // ===== CONSOLAS / ACCESORIOS =====
        Producto(31, "Consola Pocket Retro", 39990.0, "5k juegos retro", Categoria.CONSOLAS, R.drawable.consolapocketretro),
        Producto(32, "Control Pro Inalámbrico", 19990.0, "BT 5.0 + vibración", Categoria.CONSOLAS, R.drawable.controlproinalambrico),
        Producto(33, "Arcade Stick Compacto", 44990.0, "Compatible PC/Consola", Categoria.CONSOLAS, R.drawable.arcadestickcompacto),
        Producto(34, "Base Carga Mandos", 14990.0, "Para 2 controles", Categoria.CONSOLAS, R.drawable.basecargarmandos),
        Producto(35, "Adaptador HDMI Retro", 12990.0, "Conecta consolas antiguas", Categoria.CONSOLAS, R.drawable.adaptadorhdmiretro),
        Producto(36, "Control Clásico USB", 9990.0, "Estilo SNES", Categoria.CONSOLAS, R.drawable.controlclasicousb),
        Producto(37, "Soporte Vertical Consola", 11990.0, "Ahorro de espacio", Categoria.CONSOLAS, R.drawable.soporteverticalconsola),
        Producto(38, "Cable HDMI 4K 2m", 7990.0, "Alta velocidad", Categoria.CONSOLAS, R.drawable.cablehdmi),
        Producto(39, "Control Inalámbrico Retro", 18990.0, "Diseño clásico", Categoria.CONSOLAS, R.drawable.controlinalambricoretro),
        Producto(40, "Kit Limpieza Consolas", 6990.0, "Microfibra + spray", Categoria.CONSOLAS, R.drawable.kitlimpiezaconsolas)
    )

    fun porCategoria(cat: Categoria) = productos.filter { it.categoria == cat }
}
