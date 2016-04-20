package cn.edu.buaa.sei.ds;

public class DocParagraph implements WordParagraph {
    
    private String style;
    private String text;

    public DocParagraph(String style, String text)
    {
        this.style = style;
        this.text = text;
    }

    public String getText()
    {
        return this.text;
    }

    public String getStyle()
    {
        return this.style;
    }

}
