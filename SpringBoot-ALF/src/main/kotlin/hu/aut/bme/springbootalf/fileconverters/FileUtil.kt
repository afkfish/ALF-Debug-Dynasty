package hu.aut.bme.springbootalf.fileconverters

import java.io.*

/**
 * Utility object for file-related operations.
 * This object provides methods for converting files to byte arrays and vice versa.
 */
object FileUtil {

    /**
     * Reads data from a file and returns it as a byte array.
     *
     * @param file The file to read data from.
     * @return The byte array containing data read from the file.
     */
    fun getByteArrayFromFile(file: File): ByteArray {
        val baos = ByteArrayOutputStream()
        var fis: FileInputStream? = null

        return try {
            fis = FileInputStream(file)
            val buffer = ByteArray(500)

            var read: Int
            while (fis.read(buffer).also { read = it } > 0) {
                baos.write(buffer, 0, read)
            }

            baos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            ByteArray(0)
        } finally {
            try {
                fis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Writes a byte array to a file.
     *
     * @param byteArray The byte array to write to the file.
     * @param filePath The path of the file to write to.
     * @return The File object representing the written file, or null if an error occurred.
     */
    fun byteArrayToFile(byteArray: ByteArray, filePath: String): File? {
        var file: File? = null
        var fos: FileOutputStream? = null

        try {
            file = File(filePath)
            fos = FileOutputStream(file)

            fos.write(byteArray)
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (ioe: IOException) {
                    ioe.printStackTrace()
                }
            }
        }

        return file
    }
}