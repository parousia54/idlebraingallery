package com.parousia.idlebrain.view;

import android.os.Handler;

public interface Updater {

    public static final int PROGRESS_DIALOG = 107;
    
    public void displayDialog(int id, String message, String title);

    public void displayProgress(String message);

    public void taskDone(int mode, Object data);

    public void setTaskDone(boolean status);

    public Handler getHandler();
}
