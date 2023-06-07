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
import com.example.project_applepie.model.dao.SearchAllProfiles
import com.example.project_applepie.model.profileData
import com.example.project_applepie.recyclerview.homeRecycle.SearchTeamRecyclerViewAdapter
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.SearchVolDetailResponse
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

    val itemList1 : ArrayList<AuerProfile> = ArrayList()
    val itemList2 : ArrayList<AuerProfile> = ArrayList()
    val itemList3 : ArrayList<AuerProfile> = ArrayList()

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

        // 프로필 정보를 담을 List
        var arrList1:ArrayList<profileData> = ArrayList()
        var arrList2:ArrayList<profileData> = ArrayList()
        var arrList3:ArrayList<profileData> = ArrayList()

        var size = 15 // TODO: 테스트 할 때는 값을 크게
//        var category = 0
        var id = 10 // TODO: 초기에 null값

        val sopModel1 = SearchAllProfiles(size, 0, id) // 외주
        val sopModel2 = SearchAllProfiles(size, 1, id) // 과외 과제
        val sopModel3 = SearchAllProfiles(size, 2, id) // 공모전

        // 외주 프로필 가져오기
        server.searchOpenProfile(sopModel1).enqueue(object : Callback<SearchVolDetailResponse>{
            override fun onFailure(call: Call<SearchVolDetailResponse>, t: Throwable) {
                Log.d("TF 외주 정보 가져오기 실패", "$t")
            }

            override fun onResponse(call: Call<SearchVolDetailResponse>, response: Response<SearchVolDetailResponse>) {
//                val userData = response.body()?.data

                val jsonArrOut = response.body()?.data
                var jsSize = jsonArrOut!!.size()
                Log.d("TF 외주 정보 가져오기 성공?", "${response.body()?.data} + ${response.body()?.message} + $jsonArrOut")

                for(i in 0 until jsSize){
                    val jsonObj = jsonArrOut.get(i).asJsonObject
                    try{
                        var proData = profileData(jsonObj.getAsJsonPrimitive("oid").asString,
                            jsonObj.getAsJsonPrimitive("introduce").asString, jsonObj.getAsJsonPrimitive("category").asString )
                        arrList1.add(proData)
                    }catch (e : RuntimeException){
                        Log.d("외주 프로필 에러 로그","${e.message}")
                    }
                }

                Log.d("외주 프로필 data1 test","$jsonArrOut")
                Log.d("외주 프로필 arr1 test","$arrList1")
                for(i in arrList1){
                    val basicImg = R.drawable.user
                    val re = AuerProfile(basicImg, "user",i.category,i.introduce, i.oid) // TODO: AuerProfile에 oid 추가
//                    Log.d("로그-배열_1_re","$re")
                    itemList1.add(re)
                }

                searchAdapter = SearchTeamRecyclerViewAdapter()
                searchAdapter.submitList(itemList1)
                teamBinding.rvTeam.layoutManager = LinearLayoutManager(view.context,
                    LinearLayoutManager.VERTICAL,false)
                teamBinding.rvTeam.adapter = searchAdapter
            }
        })

        // 과외 과제 프로필 가져오기
        server.searchOpenProfile(sopModel2).enqueue(object : Callback<SearchVolDetailResponse>{
            override fun onFailure(call: Call<SearchVolDetailResponse>, t: Throwable) {
                Log.d("TF 과외 과제 정보 가져오기 실패", "$t")
            }

            override fun onResponse(call: Call<SearchVolDetailResponse>, response: Response<SearchVolDetailResponse>) {
//                val userData = response.body()?.data

                val jsonArrLes = response.body()?.data
                var jsSize = jsonArrLes!!.size()
                Log.d("TF 과외 과제 정보 가져오기 성공?", "${response.body()?.data} + ${response.body()?.message} + $jsonArrLes")

                for(i in 0 until jsSize){
                    val jsonObj = jsonArrLes.get(i).asJsonObject
                    try{
                        var proData = profileData(jsonObj.getAsJsonPrimitive("oid").asString,
                            jsonObj.getAsJsonPrimitive("introduce").asString, jsonObj.getAsJsonPrimitive("category").asString )
                        arrList2.add(proData)
                    }catch (e : RuntimeException){
                        Log.d("과외 과제 프로필 에러 로그","${e.message}")
                    }
                }

                Log.d("과외 과제 프로필 arr2 test","$arrList2")
                for(i in arrList2){
                    val basicImg = R.drawable.bulbasaur
                    val re = AuerProfile(basicImg, "user",i.category,i.introduce, i.oid)
//                    Log.d("로그-배열_2_re","$re")
                    itemList2.add(re)
                }
            }
        })

        // 공모전 프로필 가져오기
        server.searchOpenProfile(sopModel3).enqueue(object : Callback<SearchVolDetailResponse>{
            override fun onFailure(call: Call<SearchVolDetailResponse>, t: Throwable) {
                Log.d("TF 공모전 정보 가져오기 실패", "$t")
            }

            override fun onResponse(call: Call<SearchVolDetailResponse>, response: Response<SearchVolDetailResponse>) {
//                val userData = response.body()?.data

                val jsonArrPro = response.body()?.data
                var jsSize = jsonArrPro!!.size()
                Log.d("TF 공모전 정보 가져오기 성공?", "${response.body()?.data} + ${response.body()?.message} + $jsonArrPro")

                for(i in 0 until jsSize){
                    val jsonObj = jsonArrPro.get(i).asJsonObject
                    try{
                        var proData = profileData(jsonObj.getAsJsonPrimitive("oid").asString,
                            jsonObj.getAsJsonPrimitive("introduce").asString, jsonObj.getAsJsonPrimitive("category").asString )
                        arrList3.add(proData)
                    }catch (e : RuntimeException){
                        Log.d("외주 프로필 에러 로그","${e.message}")
                    }
                }

                Log.d("공모전 프로필 arr3 test","$arrList3")
                for(i in arrList3){
                    val basicImg = R.drawable.charmander
                    val re = AuerProfile(basicImg, "user",i.category,i.introduce, i.oid)
//                    Log.d("로그-배열_3_re","$re")
                    itemList3.add(re)
                }
            }
        })

        Log.d("생성 데이터 확인","$itemList1 + $itemList2 + $itemList3")

//        val itemList = arrayListOf(
//            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
//            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
//            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
//            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","가나다라마바사아"),
//            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
//            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
//            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
//            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다"),
//            AuerProfile(basicImg,"파이리","도롱뇽포켓몬","뜨거운 것을 좋아하는 성격이다")
//        )

        searchAdapter = SearchTeamRecyclerViewAdapter()
        searchAdapter.submitList(itemList1)
        teamBinding.rvTeam.layoutManager = LinearLayoutManager(view.context,
            LinearLayoutManager.VERTICAL,false)
        teamBinding.rvTeam.adapter = searchAdapter

        teamBinding.tabLayoutRecruit.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 ->{
                        bindAdpater(itemList1,view.context)
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