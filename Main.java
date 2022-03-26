package com.company.javaCore.download;

import com.company.javaCore.save.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void unZip(String zipName, String destination) throws IOException {
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipName))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = new File(destination, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdir()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void returnGameProgress(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            GameProgress gameProgress = (GameProgress) ois.readObject();
            System.out.println(gameProgress.toString());
            System.out.println();

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }


    }

    public static void main(String[] args) throws IOException, InterruptedException {
        unZip("d://games/savegames/save.zip", "d://games/savegames/save");

        Thread.sleep(100);

        returnGameProgress("d://games/savegames/save/save1.dat");
        returnGameProgress("d://games/savegames/save/save2.dat");
        returnGameProgress("d://games/savegames/save/save3.dat");


    }
}

