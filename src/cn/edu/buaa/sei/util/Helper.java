package cn.edu.buaa.sei.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Helper {

    public Helper()
    {
    }

    /**
     * 删除一些无用的字符
     * 
     * @param original 原始字符串
     * @return 删除后的字符串
     */
    private static String escapeBlank(String original)
    {
        return original.replaceAll("[\r|\n|\t|\b|\f]", "")
                       .replaceAll("\\uf06c", "")
                       .replaceAll("\\u0001", "")
                       .replaceAll("\\u0002", "")
                       .replaceAll("\\u0007", "")
                       .replaceAll("\\u0013", "")
                       .replaceAll("\\u0015", "")
                       .replaceAll("\\u0014", "");
    }

    public static String rmNonUTF8(String original)
    {
        String ret = null;
        byte[] utf8Bytes;
        try {
            utf8Bytes = original.getBytes("UTF-8");
            ret = new String(utf8Bytes, "UTF-8");
            return escapeBlank(ret);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    static public String filterOffUtf8Mb4(String text) {
        byte[] bytes;
        try {
            bytes = text.getBytes("UTF-8");
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            int i = 0;
            while (i < bytes.length) {
                short b = bytes[i];
                if (b > 0) {
                    buffer.put(bytes[i++]);
                    continue;
                }
                b += 256;
                if ((b ^ 0xC0) >> 4 == 0) {
                    buffer.put(bytes, i, 2);
                    i += 2;
                }
                else if ((b ^ 0xE0) >> 4 == 0) {
                    buffer.put(bytes, i, 3);
                    i += 3;
                }
                else if ((b ^ 0xF0) >> 4 == 0) {
                    i += 4;
                }
            }
            buffer.flip();
            return new String(buffer.array(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
