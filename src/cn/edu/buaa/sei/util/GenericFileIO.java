package cn.edu.buaa.sei.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 通用的文件读取和写入类
 * @author hujh
 *
 */
public class GenericFileIO {

    public GenericFileIO()
    {
    }
    
    /**
     * 读取文件file的内容
     * @param file 要读的文件
     * @return 读取的文件的内容
     */
    public String read(File file)
    {
        if (!file.exists()) return null;

        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader breader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = breader.readLine())!=null) {
                sb.append(line);
            }
            breader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return sb.toString();
    }
    
    /**
     * 读取文件file的内容
     * @param filename 要读的文件名
     * @return 读取的文件的内容
     */
    public String read(String filename)
    {
        return this.read(new File(filename));
    }
    
    /**
     * 将text覆盖地方式写入文件file里面
     * @param text 要写入的字符串
     * @param file 文件句柄
     * @return 是否成功
     */
    public boolean write(String text, File file)
    {
        try {
            FileWriter writer = new FileWriter(file, false);
            PrintWriter out = new PrintWriter(writer);
            out.print(text);
            out.flush();
            out.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    /**
     * 将text覆盖地方式写入文件file里面
     * @param text 要写入的字符串
     * @param filename 文件名
     * @return 是否成功
     */
    public boolean write(String text, String filename)
    {
        return this.write(text, new File(filename));
    }

}
