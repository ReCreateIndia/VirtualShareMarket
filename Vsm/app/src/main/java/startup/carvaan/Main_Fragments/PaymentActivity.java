package startup.carvaan.Main_Fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gocashfree.cashfreesdk.CFPaymentService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import startup.carvaan.Constructor.CashFreeToken;
import startup.carvaan.Main_Activities.MainActivity;
import startup.carvaan.Main_Activities.R;
import startup.carvaan.remote.ICloudFunctions;
import startup.carvaan.remote.RetrofitClient;

import static android.content.ContentValues.TAG;

public class PaymentActivity extends AppCompatActivity {
    private Button checkout;
    CompositeDisposable compostiteDisposable=new CompositeDisposable();
    ICloudFunctions iCloudFunctions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        iCloudFunctions= RetrofitClient.getInstance().create(ICloudFunctions.class);
        checkout=findViewById(R.id.payment);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePaymentRequest();
            }
        });
    }
    @Override
    protected void  onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Same request code for all payment APIs.
        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d(TAG, "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle  bundle = data.getExtras();
            if (bundle != null)
                for (String  key  :  bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d(TAG, key + " : " + bundle.getString(key));
                        Toast.makeText(PaymentActivity.this,key+":"+bundle.getString(key),Toast.LENGTH_LONG).show();
                    }
                }
        }
    }
    private void makePaymentRequest() {
        String orderId= UUID.randomUUID().toString();
        final Map<String,String> payment=new HashMap<>();
        payment.put("appId","3008091222ee68f67fbc5fd1c08003");
        payment.put("orderId",orderId);
        payment.put("orderAmount","10");
        payment.put("orderCurrency","INR");
        payment.put("customerPhone","+918171365728");
        payment.put("customerEmail","test@gmail.com");
        compostiteDisposable.add(iCloudFunctions.getToken(orderId,"10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                        subscribe(new Consumer<CashFreeToken>() {
                                      @Override
                                      public void accept(CashFreeToken cashFreeToken) throws Exception {
                                          if(cashFreeToken.getStatus().equals("OK")){
                                              CFPaymentService.getCFPaymentServiceInstance().doPayment(PaymentActivity.this,payment,cashFreeToken.getCftoken(),"TEST");
                                          }
                                      }
                                  }, new Consumer<Throwable>() {
                                      @Override
                                      public void accept(Throwable throwable) throws Exception {
                                            Toast.makeText(PaymentActivity.this,""+throwable.getMessage(),Toast.LENGTH_LONG).show();
                                      }
                                  }
                        ));
    }

    @Override
    protected void onStop() {
        compostiteDisposable.clear();
        super.onStop();
    }
}