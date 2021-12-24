package wolfsurge.cadmusinstaller;

import java.net.*;
import java.io.*;

public class DownloadFiles
{
    public static String ClientDL;
    public static String JSONDL;
    public static String username;
    public static String path;
    
    public static void dl() {
        final File file = new File(DownloadFiles.path);
        try {
            file.mkdir();
        }
        catch (Exception ex) {}
        try (final BufferedInputStream in = new BufferedInputStream(new URL(DownloadFiles.ClientDL).openStream());
             final FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\" + DownloadFiles.username + "\\AppData\\Roaming\\.minecraft\\versions\\Cadmus\\Cadmus.jar")) {
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
        System.out.println("Attempting to download client json...");
        try (final BufferedInputStream in = new BufferedInputStream(new URL(DownloadFiles.JSONDL).openStream());
             final FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\" + DownloadFiles.username + "\\AppData\\Roaming\\.minecraft\\versions\\Cadmus\\Cadmus.json")) {
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
    
    static {
        DownloadFiles.ClientDL = "https://www.dropbox.com/s/l9pu3of7gfr3how/Cadmus.jar?dl=1";
        DownloadFiles.JSONDL = "https://www.dropbox.com/s/42qndgw8vwblfks/Cadmus.json?dl=1";
        DownloadFiles.username = System.getProperty("user.name");
        DownloadFiles.path = "C:\\Users\\" + DownloadFiles.username + "\\AppData\\Roaming\\.minecraft\\versions\\Cadmus";
    }
}
