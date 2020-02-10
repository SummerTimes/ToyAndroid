/**
 * @Probject Name: phone_service
 * @Path: com.bailian.middleware.utilBase64.java
 * @Create By songwei
 * @Create In 2015年1月5日 下午4:26:59
 * TODO
 */
package com.kk.app.lib.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Class Name Base64
 * @Author songwei
 * @Create In 2015年1月5日
 */
public class Base64 {

	private static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    /**
     * data[]进行编码
     * 
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        int start = 0;
        int len = data.length;
        StringBuffer buf = new StringBuffer(data.length * 3 / 2);

        int end = len - 3;
        int i = start;
        int n = 0;

        while (i <= end) {
            int d = ((((int) data[i]) & 0x0ff) << 16)
                    | ((((int) data[i + 1]) & 0x0ff) << 8)
                    | (((int) data[i + 2]) & 0x0ff);

            buf.append(CHARS[(d >> 18) & 63]);
            buf.append(CHARS[(d >> 12) & 63]);
            buf.append(CHARS[(d >> 6) & 63]);
            buf.append(CHARS[d & 63]);

            i += 3;

            if (n++ >= 14) {
                n = 0;
//                buf.append(" "); //why why why
            }
        }

        if (i == start + len - 2) {
            int d = ((((int) data[i]) & 0x0ff) << 16)
                    | ((((int) data[i + 1]) & 255) << 8);

            buf.append(CHARS[(d >> 18) & 63]);
            buf.append(CHARS[(d >> 12) & 63]);
            buf.append(CHARS[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = (((int) data[i]) & 0x0ff) << 16;

            buf.append(CHARS[(d >> 18) & 63]);
            buf.append(CHARS[(d >> 12) & 63]);
            buf.append("==");
        }

        return buf.toString();
    }
    
    
  public static byte[] decode(String s) {

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      try {
          decode(s, bos);
      } catch (IOException e) {
          throw new RuntimeException();
      }
      byte[] decodedBytes = bos.toByteArray();
      try {
          bos.close();
          bos = null;
      } catch (IOException ex) {
          System.err.println("Error while decoding BASE64: " + ex.toString());
      }
      return decodedBytes;
  }
  private static void decode(String s, OutputStream os) throws IOException {
      int i = 0;

      int len = s.length();

      while (true) {
          while (i < len && s.charAt(i) <= ' ') {
              i++;
          }

          if (i == len) {
              break;
          }

          int tri = (decode(s.charAt(i)) << 18)
                  + (decode(s.charAt(i + 1)) << 12)
                  + (decode(s.charAt(i + 2)) << 6)
                  + (decode(s.charAt(i + 3)));

          os.write((tri >> 16) & 255);
          if (s.charAt(i + 2) == '=') {
              break;
          }
          os.write((tri >> 8) & 255);
          if (s.charAt(i + 3) == '=') {
              break;
          }
          os.write(tri & 255);

          i += 4;
      }
  }
  private static int decode(char c) {
      if (c >= 'A' && c <= 'Z') {
          return ((int) c) - 65;
      } else if (c >= 'a' && c <= 'z') {
          return ((int) c) - 97 + 26;
      } else if (c >= '0' && c <= '9') {
          return ((int) c) - 48 + 26 + 26;
      } else {
          switch (c) {
              case '+':
                  return 62;
              case '/':
                  return 63;
              case '=':
                  return 0;
              default:
                  throw new RuntimeException("unexpected code: " + c);
          }
      }
  }

}
