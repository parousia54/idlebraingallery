package com.parousia.idlebrain.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

public class IdleBrainScreen extends FragmentActivity implements Updater {

	protected ProgressDialog progress;
	protected Handler handler;
	protected String dialogMessage = "Error", dialogTitile = "";
	private boolean isTaskDone = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		progress = new ProgressDialog(this);
		progress.setCancelable(false);

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		final Dialog dialog = new Dialog(this);
		switch (id) {
		case PROGRESS_DIALOG: {
			progress = new ProgressDialog(this);
			progress.setCancelable(false);
			progress.setMessage(dialogMessage);
			return progress;
		}

		}
		return dialog;
	}

	@Override
	public void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case PROGRESS_DIALOG: {
			System.out.println("Progress Dialog Msg : " + dialogMessage);
			((ProgressDialog) dialog).setMessage(dialogMessage);
			break;
		}
		}
	}

	@Override
	public void displayProgress(String message) {
		if (progress.isShowing())
			progress.cancel();
		dialogMessage = message;
		this.showDialog(PROGRESS_DIALOG);
	}

	@Override
	public void displayDialog(int id, String message, String title) {
		if (progress.isShowing())
			progress.dismiss();
		if (message != null)
			dialogMessage = message;
		if (title != null)
			dialogTitile = title;
		this.showDialog(id);
	}

	@Override
	public void taskDone(int mode, Object data) {
		if (progress.isShowing()) {
			progress.cancel();
		}
	}

	@Override
	public void setTaskDone(boolean status) {
		isTaskDone = status;

	}

	@Override
	public Handler getHandler() {
		return handler;
	}
}