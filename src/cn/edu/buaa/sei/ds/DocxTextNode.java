package cn.edu.buaa.sei.ds;

import java.io.Serializable;

public class DocxTextNode implements Serializable {

    private static final long serialVersionUID = 6226935724305291071L;
    String text;

    public DocxTextNode()
    {
    }

    public DocxTextNode(String text)
    {
        this.text = text;
    }

    /**
     * @return the text
     */
    public String getText()
    {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text)
    {
        this.text = text;
    }

}
