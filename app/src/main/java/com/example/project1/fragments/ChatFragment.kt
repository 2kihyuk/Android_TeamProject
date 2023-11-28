package com.example.project1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.ChatModel
import com.example.project1.ChatRVAdapter
import com.example.project1.ContentModel
import com.example.project1.R
import com.example.project1.databinding.FragmentChatBinding
import com.example.project1.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class ChatFragment : Fragment() {
    lateinit var binding: FragmentChatBinding
    private val db: FirebaseFirestore = Firebase.firestore
    private val MessageCollectionRef = db.collection("Message") // Message는 Collection ID
    private lateinit var auth: FirebaseAuth
    private val allItems = mutableListOf<ChatModel>() // 전체 상품 아이템 리스트


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_chat,container,false)
        val chatrv :RecyclerView = binding.chatrv

//        val chatitem = mutableListOf<ChatModel>()

        val chatRVAdapter = ChatRVAdapter(allItems)
        chatrv.adapter=chatRVAdapter
        chatrv.layoutManager=LinearLayoutManager(context)



        //여기서 이제 데이터베이스에서 받은 메시지의 정보를 가지고와서 리싸이클러뷰에 띄우기.
        MessageCollectionRef.get().addOnSuccessListener { querySnapshot ->
            for (doc in querySnapshot) {
                val receiverId = doc.getString("receiverId")
                val senderId = doc.getString("senderId")
                val content = doc.getString("content")
                val what = doc.getString("what")

                if(auth.currentUser?.uid.toString()==receiverId.toString()){
                    val chatmodel = ChatModel(senderId.toString(),content.toString(),what.toString())
                    allItems.add(chatmodel)
                }

            }
            chatRVAdapter.notifyDataSetChanged()
        }.addOnFailureListener{
                exception->
            Log.w("Firestore", "Error getting documents: ", exception)
        }




        binding.hometab.setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_chatFragment_to_homeFragment)

        }
        binding.settingstab.setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_chatFragment_to_settingsFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }


    }
