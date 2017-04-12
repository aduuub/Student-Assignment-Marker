package marker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * A assignment marking assistant designed to make marking student code easier
 * Compares two jars based on required submission guidelines and compares
 * student version with original version
 * <p>
 * Also detects additional java files submitted and prints them out
 *
 * @author Kai Yang
 * @author Adam Wareing
 */
public class Marker {

    public final String dir = "marking/"; // folder name
    private List<String> files;
    int addFiles = 0;
    int missingFiles = 0;

    private List<String> missing;
    private List<String> additional;

    public Marker() {
        files = new ArrayList<>();
        missing = new ArrayList<>();
        additional = new ArrayList<>();
    }


    /**
     * Unzips the master jar to working directory and adds all files to filelist
     *
     * @param filename
     */
    public void unzipMasterJar(String filename) {
        try {
            // Open the zip file
            ZipFile zipFile = new ZipFile(filename);
            Enumeration<?> enu = zipFile.entries();
            while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                String name = zipEntry.getName();

                // Do we need to create a directory ?
                File file = new File(name);
                if (name.endsWith("/")) {
                    file.mkdirs();
                    continue;
                }

                File parent = file.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                // Extract the file
                InputStream is = zipFile.getInputStream(zipEntry);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = is.read(bytes)) >= 0) {
                    fos.write(bytes, 0, length);
                }
                if (getFileExtension(file).equals("java")) {
                    files.add(name);
                }
                is.close();
                fos.close();

            }
            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Master unzip done");

    }


    /**
     * Unzips a zip(or jar) file to a specified directory affix
     *
     * @param filename zip file to be unzipped
     * @param affix    folder to unzip it to
     */
    public void unzipJar(String filename, String affix) {
        try {
            // Open the zip file
            ZipFile zipFile = new ZipFile(filename);
            Enumeration<?> enu = zipFile.entries();
            while (enu.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enu.nextElement();

                String name = affix + zipEntry.getName();

                // Do we need to create a directory ?
                File file = new File(name);
                if (name.endsWith("/")) {
                    file.mkdirs();
                    continue;
                }

                File parent = file.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }

                // Extract the file
                InputStream is = zipFile.getInputStream(zipEntry);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = is.read(bytes)) >= 0) {
                    fos.write(bytes, 0, length);
                }
                is.close();
                fos.close();

            }
            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Student unzip done");
    }


    /**
     * Runs diff on each file to find differences
     */
    protected String diffFiles() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : files) {
            System.out.println(s);
            stringBuilder.append(s + "\n");
            File studentCurrent = new File(dir + s);
            if (studentCurrent.exists())
                stringBuilder.append(Diff.diff(new String[]{s, dir + s}) + "\n");
            else {
                stringBuilder.append("ERROR: This file was not present in student submission" + "\n");
                missing.add(s);
                missingFiles++;
            }
            stringBuilder.append("================END OF FILE================ \n");
        }
        return stringBuilder.toString();

    }


    /**
     * Recursively goes through a directory to find all java files not present
     * in mark scheme
     *
     * @param folder
     * @throws FileNotFoundException
     */
    protected String findExtras(File folder) throws FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();

        File[] fileList = folder.listFiles();
        for (File f : fileList) {
            String filePath = f.getPath();
            String[] filePathSplit = filePath.split("\\\\|/");
            ArrayList<String> filePathSplitList = new ArrayList(Arrays.asList(filePathSplit));
            filePathSplitList.remove(0);
            filePath = String.join("/", filePathSplitList);

            if (filePath.endsWith("java") && !files.contains(filePath)) {
                System.out.println(filePath);
                stringBuilder.append(filePath + "\n");
                additional.add(filePath);
                Scanner sc = new Scanner(f);
                addFiles++;
                while (sc.hasNextLine()) {
                    stringBuilder.append(sc.nextLine() + "\n");
                }
                stringBuilder.append("===========END OF FILE=========== \n");
                sc.close();
            }
            if (f.isDirectory()) {
                stringBuilder.append(findExtras(f));
            }
        }
        return stringBuilder.toString();
    }


    /**
     * Gets a file extension
     *
     * @param file
     * @return
     */
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else
            return "";
    }


    /**
     * Recursively deletes files
     *
     * @param f
     * @throws IOException
     */
    public void deleteFile(File f) throws IOException {
        System.out.println(f);
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                deleteFile(c);
        }
        if (!f.delete()) {
            System.out.println("failed to deleteFile file " + f.getPath());
        }
    }


    /**
     * Gets the results from th
     * @return
     */
    protected String getFormattedTextOfChangedFiles() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Parsing complete. \nNumber of files compared : " + files.size() + "\n");
        stringBuilder.append("Number of additional files: " + addFiles + "\n");

        if (addFiles != 0) {
            stringBuilder.append("Additional filenames: \n");

            for (String s : additional) {
                stringBuilder.append(s + "\n");
            }
        }
        stringBuilder.append("Number of missing files : " + missingFiles + "\n");
        if (missingFiles != 0) {
            stringBuilder.append("Missing filenames: " + "\n");

            for (String s : missing) {
                stringBuilder.append(s);
            }
        }
        return stringBuilder.toString();
    }

}
