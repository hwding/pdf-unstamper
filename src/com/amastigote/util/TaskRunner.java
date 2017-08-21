package com.amastigote.util;

import com.amastigote.core.Processor;
import com.amastigote.io.IOHandler;
import com.amastigote.log.GeneralLogger;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class TaskRunner {
    public static String[] keywords;

    public static void init(String[] keywords) {
        TaskRunner.keywords = keywords;
    }

    public static void procSingleFile(String ifn, String ofn) {
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

    private static void submitToProcessor(File file) {
        Processor.process(file, keywords);
    }

    public static void procSingleFileDirectly(String ifn) {
        File file = IOHandler.getFile(ifn);
        if (file == null) {
            GeneralLogger.File.notExist(ifn);
            return;
        }
        submitToProcessor(file);
    }

    public static void procMassFiles(String idn, String odn, boolean recursively) {
        try {
            Iterator<File> fileIterator = IOHandler.getCopiedFiles(idn, odn, recursively);
            if (fileIterator == null) {
                GeneralLogger.File.notExist(odn);
                return;
            }
            while (fileIterator.hasNext()) {
                submitToProcessor(fileIterator.next());
            }
        } catch (IOException e) {
            GeneralLogger.File.error(idn);
        } catch (IOHandler.FileNameDuplicateException e) {
            GeneralLogger.File.nameDuplicate(idn);
        }
    }

    public static void procMassFilesDirectly(String idn, boolean recursively) {
        Iterator<File> fileIterator = IOHandler.getFiles(idn, recursively);
        if (fileIterator == null) {
            GeneralLogger.File.notExist(idn);
            return;
        }
        while (fileIterator.hasNext()) {
            submitToProcessor(fileIterator.next());
        }
    }
}
