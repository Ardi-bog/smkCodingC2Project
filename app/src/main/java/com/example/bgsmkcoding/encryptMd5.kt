package com.example.bgsmkcoding

import  java.security.MessageDigest

object encryptMd5 {
    @Throws(Exception::class)
    fun encryptMd5(data: ByteArray?): ByteArray{
        val md5 = MessageDigest.getInstance("MD5")
        md5.update(data)
        return md5.digest()
    }
}