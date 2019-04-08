package com.example.soundboard;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class NameDialog extends DialogFragment
{
    public interface NoticeDialogListener
    {
        void onDialogPositiveClick(DialogFragment dialog, String name, String path);
    }

    NoticeDialogListener listener;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        final View promptView = inflater.inflate(R.layout.name_dialog, null);

        promptView.findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final EditText newName = promptView.findViewById(R.id.editText);
                if (newName.getText().toString().isEmpty())
                    ((TextView) promptView.findViewById(R.id.error)).setText(R.string.set_name_error);
                else
                    listener.onDialogPositiveClick(NameDialog.this, newName.getText().toString(), getArguments().getString("path"));
            }
        });

        promptView.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NameDialog.this.getDialog().cancel();
            }
        });

        builder.setView(promptView)
                .setCancelable(true);

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException("MainActivity must implement NoticeDialogListener");
        }
    }
}
