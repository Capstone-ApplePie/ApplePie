package com.example.project_applepie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_applepie.databinding.FragmentTeamBinding
import com.example.project_applepie.model.AuerProfile
import com.example.project_applepie.model.dao.searchAllProfiles
import com.example.project_applepie.recyclerview.homeRecycle.SearchTeamRecyclerViewAdapter
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.SearchVolDetailResponse
import com.example.project_applepie.retrofit.domain.SearchVolunteerResponse
import com.example.project_applepie.sharedpref.SharedPref
import com.example.project_applepie.utils.Url
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    // Fragment에서 SharedPreference를 쓸 수 있게 해주는 코드
    private lateinit var homeActivity : HomeActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 서버 연동
        val url = Url.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiService::class.java)

        // 사용자의 uid & pid를 가져옴
        val uid = SharedPref.getUserId(homeActivity)
        val pid = SharedPref.getPid(homeActivity)

        var size = 5 // TODO: 테스트 할 때는 값을 크게
        var category = 0
        var id = 10 // TODO: 초기에 null값

        val sopModel = searchAllProfiles(size, category, id)
        server.searchOpenProfile(sopModel).enqueue(object : Callback<SearchVolDetailResponse>{
            override fun onFailure(call: Call<SearchVolDetailResponse>, t: Throwable) {
                Log.d("TF 정보 가져오기 실패", "$t")
            }

            override fun onResponse(call: Call<SearchVolDetailResponse>, response: Response<SearchVolDetailResponse>) {
                val userData = response.body()?.data
                Log.d("TF 정보 가져오기 성공?", "${response.body()?.data} + ${response.body()?.message} + $userData")
            }
        })

        val basicImg = R.drawable.charmander
        val itemList = arrayListOf(
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","가나다라마바사아"),
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
        searchAdapter.setOnItemClickListener(object : SearchTeamRecyclerViewAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: AuerProfile, pos: Int) {
                val intent = Intent(context, UserProfileSearchActivity::class.java)
                intent.putExtra("data",data)
                startActivity(intent)
            }
        })

    }
    fun bindAdpater(list : ArrayList<AuerProfile>, context : Context){
        searchAdapter = SearchTeamRecyclerViewAdapter()
        searchAdapter.submitList(list)
        searchAdapter.setOnItemClickListener(object : SearchTeamRecyclerViewAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: AuerProfile, pos: Int) {
                val intent = Intent(context, UserProfileSearchActivity::class.java)
                intent.putExtra("data",data)
                startActivity(intent)
            }
        })
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