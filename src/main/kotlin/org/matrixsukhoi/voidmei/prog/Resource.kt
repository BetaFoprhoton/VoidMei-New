package org.matrixsukhoi.voidmei.prog

import org.matrixsukhoi.voidmei.VoidMeiMain
import java.io.File
import java.io.InputStream
import java.nio.file.Path

/**
 * 获取resources文件夹下的文件，不存在返回null
 * @param path resources文件夹到目标文件的路径，前面必须有个"/"
 */
internal fun getResource(path: String): File? {
    return VoidMeiMain::class.java.getResource(if (path[0] != '/') "/$path" else path)?.path?.let {
        File(it)
    }
}

/**
 * 获取resources文件夹下的文件的绝对路径
 * @param path resources文件夹到目标文件的路径，前面必须有个"/"
 */
internal fun getResourcePath(path: String): String {
    return VoidMeiMain::class.java.getResource(if (path[0] != '/') "/$path" else path)?.path ?: ""
}

/**
 * 获取resources文件夹下的文件的InputStream
 * @param path resources文件夹到目标文件的路径，前面必须有个"/"
 */
internal fun getResourceStream(path: String): InputStream? {
        return VoidMeiMain::class.java.getResourceAsStream(if (path[0] != '/') "/$path" else path)

}

/**
 * 获取已打包的jar文件同目录下的文件，不存在就创建一个
 * @param name jar包同目录到目标文件的路径，前面必须有个"/"
 */
internal fun getOrCreateFolderResource(name: String): File {
    var path = VoidMeiMain::class.java.getProtectionDomain().codeSource.location.path
    val index = path.lastIndexOf("/")
    path = path.substring(1, index)

    return File(path + name).let {
        if (!it.exists())
            it.createNewFile()
        it
    }
}

/**
 * 获取已打包的jar文件同目录下的文件的绝对路径
 * @param name jar包同目录到目标文件的路径，前面必须有个"/"
 */
internal fun getFolderResourcePath(name: String): String {
    val path = VoidMeiMain::class.java.getProtectionDomain().codeSource.location.path
    val index = path.lastIndexOf("/")
    return path.substring(1, index) + name
}

internal fun String.toPath(): Path {
    return Path.of(this)
}