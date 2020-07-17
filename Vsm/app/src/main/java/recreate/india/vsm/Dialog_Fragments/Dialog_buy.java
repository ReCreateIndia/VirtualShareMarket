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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ThrowOnExtraProperties;

import java.util.HashMap;
import java.util.Map;

import recreate.india.vsm.Constructor.credits;
import recreate.india.vsm.Constructor.sharedetails;
import recreate.india.vsm.Main_Activities.R;



public class Dialog_buy extends DialogFragment {

    private Button btn_buy;
    private EditText noofshares;
    double netCredits;
    private TextView textView;
    private FirebaseFirestore ff;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private TextView shareprice;
    sharedetails sharedetails=new sharedetails();
    credits credits=new credits();
    int priceofshare;

    //Bundle bundle = getArguments();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_buy_shares,null,false);
        builder.setView(view);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        ff=FirebaseFirestore.getInstance();

        noofshares=view.findViewById(R.id.noofshares);

        //getting current credits of current user
        ff.collection("Users")
                .document(firebaseUser.getUid()).collection("Credits")
                .document("Credits").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot snapshot=task.getResult();
                credits.setCredits(snapshot.getString("credits"));
            }
        });

        textView=view.findViewById(R.id.totalAmount);
        //textView.setText("Total Amount:"+Integer.parseInt(sharedetails.getBuyingprice())*(Integer.parseInt(noofshares.getText().toString())));
        Bundle bundle= getArguments();
        String s= bundle.getString("shareid");
        ff.collection("Shares").document(s)
                .collection("ShareDetails").document("price").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot=task.getResult();
                sharedetails.setBuyingprice(snapshot.getString("buyingprice"));
                shareprice=view.findViewById(R.id.shareprice);
                shareprice.setText(sharedetails.getBuyingprice());
            }
        });


         // this is not working

        btn_buy = view.findViewById(R.id.btn_buy);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceofshare = Integer.parseInt(sharedetails.getBuyingprice());
                if (Integer.parseInt(noofshares.getText().toString()) == 0) {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_LONG).show();
                }
                else {
                    int a = Integer.parseInt(noofshares.getText().toString());
//
//                    //user having current credits less then needed
                    if ((double) (a * priceofshare) > Double.parseDouble(credits.getCredits())) {
                        Toast.makeText(getContext(), "needed" + a * priceofshare + "you have " + credits.getCredits(), Toast.LENGTH_LONG).show();
                    } else {

                        netCredits = Double.parseDouble(credits.getCredits()) - (a * priceofshare);

                        // putting new credts into map
                        Map<String, Object> map = new HashMap<>();
                        map.put("credits", String.valueOf(netCredits));

                        //pushing new credits of current user in firebase
                        ff.collection("Users")
                                .document(firebaseUser.getUid())
                                .collection("Credits")
                                .document("Credits")
                                .set(map);


                        //success dailog with no of shares has been bought
                        Buy_success buy_success = new Buy_success();
                        Bundle bundle1 = new Bundle();
                        buy_success.setArguments(bundle1);
                        bundle1.putInt("shares", a);
                        buy_success.show(getChildFragmentManager(), "Buy_Success");
                        //dismiss();
                        // }
                    }
                }
            }
        });

        return builder.create();
    }
}
