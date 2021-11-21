package com.github.httpflowlabs.httpflow.plugin.intellij.support;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class HttpFlowJenkinsUtils {

    public static void writeFile(File file, InputStream is) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            IOUtils.copy(is, fos);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

}
