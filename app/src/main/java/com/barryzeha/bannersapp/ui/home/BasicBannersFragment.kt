package com.barryzeha.bannersapp.ui.home

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.barryzeha.bannersapp.databinding.FragmentBasicBannersBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView


class BasicBannersFragment : Fragment() {

    private var _binding: FragmentBasicBannersBinding? = null
    private lateinit var adaptiveBanner:AdView
    private val  bannerAdSize: AdSize
    get(){
        val display = requireActivity().resources.configuration.screenWidthDp
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(requireContext(),display)
       /* val display = requireActivity().windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val density = outMetrics.density

        var adWidthPixels = binding.adViewContainer.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }

        val adWidth = (adWidthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(requireContext(), adWidth)*/
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentBasicBannersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initBanners()
        return root
    }
    private fun initBanners(){
        val basicBanner = binding.adView
        val basicBannerRequest=AdRequest.Builder().build()
        basicBanner.loadAd(basicBannerRequest)

        //El banner inteligente está descontinuado ahora debe usarse el adaptativo
        val smartBanner = binding.adViewSmart
        val smartBannerReq = AdRequest.Builder().build()
        smartBanner.loadAd(smartBannerReq)


        adaptiveBanner = AdView(requireActivity())
        binding.adViewContainer.addView(adaptiveBanner)
        adaptiveBanner.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        adaptiveBanner.setAdSize(bannerAdSize)
        val adaptiveBannerReq = AdRequest
            .Builder()
            .build()
        adaptiveBanner.loadAd(adaptiveBannerReq)



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}