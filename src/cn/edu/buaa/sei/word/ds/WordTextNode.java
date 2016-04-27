package cn.edu.buaa.sei.word.ds;

import java.io.Serializable;

public class WordTextNode implements Serializable {

    private static final long serialVersionUID = 6226935724305291071L;
    String text;

    public WordTextNode()
    {
    }

    public WordTextNode(String text)
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
