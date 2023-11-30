package com.example.project1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.project1.ChatModel
import com.example.project1.R
import com.example.project1.databinding.FragmentHomeBinding
import com.example.project1.databinding.FragmentSettingsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    private val db: FirebaseFirestore = Firebase.firestore
    private val UserCollectionRef = db.collection("user") // Message는 Collection ID
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_settings,container,false)




        //여기서 이제 데이터베이스에서 받은 메시지의 정보를 가지고와서 리싸이클러뷰에 띄우기.
        UserCollectionRef.get().addOnSuccessListener { querySnapshot ->
            for (doc in querySnapshot) {
                val userUidCheck = doc.getString("uid")
                val userName = doc.getString("name")
                val userBirth = doc.getString("birthday")

                if(userUidCheck.toString()==auth.currentUser?.uid.toString()){
                    binding.userEmail.text= auth.currentUser?.email.toString()
                    binding.userName.text = userName
                    binding.userBirthday.text = userBirth
                }

            }

        }.addOnFailureListener{
                exception->
            Log.w("Firestore", "Error getting documents: ", exception)
        }

        binding.hometab.setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_settingsFragment_to_homeFragment)

        }
        binding.chattab.setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.action_settingsFragment_to_chatFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
        }


    }
