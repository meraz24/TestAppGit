package com.vsple.githubtestappmeraz.Adapter
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.vsple.githubtestappmeraz.Models.SearchRepoModel.Item
import com.vsple.githubtestappmeraz.Models.SearchRepoModel.SearchDataModel
import com.vsple.githubtestappmeraz.R

class RepoListAdapter(
    private var context: Context, private var searchDataViewList: List<Item>?
) : RecyclerView.Adapter<RepoListAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycleview, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.imageViewDetails.setOnClickListener {
            onClickListener!!.llButtonViewDetails(position)
        }
        holder.tvTitle.text = searchDataViewList?.get(position)?.name
        holder.tvId.text = searchDataViewList?.get(position)?.id.toString()
        holder.tvName.text = searchDataViewList?.get(position)?.owner?.login
        var userImage = searchDataViewList?.get(position)?.owner?.avatar_url
        Glide.with(context).asBitmap().load(
            userImage
        ).apply(
            RequestOptions.circleCropTransform()
        ).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.user_image_placeholder).into(holder.imageUser)
    }

    override fun getItemCount(): Int {

        return searchDataViewList?.size!!
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle = view.findViewById<TextView>(R.id.tvSearchName)
        var tvName = view.findViewById<TextView>(R.id.tvName)
        var tvId = view.findViewById<TextView>(R.id.tvId)
        var imageViewDetails = view.findViewById<ImageView>(R.id.imageViewDetails)
        var imageUser = view.findViewById<ImageView>(R.id.imageUser)
    }

    interface OnClickListener {
        fun llButtonViewDetails(position: Int)
    }

    fun setOnClickListener(OnClickListener: OnClickListener) {
        this.onClickListener = OnClickListener
    }
}