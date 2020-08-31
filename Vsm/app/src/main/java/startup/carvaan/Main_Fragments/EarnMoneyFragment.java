package startup.carvaan.Main_Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gocashfree.cashfreesdk.CFPaymentService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import startup.carvaan.Constructor.CashFreeToken;
import startup.carvaan.Main_Activities.MainActivity;
import startup.carvaan.Main_Activities.R;
import startup.carvaan.Recycler_View_Adapters.Ads_Adapter;
import startup.carvaan.remote.ICloudFunctions;
import startup.carvaan.remote.RetrofitClient;

import static android.content.ContentValues.TAG;

public class EarnMoneyFragment extends Fragment implements RewardedVideoAdListener {

    private RewardedVideoAd rewardedVideoAd;
    private Button showadd;
    private RecyclerView ad_recycler_view;
    private Button payment;

    public EarnMoneyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_earn_money, container, false);
        MobileAds.initialize(getContext(),"ca-app-pub-1372656325166770~1018059934");
        rewardedVideoAd=MobileAds.getRewardedVideoAdInstance(getContext());
        rewardedVideoAd.setRewardedVideoAdListener(this);

        payment=view.findViewById(R.id.payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),PaymentActivity.class));
            }
        });
        loadAds();
//        showadd=view.findViewById(R.id.startadd);
//        showadd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(rewardedVideoAd.isLoaded()){
//                    rewardedVideoAd.show();
//                }
//            }
//        });
        ad_recycler_view = view.findViewById(R.id.ads_recycler_view);
        final Ads_Adapter ads_adapter = new Ads_Adapter();
        ad_recycler_view.setAdapter(ads_adapter);
        ad_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                switch (direction)
                {
                    case ItemTouchHelper.RIGHT:
                        if(position==0) {
                            rewardedVideoAd.show();
                            loadAds();
                        }
                        else if(position==1){
                            rewardedVideoAd.show();
                            loadAds();
                        }
                        else if(position==2)
                        {
                            rewardedVideoAd.show();
                            loadAds();
                        }
                        break;
                }
                ads_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(getResources().getColor(R.color.bottomnav))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_play_arrow_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX/2, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(ad_recycler_view);



        return view;
    }

    private void loadAds() {
        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());
    }


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Toast.makeText(getContext(),"add did not load ",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoCompleted() {

    }


}