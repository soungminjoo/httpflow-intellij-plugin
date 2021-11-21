package com.github.httpflowlabs.httpflow.plugin.intellij.filetype;

import com.github.httpflowlabs.httpflow.plugin.intellij.configuration.HttpFlowFileEventListener;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class HttpFlowFileType extends LanguageFileType {

    public static final HttpFlowFileType INSTANCE = new HttpFlowFileType();

    private HttpFlowFileType() {
        super(HttpFlowLanguage.INSTANCE);
        HttpFlowFileEventListener.INSTANCE.register();
    }

    @NotNull
    @Override
    public String getName() {
        return "Httpflow File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Httpflow definition file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "hfd";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return AllIcons.General.Information;
    }

}