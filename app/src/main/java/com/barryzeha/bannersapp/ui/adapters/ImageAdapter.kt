package com.barryzeha.bannersapp.ui.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.barryzeha.bannersapp.R
import com.barryzeha.bannersapp.common.Constants
import com.barryzeha.bannersapp.databinding.ContainerNativeAdViewBinding
import com.barryzeha.bannersapp.databinding.ImageItemBinding
import com.barryzeha.bannersapp.databinding.NativeAdItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView


/****
 * Project BannersApp
 * Created by Barry Zea H. on 26/01/2023
 * Copyright (c)  All rights reserved.
 ***/

class ImageAdapter(val activity:Activity):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val CONTENT_TYPE=1
    private val AD_TYPE=2
    private var imagesList:MutableList<String> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
         return when(viewType) {
             CONTENT_TYPE -> ViewHolder(
                 LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
             )
             AD_TYPE -> AdViewHolder(
                 //podemos pasarle directamente el layout del NativeAdView
                 //LayoutInflater.from(context).inflate(R.layout.native_ad_item, parent, false)
                 //o un contenedor customizado como en nuestro caso
                 LayoutInflater.from(context).inflate(R.layout.container_native_ad_view, parent, false)
             )
             else -> ViewHolder(
                 LayoutInflater.from(context).inflate(R.layout.image_item, parent, false)
             )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position)==CONTENT_TYPE){
        (holder as ViewHolder).bind(imagesList[position])
        }else{
            (holder as AdViewHolder).bind()
        }

    }

    override fun getItemViewType(position: Int): Int {
        if(imagesList[position] == ""){
            return AD_TYPE
        }
        return CONTENT_TYPE
    }

    override fun getItemCount() = if(imagesList.size>0) imagesList.size else 0

    fun add(imagesUrl:List<String>){
        var countItem=0
        Log.e("TAG",imagesUrl.toString() )
        imagesUrl.forEach {
            if(!imagesList.contains(it)) {
                countItem++
                //dividimos en tres la lista obtenida para poner un anuncio después de cada tercera parte de elementos
                val middle = imagesUrl.size / 3
                if(countItem % middle==0){
                    //por cada tercera parte de  elementos traidos cargamos un elemento vacío para llenar el anuncio
                    imagesList.add("")
                }
                imagesList.add(it)
                notifyItemInserted(imagesList.size - 1)
            }

        }
    }
    fun clear(){
        if(imagesList.isNotEmpty()){
            imagesList.clear()
            notifyItemRangeRemoved(0,imagesList.size)
        }
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val binding = ImageItemBinding.bind(itemView)
        private val set = ConstraintSet()
        fun bind(imageUrl:String)=with(binding){

           Glide.with(activity)
               .load(imageUrl)
               .centerCrop()
               .placeholder(R.drawable.ic_launcher_background)
               .diskCacheStrategy(DiskCacheStrategy.ALL)
               .fitCenter()
               .into(ivImage)

        }



    }
    inner class AdViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        private val contentAdBinding = ContainerNativeAdViewBinding.bind(itemView)

        @SuppressLint("InflateParams")
        //poner parent en null para castear el layout y ponerlo dentro de nuestro cardview
        private val adViewLayout = activity.layoutInflater.inflate(R.layout.native_ad_item,null) as NativeAdView
        fun bind(){
            val adBuilder = AdLoader.Builder(activity,Constants.NATIVE_AD_ID)
                .forNativeAd {nativeAd->
                   displayCardNativeAd(adViewLayout,nativeAd)
                }
                .build()

            adBuilder.loadAd(AdRequest.Builder().build())

        }

        private fun displayCardNativeAd(adView:NativeAdView, ad:NativeAd) = with(contentAdBinding){

            val bindAdView= NativeAdItemBinding.bind(adView)

            adView.setNativeAd(ad)
            bindAdView.apply {
                adHeadline.text=ad.headline
                adPpIcon.setImageDrawable(ad.icon?.drawable)
                adTitle.text=ad.advertiser
                adView.callToActionView = adButton
                adButton.text = ad.store
                adDescription.text=ad.body
                adPrice.text=ad.price
                adView.bodyView = adDescription
                ad.mediaContent?.let { adMedia.setMediaContent(it) }
            }
            //importante limpiar las vistas previas dentro del contenedor de nuestro adView
            ctlContainerMain.removeAllViews()
            ctlContainerMain.addView(adView)

        }
    }
}