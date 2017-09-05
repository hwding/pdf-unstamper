/*
  AUTH | hwding
  DATE | Sep 05 2017
  DESC | text stamp remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.unstamper.io;

import com.sun.istack.internal.NotNull;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class IOHandler {
    public static File getFile(@NotNull String fn) {
        File file = new File(fn);
        return file.exists() && file.isFile() ? file : null;
    }

    public static File getCopiedFile(
            @NotNull String ifn,
            @NotNull String ofn)
            throws IOException, FileNameDuplicateException {
        File fileI = new File(ifn);
        File fileO = new File(ofn);
        if (fileI.getCanonicalPath().equals(fileO.getCanonicalPath()))
            throw new FileNameDuplicateException();
        else if (fileI.exists() && fileI.isFile()) {
            FileUtils.copyFile(fileI, fileO);
            return fileO;
        } else
            return null;
    }

    public static Iterator<File> getFiles(
            @NotNull String idn,
            @NotNull boolean recursive) {
        File dirI = new File(idn);
        if (dirI.exists() && dirI.isDirectory()) {
            return FileUtils.iterateFiles(dirI, new String[]{"pdf"}, recursive);
        } else
            return null;
    }

    public static Iterator<File> getCopiedFiles(
            @NotNull String idn,
            @NotNull String odn,
            @NotNull boolean recursive)
            throws IOException, FileNameDuplicateException {
        File dirI = new File(idn);
        File dirO = new File(odn);
        if (dirI.getCanonicalPath().equals(dirO.getCanonicalPath()))
            throw new FileNameDuplicateException();
        else if (dirI.exists() && dirI.isDirectory()) {
            FileUtils.copyDirectory(dirI, dirO);
            return FileUtils.iterateFiles(dirO, new String[]{"pdf"}, recursive);
        } else
            return null;
    }

    public static class FileNameDuplicateException extends Exception {
    }
}
