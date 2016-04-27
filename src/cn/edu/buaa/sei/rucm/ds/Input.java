package cn.edu.buaa.sei.rucm.ds;

import java.io.Serializable;

public class Input implements Serializable {

    private static final long serialVersionUID = 8658801445621703256L;
    
    private String text;

    public Input()
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
