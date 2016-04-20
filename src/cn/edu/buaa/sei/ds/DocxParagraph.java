package cn.edu.buaa.sei.ds;

public class DocxParagraph implements WordParagraph {
    
    private String text;
    private String style;

    public DocxParagraph(String style, String text)
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
