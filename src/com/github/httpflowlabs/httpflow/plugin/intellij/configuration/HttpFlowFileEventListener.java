package com.github.httpflowlabs.httpflow.plugin.intellij.configuration;

import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.events.VFileDeleteEvent;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HttpFlowFileEventListener {

    public static HttpFlowFileEventListener INSTANCE = new HttpFlowFileEventListener();

    private boolean isRegistered;

    public void register() {
        if (!isRegistered) {
            VirtualFileManager.getInstance().addAsyncFileListener((@NotNull List<? extends VFileEvent> events) -> {
                for (VFileEvent event : events) {
                    if (event instanceof VFileDeleteEvent) {
                        // TODO run configuration icon change
                        System.out.println("deleted : " + event.getFile());
                    }
                }
                return null;
            }, () -> {});

            isRegistered = true;
        }
    }

}
