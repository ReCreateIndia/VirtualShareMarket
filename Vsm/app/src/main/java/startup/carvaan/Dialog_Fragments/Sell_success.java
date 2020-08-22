package startup.carvaan.Dialog_Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

import startup.carvaan.Main_Activities.R;

public class Sell_success extends DialogFragment {
    private TextView sellsuccess;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sell_success,null,false);
        builder.setView(view);
        sellsuccess=view.findViewById(R.id.sellsuccess);
        Bundle bundle = getArguments();
        sellsuccess.setText("$ "+bundle.getInt("shares")+"");
        return builder.create();
    }
}
