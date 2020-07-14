package recreate.india.vsm.Dialog_Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ThrowOnExtraProperties;

import java.util.HashMap;
import java.util.Map;

import recreate.india.vsm.R;

public class Dialog_buy extends DialogFragment {
    public interface OnInputlistener{
        public void input(String s);
    }
    OnInputlistener my_oninputlistener;
    public void setMy_oninputlistener(OnInputlistener my_oninputlistener)
    {
        this.my_oninputlistener = my_oninputlistener;
    }
    private Button btn_buy;
    private EditText noofshares;
    double netCredits;
    private FirebaseFirestore ff;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_buy_shares,null,false);
        builder.setView(view);
        ff=FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        final Bundle bundle = getArguments();
        noofshares=view.findViewById(R.id.noofshares);
        my_oninputlistener.input(noofshares.getText().toString());
        TextView shareprice=view.findViewById(R.id.shareprice);
        if(!(bundle.getInt("price")==0)){
            shareprice.setText("$ "+bundle.getInt("price")+"");
        }
        else{
            shareprice.setText("0");
        }

        btn_buy = view.findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int a = Integer.parseInt(noofshares.getText().toString());
                if((double)(a*bundle.getInt("price"))>bundle.getDouble("credits")){
                    Toast.makeText(getContext(),"sorry you dont have enough credits"+bundle.getDouble("credits"),Toast.LENGTH_LONG).show();


                }
                else{
                    netCredits=bundle.getDouble("credits")-(double)(a*bundle.getInt("price"));
                    if(netCredits==0){
                        Toast.makeText(getContext(),"no", Toast.LENGTH_SHORT).show();
                    }
                    Map<String,Object>map=new HashMap<>();
                    map.put("credits",netCredits);
                    ff.collection("Users").document(firebaseUser.getUid()).collection("Credits").document("Credits").set(map);
                    Buy_success buy_success = new Buy_success();
                    Bundle bundle1=new Bundle();
                    buy_success.setArguments(bundle);
                    bundle.putInt("shares",a);
                    buy_success.show(getChildFragmentManager(),"Buy_Success");
                }

            }
        });
        return builder.create();
    }
}
