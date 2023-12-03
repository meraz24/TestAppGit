package com.vsple.githubtestappmeraz.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vsple.githubtestappmeraz.Models.Contributors.ContributorsModelItem
import com.vsple.githubtestappmeraz.R

class ContributorsAdaptor(
    private var context: Context, private var contributorsDataViewList: List<ContributorsModelItem>?
) : RecyclerView.Adapter<ContributorsAdaptor.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contributors, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvName.text = contributorsDataViewList?.get(position)?.login
        var userImage = contributorsDataViewList?.get(position)?.avatar_url
        Glide.with(context).asBitmap().load(
            userImage
        ).apply(
            RequestOptions.circleCropTransform()
        ).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.user_image_placeholder).into(holder.imageContributors)
    }

    override fun getItemCount(): Int {

        return contributorsDataViewList?.size!!
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName = view.findViewById<TextView>(R.id.tvName)
        var imageContributors = view.findViewById<ImageView>(R.id.imageContributors)
    }
}