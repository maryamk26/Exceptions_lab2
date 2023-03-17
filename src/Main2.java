import java.io.File;
import java.io.IOException;

public class Main2 {
    public static void main(String[] args) throws IOException{
        File BASEDir = new File("BASEDIR");
        File PICDir = new File(BASEDir, "PIC");
        File TXTDir = new File(BASEDir, "TXT");
        File HISDir = new File(TXTDir, "HIS");
        File HORRORDir= new File(TXTDir, "HORROR");
        File FSDir = new File(HORRORDir, "FS");
        String[] PICFiles = {"1.txt", "2.txt", "3.txt", "4.txt", "5.txt", "6.txt"};

        crDIRS(PICDir);
        crDIRS(HISDir);
        crDIRS(HORRORDir);
        crDIRS(FSDir);
        crFiles(PICDir, PICFiles);
    }

    private static void crDIRS(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println(directory.exists());
        }

    }

    private static void crFiles(File DIRS, String[] FILESNAME) throws IOException {
        for (String fileName : FILESNAME) {
            File file = new File(DIRS, fileName);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println(DIRS.exists());
            }
        }
    }
}
