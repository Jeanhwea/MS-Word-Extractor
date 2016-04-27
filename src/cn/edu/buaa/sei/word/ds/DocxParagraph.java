package cn.edu.buaa.sei.word.ds;

public class DocxParagraph implements WordParagraph {
    
    private String style;
    private String text;

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
