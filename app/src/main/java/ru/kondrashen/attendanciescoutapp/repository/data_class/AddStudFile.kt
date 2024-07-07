package ru.kondrashen.attendanciescoutapp.repository.data_class

import okhttp3.RequestBody
import java.io.File

data class AddStudFile(
    val fileName: String,
    val file: ByteArray
)
