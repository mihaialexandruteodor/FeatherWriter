package com.mihaialexandruteodor.FeatherWriter.utlis;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class DataSingleton {

    private static DataSingleton single_instance = null;
    String downloadPath;

    private DataSingleton() {

        setDownloadPath();
    }

    public static DataSingleton getInstance() {
        if (single_instance == null)
            single_instance = new DataSingleton();

        return single_instance;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath() {

        String osName = System.getProperty("os.name").toLowerCase();
        System.out.println("THIS OS: " + osName);
        boolean isMac = osName.startsWith("mac");
        boolean isWindows = osName.startsWith("windows");
        boolean isDocker = osName.startsWith("linux");
        if (isMac) {
            downloadPath = System.getProperty("file.separator") + "Users" + System.getProperty("file.separator")
                    + System.getProperty("user.name") + System.getProperty("file.separator") + "Documents"
                    + System.getProperty("file.separator") + "FeatherWriter" + System.getProperty("file.separator");

        } else if (isWindows) {
            String myDocuments = null;

            try {
                Process p = Runtime.getRuntime().exec(
                        "reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
                p.waitFor();

                InputStream in = p.getInputStream();
                byte[] b = new byte[in.available()];
                in.read(b);
                in.close();

                myDocuments = new String(b);
                myDocuments = myDocuments.split("\\s\\s+")[4];

            } catch (Throwable t) {
                t.printStackTrace();
            }

            downloadPath = myDocuments + System.getProperty("file.separator") + "FeatherWriter"
                    + System.getProperty("file.separator");

            File directory = new File(downloadPath);
            if (! directory.exists()){
                directory.mkdir();
                // If you require it to make the entire directory path including parents,
                // use directory.mkdirs(); here instead.
            }
        }
        else if(isDocker)
        {
            downloadPath = System.getProperty("file.separator") + "FeatherWriter" + System.getProperty("file.separator");
            File directory = new File(downloadPath);
            if (! directory.exists())
                directory.mkdir();
        }

    }


}
