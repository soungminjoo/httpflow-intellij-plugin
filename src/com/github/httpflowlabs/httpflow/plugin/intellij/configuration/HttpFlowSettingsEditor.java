package com.github.httpflowlabs.httpflow.plugin.intellij.configuration;

import com.github.httpflowlabs.httpflow.plugin.intellij.configuration.credential.HttpFlowCredentialDialog;
import com.github.httpflowlabs.httpflow.plugin.intellij.configuration.credential.HttpFlowCredentialsTableModel;
import com.github.httpflowlabs.httpflow.plugin.intellij.configuration.credential.TableStyleCredentialManager;
import com.intellij.credentialStore.Credentials;
import com.intellij.execution.impl.CheckableRunConfigurationEditor;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.ui.DoubleClickListener;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpFlowSettingsEditor extends SettingsEditor<HttpFlowRunConfiguration> implements CheckableRunConfigurationEditor {

    public static final String[] COLUMN_NAMES = {"Name", "Value"};

    private DefaultTableModel dm;
    private Map<String, String> notSavedPassword = new HashMap<>();

    private TableStyleCredentialManager HttpFlowIntellijUtils = new TableStyleCredentialManager();

    @NotNull
    @Override
    protected JComponent createEditor() {
        buildDataModel();

        JBTable myTable = new JBTable(dm);
        myTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        new DoubleClickListener() {
            protected boolean onDoubleClick(MouseEvent e) {
                onEditButton(myTable);
                return true;
            }
        }.installOn(myTable);

        JPanel panel = decorateToolbar(myTable);

        return panel;
    }

    private void buildDataModel() {
        Credentials keyCountCred = HttpFlowIntellijUtils.getKeyCountCredentials();
        if (keyCountCred == null) {
            this.dm = new HttpFlowCredentialsTableModel(new String[0][0], COLUMN_NAMES);

        } else {
            List<String[]> list = HttpFlowIntellijUtils.loadAllCredentials(true);
            this.dm = new HttpFlowCredentialsTableModel(list.toArray(new String[0][0]), COLUMN_NAMES);
        }

    }

    private JPanel decorateToolbar(JBTable myTable) {
        return ToolbarDecorator.createDecorator(myTable)
                    .setAddAction(button -> {
                        onNewButton();
                    })
                    .setRemoveAction(button -> {
                        onRemoveButton((JBTable) button.getContextComponent());
                    })
                    .setEditAction(button -> {
                        onEditButton((JBTable) button.getContextComponent());
                    })
                    .disableUpDownActions()
                    .createPanel();
    }

    private void onNewButton() {
        HttpFlowCredentialDialog dialog = new HttpFlowCredentialDialog();
        dialog.show();

        if (dialog.isOK()) {
            if (isExistingName(dialog.getName())) {
                JOptionPane.showMessageDialog(getComponent(), "Same name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            notSavedPassword.put(dialog.getName(), dialog.getValue());
            dm.addRow(new String[]{dialog.getName(), getMaskedValue(dialog.getValue())});
        }
    }

    private void onRemoveButton(JBTable table) {
        int selectedRow = table.getSelectedRow();
        dm.removeRow(selectedRow);
        if (dm.getRowCount() > 0) {
            if (selectedRow < dm.getRowCount()) {
                table.setRowSelectionInterval(selectedRow, selectedRow);
            } else {
                table.setRowSelectionInterval(dm.getRowCount()-1, dm.getRowCount()-1);
            }
        }
    }

    private void onEditButton(JBTable table) {
        int rowIdx = table.getSelectedRow();

        String name = (String) dm.getValueAt(rowIdx, 0);
        String value = HttpFlowIntellijUtils.getCredentialsByRowIndex(rowIdx).getPasswordAsString();
        HttpFlowCredentialDialog dialog = new HttpFlowCredentialDialog(name, value);
        dialog.show();

        if (dialog.isOK()) {
            if (isExistingName(dialog.getName())) {
                JOptionPane.showMessageDialog(getComponent(), "Same name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dm.setValueAt(dialog.getName(), rowIdx, 0);
            dm.setValueAt(getMaskedValue(dialog.getValue()), rowIdx, 1);
            notSavedPassword.put(dialog.getName(), dialog.getValue());
        }
    }

    private boolean isExistingName(String newName) {
        for (int i = 0; i < dm.getRowCount(); i++) {
            String name = (String) dm.getValueAt(i, 0);
            if (name.equals(newName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void applyEditorTo(@NotNull HttpFlowRunConfiguration httpFlowRunConfiguration) {
        List<String[]> list = HttpFlowIntellijUtils.loadAllCredentials(false);
        Map<String, String> oldCredentials = new HashMap<>();
        list.forEach(cred -> {
            oldCredentials.put(cred[0], cred[1]);
        });

        HttpFlowIntellijUtils.setKeyCountCredentials(dm.getRowCount());
        for (int i = 0; i < dm.getRowCount(); i++) {
            String name = (String) dm.getValueAt(i, 0);
            String value = null;
            if (notSavedPassword.containsKey(name)) {
                value = notSavedPassword.get(name);
            } else {
                value = oldCredentials.get(name);
            }

            HttpFlowIntellijUtils.setCredentialsByRowIndex(i, name, value);
        }

        notSavedPassword = new HashMap<>();
    }



    @Override
    protected void resetEditorFrom(HttpFlowRunConfiguration httpFlowRunConfiguration) {
        notSavedPassword = new HashMap<>();
    }

    @Override
    public void checkEditorData(Object o) {
    }

    @NotNull
    private String getMaskedValue(String password) {
        return password.replaceAll(".", "*");
    }

}
