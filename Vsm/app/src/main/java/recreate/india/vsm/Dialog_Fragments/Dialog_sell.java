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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import recreate.india.vsm.Constructor.Sharequantity;
import recreate.india.vsm.Constructor.credits;
import recreate.india.vsm.Constructor.sharedetails;
import recreate.india.vsm.Main_Activities.R;

public class Dialog_sell extends DialogFragment {
    private FirebaseFirestore ff;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Button btn_sell;
    private TextView sharePrice,totalAmount;
    private EditText noofshares;
    private TextView shareprice;
    private double priceofshare;
    Sharequantity sharequantity=new Sharequantity();
    credits credits=new credits();
    sharedetails sharedetails=new sharedetails();
    String s;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_sell_shares,null,false);
        Bundle bundle= getArguments();
        noofshares=view.findViewById(R.id.noofshares);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        ff=FirebaseFirestore.getInstance();
         s= bundle.getString("shareid");


        ff.collection("Users")
                .document(firebaseUser.getUid()).collection("Credits")
                .document("Credits").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                DocumentSnapshot snapshot=documentSnapshot;
                credits.setCredits(snapshot.getString("credits"));
            }
        });


        ff.collection("Shares").document(s)
                .collection("ShareDetails").document("price").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                DocumentSnapshot snapshot=documentSnapshot;
                sharedetails.setSellingprice(snapshot.getString("sellingprice"));
                shareprice=view.findViewById(R.id.price_of_shares);
                shareprice.setText("$"+sharedetails.getSellingprice());
            }
        });


        ff.collection("Shares").document(s)
                .collection("ShareDetails").document("quantity").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                DocumentSnapshot snapshot=documentSnapshot;
                sharequantity.setAvailable(documentSnapshot.getString("available"));
                sharequantity.setTotal(documentSnapshot.getString("total"));
            }
        });


        builder.setView(view);
        btn_sell = view.findViewById(R.id.btn_sell);
        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String new_available;
                new_available=String.valueOf(Integer.parseInt(noofshares.getText().toString())+Integer.parseInt(sharequantity.getAvailable()));
                Map<String,Object>mapp=new HashMap<>();
                mapp.put("available",new_available);
                mapp.put("total",sharequantity.getTotal());
                ff.collection("Shares").document(s)
                        .collection("ShareDetails").document("quantity").set(mapp);
                priceofshare = Double.parseDouble(sharedetails.getSellingprice());
                String new_credits;
                new_credits=String.valueOf(Double.parseDouble(credits.getCredits())+(priceofshare*Double.parseDouble(noofshares.getText().toString())));
                Map<String, Object> map = new HashMap<>();
                map.put("credits", new_credits);
                ff.collection("Users")
                        .document(firebaseUser.getUid())
                        .collection("Credits")
                        .document("Credits").set(map);
                Sell_success sell_success = new Sell_success();
                sell_success.show(getChildFragmentManager(),"Sell_Success");
            }
        });
        return builder.create();
    }

}
