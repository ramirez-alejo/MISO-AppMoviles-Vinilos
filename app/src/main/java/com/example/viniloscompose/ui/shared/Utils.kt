package com.example.viniloscompose.ui.shared

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertirFormatoFecha(fechaCadena: String): String {
    // Formato de la cadena de fecha de entrada
    val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

    // Formato de la cadena de fecha de salida
    val formatoSalida = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Parsear la cadena de fecha y formatearla
    val fecha = formatoEntrada.parse(fechaCadena)
    return formatoSalida.format(fecha as Date)
}