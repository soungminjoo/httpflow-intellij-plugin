package com.github.httpflowlabs.httpflow.plugin.intellij;

import com.github.httpflowlabs.httpflow.plugin.intellij.configuration.HttpFlowRunConfiguration;
import com.github.httpflowlabs.httpflow.plugin.intellij.configuration.HttpFlowRunConfigurationType;
import com.intellij.execution.ProgramRunnerUtil;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class ExecuteHfdRunnerAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);

        runHfdFile(e.getProject(), virtualFile);
    }

    public void runHfdFile(@NotNull Project project, VirtualFile virtualFile) {
        RunManagerImpl runManager = (RunManagerImpl) RunManager.getInstance(project);

        RunnerAndConfigurationSettings configurationSettings = runManager.createConfiguration(virtualFile.getName(), HttpFlowRunConfigurationType.class);
        HttpFlowRunConfiguration configuration = (HttpFlowRunConfiguration) configurationSettings.getConfiguration();
        configuration.setHfdFileName(virtualFile.getCanonicalPath());

        runManager.addConfiguration(configurationSettings, false);
        runManager.setSelectedConfiguration(configurationSettings);

        ProgramRunnerUtil.executeConfiguration(runManager.getSelectedConfiguration(), DefaultRunExecutor.getRunExecutorInstance());
    }

    @Override
    public void update(AnActionEvent e) {
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        e.getPresentation().setText("Run '" + psiFile.getName() + "'");
        e.getPresentation().setIcon(AllIcons.Actions.Execute);
        e.getPresentation().setEnabledAndVisible(psiFile.getName().toLowerCase().endsWith(".hfd"));
    }

}
