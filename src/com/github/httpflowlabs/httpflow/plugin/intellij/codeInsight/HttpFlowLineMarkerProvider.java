package com.github.httpflowlabs.httpflow.plugin.intellij.codeInsight;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProvider;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPlainTextFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class HttpFlowLineMarkerProvider implements LineMarkerProvider {

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement element) {
        if (element instanceof PsiPlainTextFile) {
            PsiPlainTextFile textFile = (PsiPlainTextFile) element;
            if (textFile.getName().toLowerCase().endsWith(".hfd")) {
                return new LineMarkerInfo<>(element, new TextRange(0, 0), AllIcons.Actions.Execute, null,
                        new HttpFlowGutterIconHandler(), GutterIconRenderer.Alignment.LEFT);
            }
        }
        return null;
    }

}