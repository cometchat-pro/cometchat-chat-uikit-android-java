package com.cometchatworkspace.resources.utils.custom_dialog;

import androidx.appcompat.app.AlertDialog;

public interface OnDialogButtonClickListener {
	void onButtonClick(AlertDialog alertDialog, int which, int popupId);
}