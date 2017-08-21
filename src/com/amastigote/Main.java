/*
  AUTH | hwding
  DATE | Aug 21 2017
  DESC | text stamp remover for PDF files
  MAIL | m@amastigote.com
  GITH | github.com/hwding
 */
package com.amastigote;

import com.amastigote.log.GeneralLogger;
import com.amastigote.util.OptionManager;
import com.amastigote.util.TaskRunner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) {
        CommandLine commandLine = null;
        try {
            commandLine = new DefaultParser()
                    .parse(OptionManager.buildOptions(), args);
        } catch (ParseException e) {
            GeneralLogger.Help.print();
            System.exit(0);
        }
        if (!commandLine.hasOption('k')) {
            GeneralLogger.Help.print();
            System.exit(0);
        } else {
            TaskRunner.init(commandLine.getOptionValues('k'));

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