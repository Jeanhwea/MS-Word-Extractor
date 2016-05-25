package cn.edu.buaa.sei;

import java.io.Serializable;

public class AppConfig implements Serializable {

    private static final long serialVersionUID = 1754904648061167616L;
    private String author;

    // 文件输入和输出的路径和文件名
    private String path2WordInput;
    private String path2WordOutput;
    private String inputFileName;
    private String outputFileName;

    // log4j 配置文件的路径
    private String path2Log4jProperties;

    // LTP - 服务器地址设置
    private String ltpServerHost;
    private String ltpServerPort;
    private String ltpServerPath;

    public AppConfig() {
        this.author = "Jeanhwea";
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPath2WordInput() {
        return path2WordInput;
    }

    public void setPath2WordInput(String path2WordInput) {
        this.path2WordInput = path2WordInput;
    }

    public String getPath2WordOutput() {
        return path2WordOutput;
    }

    public void setPath2WordOutput(String path2WordOutput) {
        this.path2WordOutput = path2WordOutput;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getPath2Log4jProperties() {
        return path2Log4jProperties;
    }

    public void setPath2Log4jProperties(String path2Log4jProperties) {
        this.path2Log4jProperties = path2Log4jProperties;
    }

    public String getLtpServerHost() {
        return ltpServerHost;
    }

    public void setLtpServerHost(String ltpServerHost) {
        this.ltpServerHost = ltpServerHost;
    }

    public String getLtpServerPort() {
        return ltpServerPort;
    }

    public void setLtpServerPort(String ltpServerPort) {
        this.ltpServerPort = ltpServerPort;
    }

    public String getLtpServerPath() {
        return ltpServerPath;
    }

    public void setLtpServerPath(String ltpServerPath) {
        this.ltpServerPath = ltpServerPath;
    }
}
