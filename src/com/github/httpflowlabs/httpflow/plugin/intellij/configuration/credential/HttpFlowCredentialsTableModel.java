package com.github.httpflowlabs.httpflow.plugin.intellij.configuration.credential;

import javax.swing.table.DefaultTableModel;

public class HttpFlowCredentialsTableModel extends DefaultTableModel {

    public HttpFlowCredentialsTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
