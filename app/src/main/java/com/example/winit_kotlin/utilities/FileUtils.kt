package com.example.winit_kotlin.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.os.PowerManager
import android.webkit.DownloadListener
import com.example.winit_kotlin.utils.isNetworkConnectionAvailable
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class FileUtils {
    interface DownloadListner{
        fun onProgress(count: Int)
        fun onComplete()
        fun onError()
    }
    companion object{


        @SuppressLint("InvalidWakeLockTag")
        @Synchronized
        fun downloadSQLITE(
            sUrl: String?,
            directory: String,
            context: Context,
            sqliteName: String,
            downloadListener: DownloadListner
        ): String? {
            var url = sUrl
            if (url != null) {
                if (!url.contains(".zip"))
                    url = url.replace("sqlite", "zip")
            }

            System.gc()

            if (url != null) {
                if (url.isEmpty()) return null
            }

            val sqliteFilePath = "$sqliteName.zip"
            val file = File(directory, sqliteFilePath)

            if (file.exists()) file.delete()

            val localFilePath = directory + sqliteFilePath
            var count = 0

            try {
                val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                val wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag")
                if (url != null) {
                    url = url.replace(" ", "%20")
                }

                var input: InputStream? = null
                var output: OutputStream? = null
                var connection: HttpURLConnection? = null

                try {
                    val urlObj = URL(url)
                    connection = urlObj.openConnection() as HttpURLConnection
                    connection.connect()

                    if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                        return null
                    }

                    val fileLength = connection.contentLength
                    input = connection.inputStream
                    output = FileOutputStream(localFilePath)

                    val data = ByteArray(1024)
                    var total: Long = 0
                    var bytesRead: Int

                    while (input.read(data).also { bytesRead = it } != -1) {
                        if (isNetworkConnectionAvailable(context)) {
                            total += bytesRead
                            if (fileLength > 0)
                                downloadListener.onProgress((total * 100 / fileLength).toInt())
                            output.write(data, 0, bytesRead)
                        } else {
                            input.closeQuietly()
                            output.closeQuietly()
                            connection?.disconnect()
                            return null
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                } finally {
                    input.closeQuietly()
                    output.closeQuietly()
                    connection?.disconnect()
                }

                ZipUtils.upZipFile(file, directory)
                downloadListener.onComplete()
                return localFilePath

            } catch (e: Exception) {
                file.delete()

                if (count < 3) {
                    count++
                    e.printStackTrace()
                    return downloadSQLITE(url, directory, context, sqliteName, downloadListener)
                } else {
                    count = 0
                    downloadListener.onError()
                    return null
                }
            }
        }

        fun InputStream?.closeQuietly() {
            try {
                this?.close()
            } catch (ignored: IOException) {}
        }

        fun OutputStream?.closeQuietly() {
            try {
                this?.close()
            } catch (ignored: IOException) {}
        }



        @SuppressLint("InvalidWakeLockTag")
        @Synchronized
        fun downloadSQLITECPS(
            sUrl: String?,
            directory: String,
            context: Context,
            sqliteName: String,
            downloadListener: DownloadListner
        ): String? {
            var url = sUrl
            if (url != null) {
                if (!url.contains(".zip"))
                    url = url.replace("sqlite", "zip")
            }

            System.gc()

            if (url != null) {
                if (url.isEmpty()) return null
            }

            val sqliteFilePath = "$sqliteName.zip"
            val file = File(directory, sqliteFilePath)

            if (file.exists()) file.delete()

            val localFilePath = directory + sqliteFilePath
            var count = 0

            try {
                val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                val wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag")
                if (url != null) {
                    url = url.replace(" ", "%20")
                }

                var input: InputStream? = null
                var output: OutputStream? = null
                var connection: HttpURLConnection? = null

                try {
                    val urlObj = URL(url)
                    connection = urlObj.openConnection() as HttpURLConnection
                    connection.connect()

                    if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                        return null
                    }

                    val fileLength = connection.contentLength
                    input = connection.inputStream
                    output = FileOutputStream(localFilePath)

                    val data = ByteArray(1024)
                    var total: Long = 0
                    var bytesRead: Int

                    while (input.read(data).also { bytesRead = it } != -1) {
                        if (isNetworkConnectionAvailable(context)) {
                            total += bytesRead
                            if (fileLength > 0)
                                downloadListener.onProgress((total * 100 / fileLength).toInt())
                            output.write(data, 0, bytesRead)
                        } else {
                            input.closeQuietly()
                            output.closeQuietly()
                            connection?.disconnect()
                            return null
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                } finally {
                    input.closeQuietly()
                    output.closeQuietly()
                    connection?.disconnect()
                }

                ZipUtils.upZipFileCPS(file, directory)
                downloadListener.onComplete()
                return localFilePath

            } catch (e: Exception) {
                file.delete()

                if (count < 3) {
                    count++
                    e.printStackTrace()
                    return downloadSQLITECPS(url, directory, context, sqliteName, downloadListener)
                } else {
                    count = 0
                    downloadListener.onError()
                    return null
                }
            }
        }
    }
}