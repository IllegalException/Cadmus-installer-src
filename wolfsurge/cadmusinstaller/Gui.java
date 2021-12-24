package wolfsurge.cadmusinstaller;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;

public class Gui implements KeyListener, ActionListener
{
    public static String os;
    public static String jreversion;
    public static String ClientDL;
    public static String JSONDL;
    public static String username;
    public static String path;
    Dimension screenSize;
    int screenWidth;
    int screenHeight;
    String title;
    String text;
    JFrame jf;
    JTextArea jta;
    JScrollPane jsp;
    JMenuBar jmb;
    JMenu jm;
    JMenuItem jmi;
    int initialCaretPosition;
    int currentCaretPosition;
    boolean inputAvailable;
    int BACKSPACE;
    int ENTER;
    int PG_UP;
    int PG_DN;
    int END;
    int HOME;
    int LEFT_ARROW;
    int UP_ARROW;
    int DOWN_ARROW;
    int CTRL;
    int A;
    int H;
    
    public Gui() {
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = 800;
        this.screenHeight = 600;
        this.title = null;
        this.text = null;
        this.jf = null;
        this.jta = null;
        this.jsp = null;
        this.jmb = null;
        this.jm = null;
        this.jmi = null;
        this.initialCaretPosition = 0;
        this.currentCaretPosition = 0;
        this.inputAvailable = false;
        this.BACKSPACE = 8;
        this.ENTER = 10;
        this.PG_UP = 33;
        this.PG_DN = 34;
        this.END = 35;
        this.HOME = 36;
        this.LEFT_ARROW = 37;
        this.UP_ARROW = 38;
        this.DOWN_ARROW = 40;
        this.CTRL = 128;
        this.A = 65;
        this.H = 72;
    }
    
    public static void dlClient() {
        final File file = new File(Gui.path);
        try {
            file.mkdir();
        }
        catch (Exception ex) {}
        try (final BufferedInputStream in = new BufferedInputStream(new URL(Gui.ClientDL).openStream());
             final FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\" + Gui.username + "\\AppData\\Roaming\\.minecraft\\versions\\Cadmus\\Cadmus.jar")) {
            final byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
        catch (IOException e) {
            System.out.println("FATAL ERROR");
            e.printStackTrace();
        }
    }
    
    public static void dlJson() {
        try (final BufferedInputStream in = new BufferedInputStream(new URL(Gui.JSONDL).openStream());
             final FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\" + Gui.username + "\\AppData\\Roaming\\.minecraft\\versions\\Cadmus\\Cadmus.json")) {
            final byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
        catch (IOException e) {
            System.out.println("FATAL ERROR");
            e.printStackTrace();
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent ae) {
        final int cCurrPos = this.jta.getCaretPosition();
        this.jta.selectAll();
        this.jta.copy();
        this.jta.select(cCurrPos, cCurrPos);
    }
    
    @Override
    public void keyTyped(final KeyEvent ke) {
    }
    
    @Override
    public void keyReleased(final KeyEvent ke) {
    }
    
    @Override
    public void keyPressed(final KeyEvent ke) {
        final int keyCode = ke.getKeyCode();
        if (keyCode == this.PG_UP || keyCode == this.PG_DN || keyCode == this.UP_ARROW || keyCode == this.DOWN_ARROW || (keyCode == this.A && ke.getModifiersEx() == this.CTRL)) {
            ke.consume();
        }
        else if (keyCode == this.LEFT_ARROW || keyCode == this.BACKSPACE || (keyCode == this.H && ke.getModifiersEx() == this.CTRL)) {
            synchronized (this) {
                if (this.jta.getCaretPosition() <= this.initialCaretPosition) {
                    ke.consume();
                }
            }
        }
        else if (keyCode == this.HOME) {
            synchronized (this) {
                this.jta.setCaretPosition(this.initialCaretPosition);
                ke.consume();
            }
        }
        else if (keyCode == this.END) {
            synchronized (this) {
                this.jta.setCaretPosition(this.jta.getDocument().getLength());
                ke.consume();
            }
        }
        else if (keyCode == this.ENTER) {
            this.jta.setCaretPosition(this.jta.getDocument().getLength());
            synchronized (this) {
                this.currentCaretPosition = this.jta.getCaretPosition();
                try {
                    final String charAtInitialCaretPosition = this.jta.getText(this.initialCaretPosition, 1);
                    if (charAtInitialCaretPosition.equals("\n")) {
                        ++this.initialCaretPosition;
                    }
                }
                catch (Exception ex) {}
            }
        }
    }
    
    String getInputFromJTextArea(final JTextArea jta) {
        int len = 0;
        String inputFromUser = "";
        while (true) {
            synchronized (this) {
                if (this.inputAvailable) {
                    len = this.currentCaretPosition - this.initialCaretPosition;
                    try {
                        inputFromUser = jta.getText(this.initialCaretPosition, len);
                        this.initialCaretPosition = this.currentCaretPosition;
                    }
                    catch (Exception e) {
                        inputFromUser = "";
                        return inputFromUser;
                    }
                    this.inputAvailable = false;
                    return inputFromUser;
                }
                try {
                    this.wait();
                }
                catch (Exception ex) {}
            }
        }
    }
    
    void outputToJTextArea(final JTextArea jta, final String text) {
        jta.append(text);
        jta.setCaretPosition(jta.getDocument().getLength());
        synchronized (this) {
            this.initialCaretPosition = jta.getCaretPosition();
        }
    }
    
    void begin() {
        this.outputToJTextArea(this.jta, "Using OS " + Gui.os + " and Java version " + Gui.jreversion + "\n\n");
        this.outputToJTextArea(this.jta, "Cadmus Client Installer\n");
        this.outputToJTextArea(this.jta, "Installing CADMUS in default Minecraft directory.\n\n");
        this.outputToJTextArea(this.jta, "Downloading Client Jar\n\n");
        dlClient();
        this.outputToJTextArea(this.jta, "Downloaded Client Jar\n\n");
        this.outputToJTextArea(this.jta, "Downloading Client Json\n\n");
        dlJson();
        this.outputToJTextArea(this.jta, "Downloaded Client Json\n");
        this.outputToJTextArea(this.jta, "\nYou can now select CADMUS as an installation in the Minecraft Launcher. You can close this program.\n\n\n");
    }
    
    void configureJTextAreaForInputOutput(final JTextArea jta) {
        jta.addKeyListener(this);
        for (final MouseListener listener : jta.getMouseListeners()) {
            jta.removeMouseListener(listener);
        }
        for (final MouseMotionListener listener2 : jta.getMouseMotionListeners()) {
            jta.removeMouseMotionListener(listener2);
        }
        for (final MouseWheelListener listener3 : jta.getMouseWheelListeners()) {
            jta.removeMouseWheelListener(listener3);
        }
    }
    
    void createAndShowGUI() {
        this.title = "Cadmus Client Installer";
        this.jf = InitComponents.setupJFrameAndGet(this.title, this.screenWidth - 150, this.screenHeight - 100);
        this.configureJTextAreaForInputOutput(this.jta = InitComponents.setupJTextAreaAndGet("", 1000, 100, true, true, true, false, 0, 0, 0, 0));
        (this.jsp = InitComponents.setupScrollableJTextAreaAndGet(this.jta, 10, 10, this.screenWidth - 180, this.screenHeight - 180)).setHorizontalScrollBarPolicy(30);
        this.jsp.setVerticalScrollBarPolicy(22);
        this.jf.add(this.jsp);
        this.jmb = InitComponents.setupJMenuBarAndGet();
        this.jf.setVisible(true);
    }
    
    public static void main(final String[] args) {
        if (!Gui.os.startsWith("Windows")) {
            if (Gui.os.startsWith("Mac")) {
                Gui.path = "/Users/" + Gui.username + "/Library/Application Support/minecraft";
            }
            else if (Gui.os.startsWith("Linux")) {
                Gui.path = "/home/" + Gui.username + "/.minecraft";
            }
        }
        final Gui cfjp = new Gui();
        cfjp.createAndShowGUI();
        cfjp.begin();
    }
    
    static {
        Gui.os = System.getProperty("os.name");
        Gui.jreversion = System.getProperty("java.version");
        Gui.ClientDL = "https://www.dropbox.com/s/l9pu3of7gfr3how/Cadmus.jar?dl=1";
        Gui.JSONDL = "https://www.dropbox.com/s/42qndgw8vwblfks/Cadmus.json?dl=1";
        Gui.username = System.getProperty("user.name");
        Gui.path = "C:\\Users\\" + Gui.username + "\\AppData\\Roaming\\.minecraft\\versions\\Cadmus";
    }
}
