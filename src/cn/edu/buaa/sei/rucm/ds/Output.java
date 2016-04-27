package cn.edu.buaa.sei.rucm.ds;

import java.io.Serializable;

public class Output implements Serializable {

    private static final long serialVersionUID = -7837544457138482104L;
    
    private String text;

    public Output()
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
