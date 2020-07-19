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
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import recreate.india.vsm.Main_Activities.R;


public class EarnMoneyFragment extends Fragment implements RewardedVideoAdListener {

    private RewardedVideoAd rewardedVideoAd;
    private Button showadd;

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
        MobileAds.initialize(getContext(),"ca-app-pub-5536388301931451~5642954827");
        rewardedVideoAd=MobileAds.getRewardedVideoAdInstance(getContext());
        rewardedVideoAd.setRewardedVideoAdListener(this);
        laodAds();
        showadd=view.findViewById(R.id.startadd);
        showadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rewardedVideoAd.isLoaded()){
                    rewardedVideoAd.show();
                }
            }
        });
        return view;
    }

    private void laodAds() {
        rewardedVideoAd.loadAd("ca-app-pub-5536388301931451/8077546472",new AdRequest.Builder().build());
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