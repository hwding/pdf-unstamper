/*
  AUTH | hwding
  DATE | Nov 16 2018
  DESC | textual watermark remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.unstamper.log;

import com.sun.istack.internal.NotNull;

public class GeneralLogger {
    public static class Help {
        private static final String usage =
                "\nPDF-UnStamper ver. 0.2.1 by hwding@GitHub\n" +
                        "\nUsage:" +
                        "\n   [OPTION] -i [INPUT PDF] -k [KEYWORDS...] (-o [OUTPUT PDF])" +
                        "\n   [OPTION] -I [INPUT DIR] -k [KEYWORDS...] (-O [OUTPUT DIR])\n" +
                        "\nOptions:" +
                        "\n   -d,  --direct            directly modify the input file(s), option o/O is\n" +
                        "                            unnecessary when this option is on" +
                        "\n   -r,  --recursive         process files in the given dir recursively" +
                        "\n   -s,  --strict            use strict mode, a text area is considered as watermark\n" +
                        "                            only if its content strictly equals one of the keywords" +
                        "\n   -c,  --clear             clear all annotations in pages which contains the target\n" +
                        "                            textual watermark(s), if you encounter bordered frame issues,\n" +
                        "                            enable this switch";

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
            System.out.println(suffix + "Processing PDF file \'" + fn + "\' ...");
        }
    }
}
