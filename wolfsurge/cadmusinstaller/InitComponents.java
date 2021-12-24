package wolfsurge.cadmusinstaller;

import java.awt.*;
import javax.swing.*;

class InitComponents
{
    public static JFrame setupJFrameAndGet(final String title, final int width, final int height) {
        final JFrame tmpJF = new JFrame(title);
        tmpJF.setSize(width, height);
        tmpJF.setLocationRelativeTo(null);
        tmpJF.setLayout(null);
        tmpJF.setDefaultCloseOperation(3);
        return tmpJF;
    }
    
    public static JTextArea setupJTextAreaAndGet(final String text, final int rows, final int columns, final boolean setEditableFlag, final boolean setLineWrapFlag, final boolean setWrapStyleWordFlag, final boolean setBoundsFlag, final int xpos, final int ypos, final int width, final int height) {
        final JTextArea tmpJTA = new JTextArea(text, rows, columns);
        tmpJTA.setEditable(setEditableFlag);
        tmpJTA.setLineWrap(setLineWrapFlag);
        tmpJTA.setWrapStyleWord(setWrapStyleWordFlag);
        if (setBoundsFlag) {
            tmpJTA.setBounds(xpos, ypos, width, height);
        }
        return tmpJTA;
    }
    
    public static JScrollPane setupScrollableJTextAreaAndGet(final JTextArea jta, final int xpos, final int ypos, final int width, final int height) {
        final JScrollPane tmpJSP = new JScrollPane(jta);
        tmpJSP.setBounds(xpos, ypos, width, height);
        return tmpJSP;
    }
    
    public static JMenuBar setupJMenuBarAndGet() {
        final JMenuBar tmpJMB = new JMenuBar();
        return tmpJMB;
    }
    
    public static JMenu setupJMenuAndGet(final String text) {
        final JMenu tmpJM = new JMenu(text);
        return tmpJM;
    }
    
    public static JMenuItem setupJMenuItemAndGet(final String text) {
        final JMenuItem tmpJMI = new JMenuItem(text);
        return tmpJMI;
    }
}
