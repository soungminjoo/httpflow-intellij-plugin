package com.github.httpflowlabs.httpflow.plugin.intellij.filetype;

import com.intellij.lang.Language;

public class HttpFlowLanguage extends Language {

    public static final HttpFlowLanguage INSTANCE = new HttpFlowLanguage();

    private HttpFlowLanguage() {
        super("Httpflow");
    }

}