package com.github.httpflowlabs.httpflow.plugin.intellij.codeInsight;

import com.github.httpflowlabs.httpflow.plugin.intellij.ExecuteHfdRunnerAction;
import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

import java.awt.event.MouseEvent;

public class HttpFlowGutterIconHandler implements GutterIconNavigationHandler {

    @Override
    public void navigate(MouseEvent mouseEvent, PsiElement psiElement) {
        PsiFile psiFile = (PsiFile) psiElement;
        new ExecuteHfdRunnerAction().runHfdFile(psiElement.getProject(), psiFile.getVirtualFile());
    }

}
