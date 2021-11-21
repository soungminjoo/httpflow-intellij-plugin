package com.github.httpflowlabs.httpflow.plugin.intellij.configuration.credential;

import com.intellij.execution.configurations.GeneralCommandLine;
import org.jetbrains.annotations.NotNull;

public class CredentialHidingCommandLine extends GeneralCommandLine {

    private String consoleViewPrintCommand = "";

    public CredentialHidingCommandLine(String... command) {
        super(command);
    }

    @NotNull
    @Override
    public String getCommandLineString() {
        return consoleViewPrintCommand;
    }

    public void setConsoleViewPrintCommand(String consoleViewPrintCommand) {
        this.consoleViewPrintCommand = consoleViewPrintCommand;
    }

}
