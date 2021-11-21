package com.github.httpflowlabs.httpflow.plugin.intellij.configuration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class HttpFlowRunConfigurationType implements ConfigurationType {

    public static final String ID = "HttpFlowRunConfiguration";

    @NotNull
    @Override
    public String getDisplayName() {
        return "HttpFlow";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "HttpFlow run configuration type";
    }

    @Override
    public Icon getIcon() {
        return AllIcons.General.Information;
    }

    @NotNull
    @Override
    public String getId() {
        return ID;
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new HttpFlowRunConfigurationFactory(this)};
    }

}
