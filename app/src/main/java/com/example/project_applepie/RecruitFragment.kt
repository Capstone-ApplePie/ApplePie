package com.example.project_applepie

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.TableLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_applepie.databinding.FragmentRecruitBinding
import com.example.project_applepie.model.recuit
import com.example.project_applepie.recyclerview.SearchItemRecyclerViewAdapter
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RecruitFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var searchAdapter : SearchItemRecyclerViewAdapter
    private var _recruitBinding : FragmentRecruitBinding? = null
    private val recruitBinding get() = _recruitBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val basicImg = R.drawable.charmander

        val itemList = arrayListOf(
            recuit(basicImg,"이상해씨","이상해씨-이상해풀-이상해꽃"),
            recuit(basicImg,"파이리","파이리-리자드-리자몽"),
            recuit(basicImg,"꼬부기","꼬부기-어니부기-거북왕"),
            recuit(basicImg,"이상해씨","이상해씨-이상해풀-이상해꽃"),
            recuit(basicImg,"이상해씨","이상해씨-이상해풀-이상해꽃"),
            recuit(basicImg,"파이리","파이리-리자드-리자몽"),
            recuit(basicImg,"꼬부기","꼬부기-어니부기-거북왕"),
            recuit(basicImg,"이상해씨","이상해씨-이상해풀-이상해꽃")

        )

        val basicImg2 = R.drawable.bulbasaur
        val itemList2 = arrayListOf(
            recuit(basicImg2,"이상해씨","이상해씨-이상해풀-이상해꽃"),
            recuit(basicImg2,"파이리","파이리-리자드-리자몽"),
            recuit(basicImg2,"꼬부기","꼬부기-어니부기-거북왕"),
            recuit(basicImg2,"이상해씨","이상해씨-이상해풀-이상해꽃")
        )

//      3.31 공모전 추가
        val basicImg3 = R.drawable.turtle
        val itemList3 = arrayListOf(
            recuit(basicImg3,"이상해씨","이상해씨-이상해풀-이상해꽃"),
            recuit(basicImg3,"파이리","파이리-리자드-리자몽"),
            recuit(basicImg3,"꼬부기","꼬부기-어니부기-거북왕"),
            recuit(basicImg3,"이상해씨","이상해씨-이상해풀-이상해꽃")
        )

        searchAdapter = SearchItemRecyclerViewAdapter()
        searchAdapter.submitList(itemList)
        recruitBinding.rvRecruit.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
        recruitBinding.rvRecruit.adapter = searchAdapter

        recruitBinding.tabLayoutRecruit.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 ->{
                        bindAdpater(itemList,view.context)
                    }
                    1 ->{
                        bindAdpater(itemList2,view.context)
                    }
                    2 ->{
                        bindAdpater(itemList3,view.context)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        searchAdapter.setOnItemClickListener(object : SearchItemRecyclerViewAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: recuit, pos: Int) {
                val intent = Intent(context, RecruitTeamActivity::class.java)
                intent.putExtra("data",data);
                startActivity(intent)
            }
        })

        recruitBinding.extendedFab.setOnClickListener {
            val intent = Intent(context, CreateTeamActivity::class.java)
            startActivity(intent)
        }

    }
    fun bindAdpater(list : ArrayList<recuit>, context : Context){
        searchAdapter = SearchItemRecyclerViewAdapter()
        searchAdapter.submitList(list)
        searchAdapter.setOnItemClickListener(object : SearchItemRecyclerViewAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: recuit, pos: Int) {
                val intent = Intent(context, RecruitTeamActivity::class.java)
                intent.putExtra("data",data);
                startActivity(intent)
            }
        })
        recruitBinding.rvRecruit.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        recruitBinding.rvRecruit.adapter = searchAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _recruitBinding = FragmentRecruitBinding.inflate(inflater, container, false)
        val view = recruitBinding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _recruitBinding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecruitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecruitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}