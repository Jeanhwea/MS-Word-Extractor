package cn.edu.buaa.sei.util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

/**
 * Created by hujh on 5/3/16.
 */
public class SimpleUI {

    public String chooseAFile(String startPath) {
        if (null == startPath) return null;
        JFileChooser chooser = new JFileChooser(startPath);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("RUCM File (*.json)", "json");
        chooser.setFileFilter(filter);
        //
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

    public void showText(String text, String windowTitle) {
        JFrame jFrame = new JFrame(windowTitle);
        JTextArea jTextArea = new JTextArea();
        Font font = new Font("Dialog", Font.PLAIN, 16);
        //
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jTextArea.append(text);
        jTextArea.setFont(font);
        jTextArea.setEditable(false);
        jFrame.add(jTextArea);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public SimpleUI() {}
}
