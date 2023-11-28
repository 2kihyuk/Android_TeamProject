package com.example.project1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatRVAdapter(val chatitem:MutableList<ChatModel>) : RecyclerView.Adapter<ChatRVAdapter.ViewHolder>() {

    interface ItemClick{  //아이템 클릭 이벤트 정의
        fun onClick(view: View, position: Int)
    }

    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRVAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item_rv, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ChatRVAdapter.ViewHolder, position: Int) {
        // ViewHolder에 데이터를 바인딩하는 로직을 작성해주세요.
        if(itemClick!=null){
            holder.itemView.setOnClickListener{v->
                itemClick?.onClick(v,position)
            }
        }
        holder.bindItem(chatitem[position])

    }

    override fun getItemCount(): Int {
        // 데이터 아이템의 개수를 반환하는 로직을 작성해주세요.
        return chatitem.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ViewHolder 내에서 사용할 UI 요소들을 선언해주세요.
        // 예: val textView: TextView = itemView.findViewById(R.id.textView)

        fun bindItem(item: ChatModel){
            val senderid = itemView.findViewById<TextView>(R.id.senderId)
            val getmessagecontent = itemView.findViewById<TextView>(R.id.GetMessageContent)
            val what = itemView.findViewById<TextView>(R.id.what)

            what.text = item.what
            senderid.text = "보낸 사람 : " + item.senderId
            getmessagecontent.text= item.ChatContent


//            val listImage = itemView.findViewById<ImageView>(R.id.listImage)



        }
    }


}
