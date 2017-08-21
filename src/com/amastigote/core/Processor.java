/*
  AUTH | hwding
  DATE | Aug 21 2017
  DESC | text stamp remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.core;

import com.amastigote.log.GeneralLogger;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.*;
import java.util.Arrays;

public class Processor {
    public static void process(File file, String[] strings) {
        try {
            if (!file.canWrite()) {
                GeneralLogger.File.notWritable(file.getName());
                return;
            }
            PDDocument pdDocument = PDDocument.load(file);
            pdDocument.getPages().forEach(pdPage -> pdPage.getContentStreams().forEachRemaining(pdStream -> {
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(pdStream.createInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String[] curLine = {""};
                    while ((curLine[0] = bufferedReader.readLine()) != null) {
                        Arrays.asList(strings).forEach(s -> {
                            if (curLine[0].contains(s)) {
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
                        });
                    }
                } catch (IOException e) {
                    GeneralLogger.Processor.errorProcess(file.getName());
                }
            }));
            pdDocument.save(file);
            pdDocument.close();
        } catch (IOException e) {
            GeneralLogger.Processor.errorLoadPdf(file.getName());
        }
    }
}
