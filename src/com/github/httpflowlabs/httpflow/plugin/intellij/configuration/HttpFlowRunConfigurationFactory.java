package com.github.httpflowlabs.httpflow.plugin.intellij.configuration;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.BaseState;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HttpFlowRunConfigurationFactory extends ConfigurationFactory {

    protected HttpFlowRunConfigurationFactory(ConfigurationType type) {
        super(type);
    }

    @Override
    public @NotNull
    String getId() {
        return HttpFlowRunConfigurationType.ID;
    }

    @NotNull
    @Override
    public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new HttpFlowRunConfiguration(project, this, "HttpFlow");
    }

    @Nullable
    @Override
    public Class<? extends BaseState> getOptionsClass() {
        return HttpFlowRunConfigurationOptions.class;
    }

}
