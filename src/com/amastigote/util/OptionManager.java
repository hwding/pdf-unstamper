package com.amastigote.util;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionManager {
    private final static Option optionLCI = new Option("i", true, null);
    private final static Option optionLCO = new Option("o", true, null);
    private final static Option optionUCI = new Option("I", true, null);
    private final static Option optionUCO = new Option("O", true, null);
    private final static Option optionK = new Option("k", true, null);
    private final static Option optionD = new Option("d", "directly", false, null);
    private final static Option optionR = new Option("r", "recursive", false, null);

    public static Options buildOptions() {
        Options options = new Options();
        optionLCI.setArgs(1);
        optionLCI.setArgs(1);
        optionUCI.setArgs(1);
        optionUCO.setArgs(1);
        optionK.setRequired(true);
        options.addOption(optionLCI);
        options.addOption(optionLCO);
        options.addOption(optionUCI);
        options.addOption(optionUCO);
        options.addOption(optionD);
        options.addOption(optionR);
        options.addOption(optionK);
        return options;
    }
}
