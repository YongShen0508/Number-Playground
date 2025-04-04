package com.game.app.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.game.app.R;
import com.game.app.gui.GameDashboard;
import com.game.app.gui.GameSelection;
import com.game.app.gui.MathsMaster;
import com.game.app.gui.NumberBuilder;
import com.game.app.gui.NumberClash;

import java.util.function.Consumer;

public class MessageDialog {
    private static Dialog dialog;

    public static void creatLeaveGameDialog(Context context, String title, String message, Consumer<Boolean> callback) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.activity_exit_dialog, null);
        // Initialize views
        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitleText);
        TextView dialogInfo = dialogView.findViewById(R.id.dialogInfoText);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnExit = dialogView.findViewById(R.id.btnExit);

        dialogTitle.setText(title);
        dialogInfo.setText(message);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Handle Exit Button
        btnExit.setOnClickListener(v -> {
            alertDialog.dismiss();
            if (callback != null) callback.accept(true); // Notify Exit was clicked
        });

        // Handle Cancel Button
        btnCancel.setOnClickListener(v -> {
            alertDialog.dismiss();
            if (callback != null) callback.accept(false); // Notify Cancel was clicked
        });
    }

    public static void createContinueGameDialog(Context context, String title, String message,Consumer<Boolean> callback) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.activity_display_answer_dialog, null);

        TextView dialogTitle = dialogView.findViewById(R.id.textAnswerTitle);
        TextView dialogInfo = dialogView.findViewById(R.id.textAnswerInfo);
        Button btnContinue = dialogView.findViewById(R.id.continueButton);

        dialogTitle.setText(title);
        dialogInfo.setText(message);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        builder.setCancelable(false);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Button Listener
        btnContinue.setOnClickListener(v -> {
            alertDialog.dismiss();
            if (callback != null) callback.accept(true);
        });
    }

}
