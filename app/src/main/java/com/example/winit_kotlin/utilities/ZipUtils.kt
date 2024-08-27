package com.example.winit_kotlin.utilities

import com.example.winit_kotlin.databaseaccess.DatabaseHelper
import com.google.android.gms.common.util.IOUtils.copyStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset
import java.util.zip.ZipFile

class ZipUtils {



    companion object{
        var tag: String = "ZipUtils"

        val BUFF_SIZE : Int = 1024 * 1024 // 1M Byte
        @Throws(IOException::class)
        fun upZipFile(zipFile: File, folderPath: String) {
            val desDir = File(folderPath)
            if (!desDir.exists()) {
                desDir.mkdirs()
            }

            ZipFile(zipFile).use { zf ->
                val buffer = ByteArray(BUFF_SIZE)
                zf.entries().asSequence().forEach { entry ->
                    val inputStream = zf.getInputStream(entry)
                    val str = folderPath + entry.name
                    val desFile = File(String(str.toByteArray(Charset.forName("8859_1")), Charset.forName("GB2312")))

                    if (!desFile.exists()) {
                        val fileParentDir = desFile.parentFile
                        if (!fileParentDir.exists()) {
                            fileParentDir.mkdirs()
                        }
                        desFile.createNewFile()
                    }

                    FileOutputStream(desFile).use { outputStream ->
                        copyStream(inputStream, outputStream)
                    }

                    val salesman = File(folderPath, "salesman.sqlite")
                    if (desFile.exists()) {
                        desFile.renameTo(salesman)
                    }

                    DatabaseHelper.createTables()
                }
            }
        }
        @Throws(IOException::class)
        fun upZipFileCPS(zipFile: File, folderPath: String) {
            val desDir = File(folderPath)
            if (!desDir.exists()) {
                desDir.mkdirs()
            }

            ZipFile(zipFile).use { zf ->
                val buffer = ByteArray(BUFF_SIZE)
                zf.entries().asSequence().forEach { entry ->
                    val inputStream = zf.getInputStream(entry)
                    val str = folderPath + entry.name
                    val desFile = File(String(str.toByteArray(Charset.forName("8859_1")), Charset.forName("GB2312")))

                    if (!desFile.exists()) {
                        val fileParentDir = desFile.parentFile
                        if (!fileParentDir.exists()) {
                            fileParentDir.mkdirs()
                        }
                        desFile.createNewFile()
                    }

                    FileOutputStream(desFile).use { outputStream ->
                        copyStream(inputStream, outputStream)
                    }

                    val salesman = File(folderPath, "salesmancpsdetails.sqlite")
                    if (desFile.exists()) {
                        desFile.renameTo(salesman)
                    }

                    DatabaseHelper.createTables()
                }
            }
        }
    }
}