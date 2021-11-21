package com.github.httpflowlabs.httpflow.plugin.intellij.configuration;

import com.intellij.execution.configurations.RunConfigurationOptions;
import com.intellij.openapi.components.StoredProperty;

public class HttpFlowRunConfigurationOptions extends RunConfigurationOptions {

    private final StoredProperty<String> hfdFilePath = string("").provideDelegate(this, "hfdFilePath");

    public String getHfdFilePath() {
        return hfdFilePath.getValue(this);
    }

    public void setHfdFilePath(String hfdFileName) {
        this.hfdFilePath.setValue(this, hfdFileName);
    }

}
