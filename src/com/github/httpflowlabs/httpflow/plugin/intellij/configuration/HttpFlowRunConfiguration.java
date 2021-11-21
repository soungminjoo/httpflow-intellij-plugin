package com.github.httpflowlabs.httpflow.plugin.intellij.configuration;

import com.github.httpflowlabs.httpflow.plugin.intellij.support.TempFileManager;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.process.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HttpFlowRunConfiguration extends RunConfigurationBase<HttpFlowRunConfigurationOptions> {

    protected HttpFlowRunConfiguration(Project project, ConfigurationFactory factory, String name) {
        super(project, factory, name);
    }

    @NotNull
    @Override
    protected HttpFlowRunConfigurationOptions getOptions() {
        return (HttpFlowRunConfigurationOptions) super.getOptions();
    }

    public void setHfdFileName(String hfdFileName) {
        getOptions().setHfdFilePath(hfdFileName);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new HttpFlowSettingsEditor();
    }

    @Override
    public void checkConfiguration() {
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) {
        return new CommandLineState(executionEnvironment) {
            @NotNull
            @Override
            protected ProcessHandler startProcess() throws ExecutionException {
                TempFileManager tempFileManager = new TempFileManager();
                tempFileManager.checkHttpFlowInstalled();

                GeneralCommandLine commandLine = tempFileManager.buildCommandLine(getOptions().getHfdFilePath());

                OSProcessHandler handler = new ColoredProcessHandler(commandLine);
                handler.addProcessListener(new ProcessAdapter() {
                    public void processTerminated(@NotNull ProcessEvent event) {
                        tempFileManager.deleteInstantFiles();
                    }
                });
                ProcessTerminatedListener.attach(handler);
                return handler;
            }
        };
    }

}
