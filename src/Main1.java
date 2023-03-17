import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;

public class Main1 {
    public static void main(String[] args) {

        File dir = new File("BASEDIR\\DIR0");
        int COUNT = 0, DCOUNT = 0;
        File[] files = dir.listFiles();
        for(File f : files){
            if (f.isFile()) COUNT++;
            if(f.isDirectory()) DCOUNT++;
        }
        System.out.println("ALL FILES: " + COUNT);
        System.out.println("ALL DIRS: " + DCOUNT);

        System.out.println("DIRS OF BASEDIR/DIR0/:");
        for(File f : files){
            if(f.isDirectory()){
                System.out.println(f.getName());
            }
        }

        int TCOUNT = 0;
        for(File f : files){
            if(f.isFile()){
                COUNT++;
                if(f.getName().endsWith(".txt")){
                    TCOUNT++;
                }
            }
        }
        System.out.println("ALL FILES : " + COUNT);
        System.out.println("TXT FILES (*.txt): " + TCOUNT);

        for(File f : files){
            if(f.isFile() && f.getName().equals("TREE.txt")){
                System.out.println("ALL TREES.txt: " + f.getAbsolutePath());
            }
        }
        listNestedItems(dir);
        printEmptyDirectories(dir);
        System.out.println("DIRS OF MAX NO OF FILES : " + getMaxFilesDirectory(dir));
        System.out.println("ITEMS : " + getDeepestNestedItem(dir));
        System.out.println("FREE DS : " + getFreeDiskSpace(dir));
        getStorageDevices();

    }
    public static void printEmptyDirectories(File RT) {
        if (RT == null || !RT.exists()) return;


        if (RT.isDirectory()) {
            File[] files = RT.listFiles();
            if (files == null) return;

            if (files.length == 0) {
                System.out.println(RT.getAbsolutePath());
            } else {
                for (File file : files) {
                    printEmptyDirectories(file);
                }
            }
        }
    }
    public static void listNestedItems(File RT) {
        if (RT == null || !RT.exists()) return;


        System.out.println(RT.getAbsolutePath());


        if (RT.isDirectory()) {
            File[] files = RT.listFiles();
            if (files == null) return;

            for (File file : files) {
                listNestedItems(file);
            }
        }
    }
    public static int countFiles(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) return 0;

        int CT = 0;

        File[] files = dir.listFiles();
        if (files == null) return 0;

        for (File file : files) {
            if (file.isFile()) {
                CT++;
            } else if (file.isDirectory()) {
                CT += countFiles(file);
            }
        }

        return CT;
    }
    public static String getMaxFilesDirectory(File RT) {
        if (RT == null || !RT.exists()) return null;


        if (RT.isDirectory()) {
            File[] files = RT.listFiles();
            if (files == null) return null;

            int maxFiles = -1;
            String maxFilesDirName = null;

            for (File file : files) {
                if (file.isDirectory()) {
                    int numFiles = countFiles(file);
                    if (numFiles > maxFiles) {
                        maxFiles = numFiles;
                        maxFilesDirName = file.getName();
                    }
                }
            }


            for (File file : files) {
                if (file.isDirectory()) {
                    String subDirMaxFilesName = getMaxFilesDirectory(file);
                    if (subDirMaxFilesName != null) {
                        if (maxFilesDirName == null || countFiles(new File(RT, subDirMaxFilesName)) > maxFiles) {
                            maxFilesDirName = subDirMaxFilesName;
                        }
                    }
                }
            }

            return maxFilesDirName;
        } else {
            return null;
        }
    }
    public static long getFreeDiskSpace(File root) {
        if (root == null || !root.exists()) return 0;

        try {
            FileStore store = Files.getFileStore(root.toPath());
            return store.getUsableSpace();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static String getDeepestNestedItem(File root) {
        if (root == null || !root.exists()) return "";

        if (root.isFile()) {
            return root.getAbsolutePath();
        }


        if (root.isDirectory()) {
            File[] files = root.listFiles();
            if (files == null || files.length == 0) {

                return root.getAbsolutePath();
            }

            String deepestPath = "";
            for (File file : files) {
                String path = getDeepestNestedItem(file);
                if (path.length() > deepestPath.length()) {
                    deepestPath = path;
                }
            }
            return deepestPath;
        }

        return "";
    }
    public static void getStorageDevices() {
        Iterable<FileStore> stores = FileSystems.getDefault().getFileStores();
        ArrayList<String> deviceNames = new ArrayList<String>();
        int CT = 0;
        for (FileStore store : stores) {
            CT++;
            String name = store.name();
            deviceNames.add(name);
        }
        System.out.println("NO OF ST DEVICES : " + CT);
        System.out.println("DEVICE : " + deviceNames);
    }
}
