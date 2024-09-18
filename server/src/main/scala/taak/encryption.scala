package manik1.manik1.encryption

import java.io.{ BufferedOutputStream, File, FileInputStream, FileOutputStream}
import java.nio.file.{Files, Paths}

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException

class Encryption {

  val nameFile = "/tmp/codecx.bin"
  val cipher: Cipher = Cipher.getInstance ( "AES" )
  val key: String = "Bar12345Bar12345" // 128 bit key

  // Create key and cipher
  val aesKey: Key = new SecretKeySpec(key.getBytes(), "AES")

  def encrypt(text: String) = {
    //try  {
      cipher.init(Cipher.ENCRYPT_MODE, aesKey)  // encrypt the text
      val encrypted: Array[Byte] = cipher.doFinal(text.getBytes()) //byte[]
      val file: File = new File( nameFile  )
      val fos: FileOutputStream = new FileOutputStream(file)
      fos.write(encrypted)
      encrypted
    //}
  }

  def decrypt(bytes: Array[Byte]) = {
    //try  {
      /*val file_is: File = new File ( nameFile  )
      val fis = new FileInputStream(file_is)
      val bytes = new Array[Byte](file_is.length().toInt)
      fis.read(bytes)
      fis.close()*/
      cipher.init(Cipher.DECRYPT_MODE, aesKey)  // decrypt the text
      new String(cipher.doFinal(bytes))
    //}

  }

}
