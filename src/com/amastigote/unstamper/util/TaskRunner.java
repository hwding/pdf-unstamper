/*
  AUTH | hwding
  DATE | Sep 10 2017
  DESC | text stamp remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.unstamper.util;

import com.amastigote.unstamper.core.Processor;
import com.amastigote.unstamper.io.IOHandler;
import com.amastigote.unstamper.log.GeneralLogger;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class TaskRunner {
    private static String[] keywords;
    private static boolean useStrict;

    public static void init(
            @NotNull String[] keywords,
            @NotNull boolean useStrict) {
        TaskRunner.keywords = keywords;
        TaskRunner.useStrict = useStrict;
    }

    public static void procSingleFile(
            @NotNull String ifn,
            @NotNull String ofn) {
        try {
            File file = IOHandler.getCopiedFile(ifn, ofn);
            if (file == null) {
                GeneralLogger.File.notExist(ifn);
                return;
            }
            submitToProcessor(file);
        } catch (IOException e) {
            GeneralLogger.File.error(ifn);
        } catch (IOHandler.FileNameDuplicateException e) {
            GeneralLogger.File.nameDuplicate(ifn);
        }
    }

    private static void submitToProcessor(@NotNull File file) {
        Processor.process(file, keywords, useStrict);
    }

    public static void procSingleFileDirectly(@NotNull String ifn) {
        File file = IOHandler.getFile(ifn);
        if (file == null) {
            GeneralLogger.File.notExist(ifn);
            return;
        }
        submitToProcessor(file);
    }

    public static void procMassFiles(
            @NotNull String idn,
            @NotNull String odn,
            @NotNull boolean recursively) {
        Iterator<File> fileIterator = null;
        try {
            fileIterator = IOHandler.getCopiedFiles(idn, odn, recursively);
        } catch (IOException e) {
            GeneralLogger.File.error(idn);
        } catch (IOHandler.FileNameDuplicateException e) {
            GeneralLogger.File.nameDuplicate(idn);
        }
        procIterator(fileIterator, idn);
    }

    public static void procMassFilesDirectly(
            @NotNull String idn,
            @NotNull boolean recursively) {
        Iterator<File> fileIterator = IOHandler.getFiles(idn, recursively);
        procIterator(fileIterator, idn);
    }

    private static void procIterator(
            @Nullable Iterator<File> fileIterator,
            @NotNull String idn) {
        if (fileIterator == null) {
            GeneralLogger.File.notExist(idn);
            return;
        }
        fileIterator.forEachRemaining(TaskRunner::submitToProcessor);
    }
}
