package cn.edu.buaa.sei;

import java.io.Serializable;

public class AppConfig implements Serializable {

    private static final long serialVersionUID = 1754904648061167616L;
    private String author;
    
    // 文件输入和输出的路径和文件名
    private String path_to_word_input;
    private String path_to_word_output;
    private String input_filename;
    private String output_filename;
    
    // log4j 配置文件的路径
    private String path_to_log4j_properties;
    
    // LTP - 服务器地址设置
    private String ltp_server_host;
    private String ltp_server_port;
    private String ltp_server_path;

    public AppConfig()
    {
        this.author="Jeanhwea";
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getPath_to_word_input()
    {
        return path_to_word_input;
    }

    public void setPath_to_word_input(String path_to_word_input)
    {
        this.path_to_word_input = path_to_word_input;
    }

    public String getPath_to_word_output()
    {
        return path_to_word_output;
    }

    public void setPath_to_word_output(String path_to_word_output)
    {
        this.path_to_word_output = path_to_word_output;
    }

    public String getInput_filename()
    {
        return input_filename;
    }

    public void setInput_filename(String input_filename)
    {
        this.input_filename = input_filename;
    }

    public String getOutput_filename()
    {
        return output_filename;
    }

    public void setOutput_filename(String output_filename)
    {
        this.output_filename = output_filename;
    }

    public String getPath_to_log4j_properties()
    {
        return path_to_log4j_properties;
    }

    public void setPath_to_log4j_properties(String path_to_log4j_properties)
    {
        this.path_to_log4j_properties = path_to_log4j_properties;
    }

    public String getLtp_server_host()
    {
        return ltp_server_host;
    }

    public void setLtp_server_host(String ltp_server_host)
    {
        this.ltp_server_host = ltp_server_host;
    }

    public String getLtp_server_port()
    {
        return ltp_server_port;
    }

    public void setLtp_server_port(String ltp_server_port)
    {
        this.ltp_server_port = ltp_server_port;
    }

    public String getLtp_server_path()
    {
        return ltp_server_path;
    }

    public void setLtp_server_path(String ltp_server_path)
    {
        this.ltp_server_path = ltp_server_path;
    }
}
