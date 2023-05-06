package com.example.project_applepie

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_applepie.databinding.FragmentChatBinding
import com.example.project_applepie.model.chating
import com.example.project_applepie.recyclerview.homeRecycle.FindItemRecyclerViewAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var findAdapter : FindItemRecyclerViewAdapter
    private var _chattingBinding : FragmentChatBinding? = null
    private val chatBinding get() = _chattingBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val UserImg = R.drawable.user

        val finditemList = arrayListOf(
            chating(UserImg, "보낸 사람", "피카츄 라이츄 파이리 꼬부기 버터풀 야도란 피존투 또가스"),
            chating(UserImg, "보낸 사람", "서로 생긴 모습은 달라도 우리는 모두 친구 (맞아)"),
            chating(UserImg, "보낸 사람", "산에서 들에서 때리고 뒹굴고 사막에서 정글에서 웃다가 울다가"),
            chating(UserImg, "보낸 사람", "서로 만나기까지 힘들었어도 우리는 모두 친구(피카피카)")
        )

        findAdapter = FindItemRecyclerViewAdapter()
        findAdapter.submitFindList(finditemList)
        chatBinding.rvChat.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        chatBinding.rvChat.adapter = findAdapter

        bindAdpater(finditemList,view.context)
    }

    fun bindAdpater(list : ArrayList<chating>, context : Context){
        findAdapter = FindItemRecyclerViewAdapter()
        findAdapter.submitFindList(list)
        chatBinding.rvChat.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        chatBinding.rvChat.adapter = findAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _chattingBinding = FragmentChatBinding.inflate(inflater, container, false)
        val view = chatBinding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _chattingBinding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}