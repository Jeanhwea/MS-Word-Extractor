package cn.edu.buaa.sei.rucm.ds;

import java.io.Serializable;

public class Condition implements Serializable {

    private static final long serialVersionUID = 5634388218121699800L;
    
    private String text;

    public Condition()
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
