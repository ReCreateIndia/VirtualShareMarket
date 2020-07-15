package recreate.india.vsm.Dialog_Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import recreate.india.vsm.R;

public class Dialog_sell extends DialogFragment {

    private Button btn_sell;
    private TextView sharePrice,totalAmount;
    private EditText noofshares;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sell_shares,null,false);
        sharePrice=view.findViewById(R.id.price_of_shares);
        totalAmount=view.findViewById(R.id.totalAmount);
        noofshares=view.findViewById(R.id.noofshares);

        builder.setView(view);
        btn_sell = view.findViewById(R.id.btn_sell);
        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sell_success sell_success = new Sell_success();
                sell_success.show(getChildFragmentManager(),"Sell_Success");
                dismiss();
            }
        });
        return builder.create();
    }

}
