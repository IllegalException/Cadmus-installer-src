package wolfsurge.cadmusinstaller;

public class Main
{
    public static void main(final String[] args) {
        System.out.println("\nCadmus Client Installer\n");
        DownloadFiles.dl();
        System.out.println("Finished downloading files. You can now select the client as an installation.");
    }
}
