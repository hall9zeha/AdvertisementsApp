package com.barryzeha.bannersapp.ui.gallery


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.barryzeha.bannersapp.R
import com.barryzeha.bannersapp.databinding.FragmentIntersticialAdBinding


import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialAdFragment : Fragment() {

    private var _binding: FragmentIntersticialAdBinding? = null
    private val ID_AD_BLOCK_DEBUG="ca-app-pub-3940256099942544/1033173712"
    private var myInterstitialBanner:InterstitialAd? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentIntersticialAdBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initInterstitialBanner()
        showBanner()
        binding.btnShow.setOnClickListener {
            initInterstitialBanner()
            showBanner()
        }
        return root
    }

    private fun initInterstitialBanner(){
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireActivity(), ID_AD_BLOCK_DEBUG, adRequest,object:InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(error: LoadAdError) {
                Log.e("ERROR_AD", error.message.toString() )
                myInterstitialBanner=null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                myInterstitialBanner=interstitialAd
            }
        })

        myInterstitialBanner?.fullScreenContentCallback = object: FullScreenContentCallback(){
            override fun onAdClicked() {
                super.onAdClicked()
                Log.d("TAG", "Ad was clicked.")
            }
            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d("TAG", "Ad dismissed fullscreen content.")
                myInterstitialBanner = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                Log.e("TAG", "Ad failed to show fullscreen content.")
                myInterstitialBanner = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d("TAG", "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d("TAG", "Ad showed fullscreen content.")
            }
        }


    }
    private fun showBannerWithButton(){
        myInterstitialBanner?.show(requireActivity())?:run{
            Log.d("INSTERTICIAL_BANNER", "El banner ya está ejecutándose")
        }
    }
    private fun showBanner(){
        //No se puede mostrar el banner inmediatamente así que debemos retrasarlo en este caso
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            binding.pbBanner.visibility=View.GONE
            binding.btnShow.visibility=View.VISIBLE
            binding.tvInterstitialBanner.text = getString(R.string.completingExec)
            myInterstitialBanner?.show(requireActivity())?:run{
                Log.d("INSTERTICIAL_BANNER", "El banner ya está ejecutándose")
             } },3000)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}