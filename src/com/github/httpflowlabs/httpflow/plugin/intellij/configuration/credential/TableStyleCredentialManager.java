package com.github.httpflowlabs.httpflow.plugin.intellij.configuration.credential;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.CredentialAttributesKt;
import com.intellij.credentialStore.Credentials;
import com.intellij.ide.passwordSafe.PasswordSafe;

import java.util.ArrayList;
import java.util.List;

public class TableStyleCredentialManager {

    public static final String KEY_COUNT_NAME = "keyCount";
    public static final String KEY_NAME_PREFIX = "no";

    public Credentials getKeyCountCredentials() {
        return getCredentials(KEY_COUNT_NAME);
    }

    public Credentials getCredentialsByRowIndex(int rowIndex) {
        return getCredentials(KEY_NAME_PREFIX + rowIndex);
    }

    private static Credentials getCredentials(String keyName) {
        CredentialAttributes key = new CredentialAttributes(
                CredentialAttributesKt.generateServiceName("Httpflow", keyName)
        );
        return PasswordSafe.getInstance().get(key);
    }

    public void setKeyCountCredentials(int rowCount) {
        setCredentials(KEY_COUNT_NAME, KEY_COUNT_NAME, String.valueOf(rowCount));
    }

    public void setCredentialsByRowIndex(int rowIndex, String name, String value) {
        setCredentials(KEY_NAME_PREFIX + rowIndex, name, value);
    }

    private static void setCredentials(String keyName, String name, String value) {
        Credentials credentials = new Credentials(name, value);
        CredentialAttributes key = new CredentialAttributes(
                CredentialAttributesKt.generateServiceName("Httpflow", keyName)
        );
        PasswordSafe.getInstance().set(key, credentials);
    }

    public List<String[]> loadAllCredentials(boolean needMask) {
        List<String[]> list = new ArrayList<>();
        Credentials keyCountCred = getKeyCountCredentials();
        if (keyCountCred != null) {
            int count = Integer.parseInt(keyCountCred.getPasswordAsString());

            for (int i = 0; i < count; i++) {
                Credentials credentials = getCredentialsByRowIndex(i);
                String password = credentials.getPasswordAsString();
                list.add(new String[]{credentials.getUserName(), needMask ? password.replaceAll(".", "*") : password});
            }
        }
        return list;
    }

}
