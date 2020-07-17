package recreate.india.vsm.Main_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import recreate.india.vsm.R;

public class EarnMoneyFragment extends Fragment implements RewardedVideoAdListener {

    private RewardedVideoAd mad;
    private Button startAdd;
    private TextView score;
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
        MobileAds.initialize(getContext(),"ca-app-pub-8657920857471834~5603416632");
        startAdd=view.findViewById(R.id.startadd);

        score=view.findViewById(R.id.score);
        startAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mad.isLoaded()){
                    mad.show();
                }
            }
        });
        mad=MobileAds.getRewardedVideoAdInstance(getContext());
        loadRewardedVideoAdd();
        mad.setRewardedVideoAdListener(this);
        // Inflate the layout for this fragment
        return view;
    }
    private void loadRewardedVideoAdd(){
        if(!mad.isLoaded()){
            mad.loadAd("ca-app-pub-8657920857471834/7276532909",new AdRequest.Builder().build());
        }
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
        loadRewardedVideoAdd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        score.setText("20");
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Toast.makeText(getContext(),"no",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}