package com.example.library.utils

/**
 * Created by Horrarndoo on 2017/10/11.
 *
 *
 */
object UnicodeUtils {
    /**
     * utf-8 转换成 unicode
     *
     * @param inStr
     * @return
     */
    fun utf8ToUnicode(inStr: String): String {
        val myBuffer = inStr.toCharArray()

        val sb = StringBuffer()
        for (i in 0 until inStr.length) {
            val ub = Character.UnicodeBlock.of(myBuffer[i])
            if (ub === Character.UnicodeBlock.BASIC_LATIN) {
                //英文及数字等
                sb.append(myBuffer[i])
            } else if (ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                //全角半角字符
                val j = myBuffer[i].toInt() - 65248
                sb.append(j.toChar())
            } else {
                //汉字
                val s = myBuffer[i].toShort()
                val hexS = Integer.toHexString(s.toInt())
                val unicode = "\\u$hexS"
                sb.append(unicode.toLowerCase())
            }
        }
        return sb.toString()
    }

    /**
     * unicode 转换成 utf-8
     *
     * @param theString
     * @return
     */
    fun unicodeToUtf8(theString: String): String {
        var aChar: Char
        val len = theString.length
        val outBuffer = StringBuffer(len)
        var x = 0
        while (x < len) {
            aChar = theString[x++]
            if (aChar == '\\') {
                aChar = theString[x++]
                if (aChar == 'u') {
                    var value = 0
                    for (i in 0..3) {
                        aChar = theString[x++]
                        value = when (aChar) {
                            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> (value shl 4) + aChar.toInt() - '0'.toInt()
                            'a', 'b', 'c', 'd', 'e', 'f' -> (value shl 4) + 10 + aChar.toInt() - 'a'.toInt()
                            'A', 'B', 'C', 'D', 'E', 'F' -> (value shl 4) + 10 + aChar.toInt() - 'A'.toInt()
                            else -> throw IllegalArgumentException(
                                    "Malformed   \\uxxxx   encoding.")
                        }
                    }
                    outBuffer.append(value.toChar())
                } else {
                    if (aChar == 't') {
                        aChar = '\t'
                    } else if (aChar == 'r')
                        aChar = '\r'
                    else if (aChar == 'n')
                        aChar = '\n'
                    else if (aChar == 'f')
                        aChar = 'f'
                    outBuffer.append(aChar)
                }
            } else
                outBuffer.append(aChar)
        }
        return outBuffer.toString()
    }
}