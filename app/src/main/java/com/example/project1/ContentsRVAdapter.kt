package com.example.project1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.QueryDocumentSnapshot


class ContentsRVAdapter(val items: MutableList<ContentModel>) : RecyclerView.Adapter<ContentsRVAdapter.ViewHolder>() {



    interface ItemClick{  //아이템 클릭 이벤트 정의
        fun onClick(view: View, position: Int)
    }

    var itemClick : ItemClick? = null

    //새로운 ViewHolder 객체를 생성
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentsRVAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_item_rv,parent,false)
        return ViewHolder(v)
    }
     //RecyclerView에서 각 아이템에 대한 데이터를 뷰에 바인딩
    override fun onBindViewHolder(holder: ContentsRVAdapter.ViewHolder, position: Int) {
        if(itemClick!=null){
            holder.itemView.setOnClickListener{v->
                itemClick?.onClick(v,position)
            }
        }
        holder.bindItem(items[position])



    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindItem(item: ContentModel){
//            val contentTitle = itemView.findViewById<TextView>(R.id.item_explain_tv)
//            contentTitle.text = item.title

            val contentTitle =itemView.findViewById<TextView>(R.id.listTitle)
            val listPrice = itemView.findViewById<TextView>(R.id.listPrice)
            val listContent = itemView.findViewById<TextView>(R.id.listContent)
            val listStatus = itemView.findViewById<TextView>(R.id.listStatus)

//            val listImage = itemView.findViewById<ImageView>(R.id.listImage)

            contentTitle.text= item.listTitle
            listPrice.text = item.listPrice.toString()
            listContent.text  = item.listContents
            listStatus.text=item.forSale


        }
    }
}



