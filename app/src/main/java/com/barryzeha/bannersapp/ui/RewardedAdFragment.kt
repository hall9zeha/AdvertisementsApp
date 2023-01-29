package com.barryzeha.bannersapp.ui

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.barryzeha.bannersapp.R
import com.barryzeha.bannersapp.common.Constants
import com.barryzeha.bannersapp.databinding.FragmentNativeAdBinding
import com.barryzeha.bannersapp.databinding.FragmentRewardedAdBinding
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class RewardedAdFragment : Fragment() {

    private var _bind: FragmentRewardedAdBinding?=null
    private val bind get() =_bind!!
    private var mRewardedAd: RewardedAd? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bind= FragmentRewardedAdBinding.inflate(inflater,container,false)
        _bind?.let{
            return  it.root
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRewardedAd()
        setUpListenerAd()

        bind.btnReward.setOnClickListener {
            //para lanzar el anuncio más de una vez
            if(mRewardedAd != null) {
                watchAdvertisement()
            }else{
                setUpRewardedAd()

                Handler(Looper.getMainLooper()).postDelayed({
                watchAdvertisement() },2000)
            }

         }
    }
    private fun setUpRewardedAd(){
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(requireActivity(),Constants.REWARDED_AD_ID, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("TAG", adError.toString())
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                mRewardedAd = rewardedAd
                val options = ServerSideVerificationOptions.Builder()
                    .setCustomData("RECOMPENSADO_POR_VER_EL_ANUNCIO")
                    .build()
                rewardedAd.setServerSideVerificationOptions(options)
            }
        })
    }
    private fun setUpListenerAd(){


        mRewardedAd?.fullScreenContentCallback = object:FullScreenContentCallback(){
            override fun onAdClicked() {
                super.onAdClicked()
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                mRewardedAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                mRewardedAd = null
            }

            override fun onAdImpression() {
                super.onAdImpression()
            }

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
            }
        }

    }
    private fun watchAdvertisement(){
        MaterialAlertDialogBuilder(requireActivity())
            .setMessage(getString(R.string.watchAd))
            .setPositiveButton("Aceptar"
            ) { dialog,_ ->
                showRewardedAd()
            }
            .setNegativeButton("cancelar"){dialog,_ -> dialog.dismiss()}
            .show()
    }
    private fun showRewardedAd(){
        if (mRewardedAd != null) {
            mRewardedAd?.show(requireActivity()) { rewardItem ->

                var rewardAmount = rewardItem.amount
                var rewardType = rewardItem.type
                Log.d("AMOUNT", "User earned the reward.$rewardAmount")
                val d = Log.d("TYPE", "User earned the reward.$rewardType")
                bind.tvMessage.text = getString(R.string.rewardMessage)
                mRewardedAd = null

            }

        } else {
            Log.d("TAG", "The rewarded ad wasn't ready yet.")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _bind=null
    }


}