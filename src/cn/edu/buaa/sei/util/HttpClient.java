package cn.edu.buaa.sei.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

public class HttpClient {

    private URLConnection conn;

    public HttpClient()
    {
        conn = null;
    }

    public String buildParams(HashMap<String, String> params)
    {
        String str = null;
        StringBuilder sb = new StringBuilder();

        for (Entry<String, String> entry : params.entrySet()) {
            try {
                str = String.format(null == str ? "%s=%s" : "&%s=%s", URLEncoder.encode(entry.getKey(), "UTF-8"),
                        URLEncoder.encode(entry.getValue(), "UTF-8"));
                sb.append(str);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private String buildURL(String host, String port, String path)
    {
        if (null == host) {
            host = "127.0.0.1";
        }
        if (null == port) {
            port = "80";
        }
        if (null == path) {
            path = "/";
        }

        return String.format("http://%s:%s%s", host, port, path);
    }

    /**
     * 打开连接URL
     * 
     * @param str_url 要打开的url链接
     */
    public boolean urlOpen(String str_url)
    {
        try {
            URL url = new URL(str_url);
            conn = url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 打开连接URL
     * 
     * @param host 主机
     * @param port 端口号
     * @param path 路径
     */
    public boolean urlOpen(String host, String port, String path)
    {
        return urlOpen(buildURL(host, port, path));
    }

    /**
     * 读取相应链接里面的数据，使用前必须先调用urlOpen(String str_url)打开链接
     * 
     * @return 读入的字符串
     */
    public String urlReadData()
    {
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 写入相应链接里面的数据，使用前必须先调用urlOpen(String str_url)打开链接
     * 
     * @param data 要写入的字符串
     */
    public void urlWriteData(String data)
    {
        try {
            OutputStream os = conn.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(os);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
