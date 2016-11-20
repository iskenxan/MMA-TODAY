package space.samatov.mmatoday.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class NoConnectionDialogFragment extends DialogFragment {

    public static final String FRAGMENT_KEY="no_connection_dialog";
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("There was an error connecting to internet.Check your connection and try again");
        builder.setPositiveButton("OK",null);

        return builder.create();
    }
}
