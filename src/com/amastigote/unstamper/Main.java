/*
  AUTH | hwding
  DATE | Sep 10 2017
  DESC | text stamp remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote.unstamper;

import com.amastigote.unstamper.log.GeneralLogger;
import com.amastigote.unstamper.util.OptionManager;
import com.amastigote.unstamper.util.TaskRunner;
import com.sun.istack.internal.NotNull;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

public class Main {

    static {
        /* Disable Logging in Apache PDFBox */
        System.setProperty("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.NoOpLog");
    }

    public static void main(@NotNull String[] args) {
        CommandLine commandLine = null;
        try {
            commandLine = new DefaultParser()
                    .parse(OptionManager.buildOptions(), args);
        } catch (ParseException e) {
            GeneralLogger.Help.print();
            System.exit(0);
        }
        if (!commandLine.hasOption('k') && !commandLine.hasOption('t')) {
            GeneralLogger.Help.print();
            System.exit(0);
        } else {
            TaskRunner.init(
                    commandLine.getOptionValues('k'),
                    commandLine.hasOption('s'));

            if (commandLine.hasOption('i') && (commandLine.hasOption('o') || commandLine.hasOption('d'))) {
                if (commandLine.hasOption('d'))
                    TaskRunner
                            .procSingleFileDirectly(
                                    commandLine.getOptionValue('i')
                            );
                else
                    TaskRunner
                            .procSingleFile(
                                    commandLine.getOptionValue('i'),
                                    commandLine.getOptionValue('o')
                            );
            } else if (commandLine.hasOption('I') && (commandLine.hasOption('O') || commandLine.hasOption('d'))) {
                if (commandLine.hasOption('d'))
                    TaskRunner
                            .procMassFilesDirectly(
                                    commandLine.getOptionValue('I'),
                                    commandLine.hasOption('r')
                            );
                else
                    TaskRunner
                            .procMassFiles(
                                    commandLine.getOptionValue('I'),
                                    commandLine.getOptionValue('O'),
                                    commandLine.hasOption('r')
                            );
            } else {
                GeneralLogger.Help.print();
                System.exit(0);
            }
        }
    }
}