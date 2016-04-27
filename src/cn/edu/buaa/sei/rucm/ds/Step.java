package cn.edu.buaa.sei.rucm.ds;

import java.io.Serializable;

public class Step implements Serializable {

    private static final long serialVersionUID = 8488739868018093304L;

    private String text;

    public Step()
    {
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

}
