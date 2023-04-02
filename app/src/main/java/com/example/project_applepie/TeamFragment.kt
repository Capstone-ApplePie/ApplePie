package com.example.project_applepie

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_applepie.databinding.FragmentTeamBinding
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.recuit
import com.example.project_applepie.recyclerview.SearchItemRecyclerViewAdapter
import com.example.project_applepie.recyclerview.SearchTeamRecyclerViewAdapter
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TeamFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TeamFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var searchAdapter : SearchTeamRecyclerViewAdapter
    private var _teamBinding : FragmentTeamBinding? = null
    private val teamBinding get() = _teamBinding!!

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
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다")
        )
        searchAdapter = SearchTeamRecyclerViewAdapter()
        searchAdapter.submitList(itemList)
        teamBinding.rvTeam.layoutManager = LinearLayoutManager(view.context,
            LinearLayoutManager.VERTICAL,false)
        teamBinding.rvTeam.adapter = searchAdapter

        teamBinding.tabLayoutRecruit.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 ->{
                        bindAdpater(itemList,view.context)
                    }
                    1 ->{
                        bindAdpater(itemList,view.context)
                    }
                    2 ->{
                        bindAdpater(itemList,view.context)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }
    fun bindAdpater(list : ArrayList<AuerProfile>, context : Context){
        searchAdapter = SearchTeamRecyclerViewAdapter()
        searchAdapter.submitList(list)
        teamBinding.rvTeam.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        teamBinding.rvTeam.adapter = searchAdapter
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _teamBinding = FragmentTeamBinding.inflate(inflater,container,false)
        val view = teamBinding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _teamBinding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TeamFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}