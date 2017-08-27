/*
  AUTH | hwding
  DATE | Aug 25 2017
  DESC | text stamp remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.unstamper.log;

public class GeneralLogger {
    public static class Help {
        private static final String usage =
                "\nPDF-UnStamper ver. 0.1.1 by hwding@GitHub\n" +
                        "\nUsage: " +
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
        public static void nameDuplicate(String fn) {
            System.err.println("input file/dir " + fn + " has the same name as output file/dir, skipping file/dir");
        }

        public static void notExist(String fn) {
            System.err.println(fn + " not exist or is not a file/dir, skipping file/dir");
        }

        public static void error(String fn) {
            System.err.println("error when generating stub file/dir for " + fn + ", skipping file");
        }

        public static void notWritable(String fn) {
            System.err.println(fn + " not writable, skipping");
        }
    }

    public static class Processor {
        public static void errorLoadPdf(String fn) {
            System.err.println("error loading " + fn + " as a PDF file, skipping file");
        }

        public static void errorProcess(String fn) {
            System.err.println("error processing " + fn + ", skipping minimum unit");
        }
    }
}
