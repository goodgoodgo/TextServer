package org.textin.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

/**
 * @program: Textin
 * @description:
 * @author: ma
 * @create: 2022-12-19 20:31
 */
public class TextHttpUtil {
    private static final String appId = "713b429d4b24b4b2657e594715706ade";
    private static final String secretCode = "c4b244a0370612fe0f5205a185e02932";

    public static String postUrl(String url,byte[] imgData){
        BufferedReader in = null;
        DataOutputStream out = null;
        String result = "";

        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("x-ti-app-id", appId);
            conn.setRequestProperty("x-ti-secret-code", secretCode);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // 设置请求方式
            out = new DataOutputStream(conn.getOutputStream());
            out.write(imgData);
            out.flush();
            out.close();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
