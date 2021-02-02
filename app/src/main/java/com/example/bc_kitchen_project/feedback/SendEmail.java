package com.example.bc_kitchen_project.feedback;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.bc_kitchen_project.R;

public class SendEmail extends DialogFragment {

    private String selection;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] messageType = getActivity().getResources().getStringArray(R.array.messageType);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.chooseMessage);
        builder.setSingleChoiceItems(R.array.messageType, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selection = messageType[which];
            }
        });
        builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), selection + " message needs to be sent", Toast.LENGTH_SHORT).show();
                String[] TO = {"zane.kaibe@gmail.com"};
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, TO);
                email.putExtra(Intent.EXTRA_SUBJECT, selection + " on Pantry item app");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose app to send email"));

            }
        });
        builder.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();

        return dialog;
    }
}
