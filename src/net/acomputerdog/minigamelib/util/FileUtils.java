package net.acomputerdog.minigamelib.util;

import java.io.*;

public class FileUtils {
    public static void copyFile(File outFile, InputStream in) {
        try (OutputStream out = new FileOutputStream(outFile)) {
            byte[] buff = new byte[256];
            while (in.available() > 0) {
                int read = in.read(buff);
                out.write(buff, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException("Exception copying file", e);
        }
    }
}
