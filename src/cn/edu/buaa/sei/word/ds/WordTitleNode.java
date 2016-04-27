package cn.edu.buaa.sei.word.ds;

import java.io.Serializable;
import java.util.List;

public class WordTitleNode implements Serializable {

    private static final long serialVersionUID = -8984758739072932015L;
    int level;
    String title;
    List<WordTextNode> content;
    List<WordTitleNode> child;

    public WordTitleNode()
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
    public List<WordTextNode> getContent()
    {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(List<WordTextNode> content)
    {
        this.content = content;
    }

    /**
     * @return the child
     */
    public List<WordTitleNode> getChild()
    {
        return child;
    }

    /**
     * @param child the child to set
     */
    public void setChild(List<WordTitleNode> child)
    {
        this.child = child;
    }

}
