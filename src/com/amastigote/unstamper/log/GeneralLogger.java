/*
  AUTH | hwding
  DATE | Sep 05 2017
  DESC | text stamp remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.unstamper.log;

import com.sun.istack.internal.NotNull;

public class GeneralLogger {
    public static class Help {
        private static final String usage =
                "\nPDF-UnStamper ver. 0.1.2 by hwding@GitHub\n" +
                        "\nUsage:" +
                        "\n   [OPTION] -i [INPUT PDF] -k [KEYWORDS...] (-o [OUTPUT PDF])" +
                        "\n   [OPTION] -I [INPUT DIR] -k [KEYWORDS...] (-O [OUTPUT DIR])\n" +
                        "\nOptions:" +
                        "\n   -d,  --directly          directly modify the input file(s), which makes option o/O unnecessary" +
                        "\n   -r,  --recursive         process files in the given dir recursively\n";

        public static void print() {
            System.out.println(usage);
        }
    }

    public static class File {
        private static final String suffix = "[File] ";

        public static void nameDuplicate(@NotNull String fn) {
            System.err.println(suffix + "Input file/dir \'" + fn + "\' has the same name as output file/dir, skipping");
        }

        public static void notExist(@NotNull String fn) {
            System.err.println(suffix + "File/dir \'" + fn + "\' not exist or is not a file/dir, skipping");
        }

        public static void error(@NotNull String fn) {
            System.err.println(suffix + "Error when generating stub file/dir for \'" + fn + "\', skipping");
        }

        public static void notWritable(@NotNull String fn) {
            System.err.println(suffix + "File \'" + fn + "\' not writable, skipping");
        }
    }

    public static class Processor {
        private static final String suffix = "[Processor] ";

        public static void errorLoadPdf(@NotNull String fn) {
            System.err.println(suffix + "Error loading file \'" + fn + "\' as PDF, skipping");
        }

        public static void errorProcess(@NotNull String fn) {
            System.err.println(suffix + "Error processing certain page of \'" + fn + "\', skipping");
        }

        public static void procInProgress(@NotNull String fn) {
            System.out.print(suffix + "Processing PDF file \'" + fn + "\' ...");
        }

        public static void procFinished() {
            System.out.println(" GOOD");
        }
    }
}
