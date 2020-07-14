package recreate.india.vsm.Dialog_Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import recreate.india.vsm.R;

public class Buy_success extends DialogFragment {
    TextView successshares;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_buy_success,null,false);
        successshares=view.findViewById(R.id.successshares);
        builder.setView(view);
        Bundle bundle = getArguments();
        successshares.setText("$ "+bundle.getInt("shares")+"");
        return builder.create();
    }
}
