/*
  AUTH | hwding
  DATE | Aug 22 2017
  DESC | text stamp remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.unstamper.core;

import com.amastigote.unstamper.log.GeneralLogger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDStream;

import java.io.*;
import java.util.Arrays;

public class Processor {
    public static void process(File file, String[] strings, boolean cutTail) {
        try {
            if (!file.canWrite()) {
                GeneralLogger.File.notWritable(file.getName());
                return;
            }
            PDDocument pdDocument = PDDocument.load(file);
            pdDocument.getPages().forEach(pdPage -> {
                PDStream[] pdStreams = {null};
                pdPage.getContentStreams().forEachRemaining(pdStream -> {
                    try {
                        pdStreams[0] = pdStream;
                        InputStreamReader inputStreamReader = new InputStreamReader(pdStream.createInputStream());
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String[] curLine = {""};
                        if (strings != null)
                            while ((curLine[0] = bufferedReader.readLine()) != null) {
                                Arrays.asList(strings).forEach(s -> {
                                    if (curLine[0].contains(s)) {
                                        emptyContent(pdStream, file);
                                    }
                                });
                            }
                    } catch (IOException e) {
                        GeneralLogger.Processor.errorProcess(file.getName());
                    }
                });
                if (cutTail && pdStreams[0] != null) {
                    emptyContent(pdStreams[0], file);
                }
            });
            pdDocument.save(file);
            pdDocument.close();
        } catch (IOException e) {
            GeneralLogger.Processor.errorLoadPdf(file.getName());
        }
    }

    private static void emptyContent(PDStream pdStream, File file) {
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(pdStream.createOutputStream())
            );
            bufferedWriter.write("");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            GeneralLogger.Processor.errorProcess(file.getName());
        }
    }
}
