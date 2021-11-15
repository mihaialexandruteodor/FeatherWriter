package com.mihaialexandruteodor.FeatherWriter.utlis;

import java.io.File;
import java.io.InputStream;

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
        boolean isMac = osName.startsWith("mac");
        boolean isLinux = osName.startsWith("linux");
        boolean isWindows = osName.startsWith("windows");
        if (isMac) {
            downloadPath = System.getProperty("file.separator") + "Users" + System.getProperty("file.separator")
                    + System.getProperty("user.name") + System.getProperty("file.separator") + "Documents"
                    + System.getProperty("file.separator") + "TeoMongoText" + System.getProperty("file.separator")
                    + "Settings.ini";
        } else if (isLinux) {
            downloadPath = System.getProperty("file.separator") + "home" + System.getProperty("file.separator")
                    + System.getProperty("user.name") + System.getProperty("file.separator") + "Documents"
                    + System.getProperty("file.separator") + "TeoMongoText" + System.getProperty("file.separator")
                    + "Settings.ini";
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

    }
}
