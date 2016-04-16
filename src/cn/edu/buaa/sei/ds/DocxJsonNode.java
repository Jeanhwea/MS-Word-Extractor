package cn.edu.buaa.sei.ds;

import java.io.Serializable;
import java.util.List;

public class DocxJsonNode implements Serializable {

    private static final long serialVersionUID = -8984758739072932015L;
    int level;
    String title;
    List<DocxTextNode> content;
    List<DocxJsonNode> child;

    public DocxJsonNode()
    {
    }

    /**
     * @return the level
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level)
    {
        this.level = level;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the content
     */
    public List<DocxTextNode> getContent()
    {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(List<DocxTextNode> content)
    {
        this.content = content;
    }

    /**
     * @return the child
     */
    public List<DocxJsonNode> getChild()
    {
        return child;
    }

    /**
     * @param child the child to set
     */
    public void setChild(List<DocxJsonNode> child)
    {
        this.child = child;
    }

}
