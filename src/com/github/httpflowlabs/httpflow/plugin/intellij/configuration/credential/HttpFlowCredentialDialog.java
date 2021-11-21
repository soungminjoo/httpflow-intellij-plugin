package com.github.httpflowlabs.httpflow.plugin.intellij.configuration.credential;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class HttpFlowCredentialDialog extends DialogWrapper {

    private JPanel panel = new JPanel();
    private GridBagConstraints c = new GridBagConstraints();
    private JTextField nameField;
    private JPasswordField valueField;

    private String name;
    private String value;

    public HttpFlowCredentialDialog() {
        this("", "");

        new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            nameField.requestFocus();
        }).start();
    }

    public HttpFlowCredentialDialog(String name, String value) {
        super(true);
        this.name = name;
        this.value = value;

        setTitle("Edit httpflow credentials");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        panel.setLayout(new GridBagLayout());
        panel.setMinimumSize(new Dimension(300, 70));

        addComponent(new JLabel("Name : "), 0, 0, 0, GridBagConstraints.NONE);
        this.nameField = new JTextField(name);
        addComponent(nameField, 1, 0, 1, GridBagConstraints.HORIZONTAL);
        addComponent(new JLabel("Value : "), 0, 1, 0, GridBagConstraints.NONE);
        this.valueField = new JPasswordField(value);
        addComponent(valueField, 1, 1, 1, GridBagConstraints.HORIZONTAL);

        return panel;
    }

    private void addComponent(JComponent component, int x, int y, double wx, int fill) {
        c.gridx = x;
        c.gridy = y;
        c.weightx = wx;
        c.fill = fill;
        panel.add(component, c);
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        this.name = this.nameField.getText();
        this.value = new String(this.valueField.getPassword());
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
