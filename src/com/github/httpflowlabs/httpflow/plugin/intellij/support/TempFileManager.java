package com.github.httpflowlabs.httpflow.plugin.intellij.support;

import com.github.httpflowlabs.httpflow.plugin.intellij.configuration.credential.CredentialHidingCommandLine;
import com.github.httpflowlabs.httpflow.plugin.intellij.configuration.credential.TableStyleCredentialManager;
import com.intellij.execution.configurations.GeneralCommandLine;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TempFileManager {

    public static final File HTTPFLOW_HOME = new File(System.getProperty("user.home"), ".httpflow-jenkins");
    public static final File HTTPFLOW_JAR_FILE = new File(HTTPFLOW_HOME, "httpflow-console-0.0.1.jar");

    private File shellFile;
    private boolean isWindowsOs;
    private TableStyleCredentialManager credentialManager = new TableStyleCredentialManager();

    public TempFileManager() {
        this.isWindowsOs = System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    public void checkHttpFlowInstalled() {
        if (!HTTPFLOW_JAR_FILE.exists()) {
            if (!HTTPFLOW_HOME.exists()) {
                HTTPFLOW_HOME.mkdirs();
            }
            HttpFlowJenkinsUtils.writeFile(HTTPFLOW_JAR_FILE, this.getClass().getResourceAsStream("/" + HTTPFLOW_JAR_FILE.getName()));
        }
    }

    public GeneralCommandLine buildCommandLine(String hfdFilePath) {
        File hfdFile = new File(hfdFilePath);

        String ext = (isWindowsOs) ? ".bat" : ".sh";
        String shellFileName = ShellCommandExecutor.SHELL_FILE_NAME + ext;
        this.shellFile = new File(hfdFile.getParentFile(), shellFileName);
        if (!shellFile.exists()) {
            HttpFlowJenkinsUtils.writeFile(shellFile, getShellFileContents());
        }

        CredentialHidingCommandLine commandLine = new CredentialHidingCommandLine(buildCmdAndArgs(hfdFile));
        commandLine.setConsoleViewPrintCommand("httpflow " + hfdFile.getName() + " " + getCredentialArgs(true));
        commandLine.setWorkDirectory(hfdFile.getParentFile().getAbsolutePath());
        return commandLine;
    }

    @NotNull
    private String[] buildCmdAndArgs(File hfdFile) {
        List<String> cmdBuilder = new ArrayList<>();
        if (isWindowsOs) {
            cmdBuilder.add("cmd.exe");
            cmdBuilder.add("/c");
        } else {
            cmdBuilder.add("sh");
        }
        cmdBuilder.add(shellFile.getName());
        cmdBuilder.add(hfdFile.getName());
        cmdBuilder.add(getCredentialArgs(false));
        return cmdBuilder.toArray(new String[0]);
    }

    private ByteArrayInputStream getShellFileContents() {
        String args = (isWindowsOs)? "%*" : "$@";
        return new ByteArrayInputStream(("java -jar -DDumpMode=file " + HTTPFLOW_JAR_FILE.getAbsolutePath() + " " + args).getBytes());
    }

    public void deleteInstantFiles() {
        shellFile.delete();
    }

    private String getCredentialArgs(boolean needMask) {
        StringBuilder builder = new StringBuilder();
        List<String[]> list = credentialManager.loadAllCredentials(needMask);
        for (String[] item : list) {
            builder.append(item[0]).append("=").append(item[1]).append(" ");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

}
