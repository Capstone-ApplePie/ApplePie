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
import com.example.project_applepie.databinding.FragmentRecruitBinding
import com.example.project_applepie.model.boardList
import com.example.project_applepie.model.dao.board
import com.example.project_applepie.model.recuit
import com.example.project_applepie.recyclerview.homeRecycle.SearchItemRecyclerViewAdapter
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.BoardResponse
import com.example.project_applepie.utils.Url
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    // Fragment에서 SharedPreference를 쓸 수 있게 해주는 코드
    private lateinit var homeActivity : HomeActivity

    val itemList : ArrayList<recuit> = ArrayList()
    val itemList2 : ArrayList<recuit> = ArrayList()
    val itemList3 : ArrayList<recuit> = ArrayList()
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
        val basicImg = R.drawable.charmander

        // Retrofit 연동
        val url = Url.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiService::class.java)

        var arrList1:ArrayList<boardList> = ArrayList()
        var arrList2:ArrayList<boardList> = ArrayList()
        var arrList3:ArrayList<boardList> = ArrayList()

        var id = 2 // TODO: 초기에 null값 <-- ??????
//        var category = 2
        var size = 5 // TODO: 테스트 할 때는 값을 크게
        val boardModel1 = board(0,size)
        val boardModel2 = board(1,size)
        val boardModel3 = board(2,size)

        //cate1번 가져오기
        server.searchBoard(boardModel1).enqueue(object :Callback<BoardResponse>{
            override fun onResponse(call: Call<BoardResponse>, response: Response<BoardResponse>) {
                Log.d("글1 확인 로그","${response.body().toString()}")

                val jsonArr= response.body()?.boardList
                var size = jsonArr!!.size()
                for(i in 0..size-1){
                    val jsonObj = jsonArr.get(i).asJsonObject
                    try{
                        var board = boardList(jsonObj.getAsJsonPrimitive("id").asInt,
                            jsonObj.getAsJsonPrimitive("title").asString, jsonObj.getAsJsonPrimitive("content").asString ,
                            jsonObj.getAsJsonPrimitive("viewCount").asInt,jsonObj.getAsJsonPrimitive("categoryId").asString,
                            jsonObj.getAsJsonPrimitive("file").asString,jsonObj.getAsJsonPrimitive("deadline").asString,
                            jsonObj.getAsJsonPrimitive("status").asBoolean)
                        arrList1.add(board)
                    }catch (e : RuntimeException){
                        Log.d("에러 로그","${e.message}")
                    }
                }
                Log.d("로그1 arr test","$arrList1")
                for(i in arrList1){
                    val re = recuit(i.file,i.title,i.content,i.id)
                    Log.d("로그-배열_1_re","$re")
                    itemList.add(re)
                }
            }

            override fun onFailure(call: Call<BoardResponse>, t: Throwable) {
                Log.d("서버 연동", "글 가져오기 실패")
                Log.d("서버 연동","${t.message}")
            }

        })
        //cate2번 가져오기
        server.searchBoard(boardModel2).enqueue(object :Callback<BoardResponse>{
            override fun onResponse(call: Call<BoardResponse>, response: Response<BoardResponse>) {
                Log.d("글2 확인 로그","${response.body().toString()}")

                val jsonArr= response.body()?.boardList
                var size = jsonArr!!.size()
                for(i in 0..size-1){
                    val jsonObj = jsonArr.get(i).asJsonObject
                    try{
                        var board = boardList(jsonObj.getAsJsonPrimitive("id").asInt,
                            jsonObj.getAsJsonPrimitive("title").asString, jsonObj.getAsJsonPrimitive("content").asString ,
                            jsonObj.getAsJsonPrimitive("viewCount").asInt,jsonObj.getAsJsonPrimitive("categoryId").asString,
                            jsonObj.getAsJsonPrimitive("file").asString,jsonObj.getAsJsonPrimitive("deadline").asString,
                            jsonObj.getAsJsonPrimitive("status").asBoolean)
                        arrList2.add(board)
                    }catch (e : RuntimeException){
                        Log.d("에러 로그","${e.message}")
                    }
                }
                for(i in arrList2){
                    val re = recuit(i.file,i.title,i.content,i.id)
                    itemList2.add(re)
                }
                Log.d("로그2 arr test","$arrList2")
            }

            override fun onFailure(call: Call<BoardResponse>, t: Throwable) {
                Log.d("서버 연동", "글 가져오기 실패")
                Log.d("서버 연동","${t.message}")
            }

        })
        //cate3번 가져오기
        server.searchBoard(boardModel3).enqueue(object :Callback<BoardResponse>{
            override fun onResponse(call: Call<BoardResponse>, response: Response<BoardResponse>) {
                Log.d("글3 확인 로그","${response.body().toString()}")
                //Log.d("글3 확인 로그","${response.body()?.boardList?.get(2)}")

                val jsonArr= response.body()?.boardList
                var size = jsonArr!!.size()
                for(i in 0..size-1){
                    val jsonObj = jsonArr.get(i).asJsonObject
                    try{
                        var board = boardList(jsonObj.getAsJsonPrimitive("id").asInt,
                            jsonObj.getAsJsonPrimitive("title").asString, jsonObj.getAsJsonPrimitive("content").asString ,
                            jsonObj.getAsJsonPrimitive("viewCount").asInt,jsonObj.getAsJsonPrimitive("categoryId").asString,
                            jsonObj.getAsJsonPrimitive("file").asString,jsonObj.getAsJsonPrimitive("deadline").asString,
                            jsonObj.getAsJsonPrimitive("status").asBoolean)
                        arrList3.add(board)
                    }catch (e : RuntimeException){
                        Log.d("에러 로그","${e.message}")
                    }
                }
                for(i in arrList3){
                    val re = recuit(i.file,i.title,i.content,i.id)
                    itemList3.add(re)
                }
                Log.d("로그3 arr test","$arrList3")
            }

            override fun onFailure(call: Call<BoardResponse>, t: Throwable) {
                Log.d("서버 연동", "글 가져오기 실패")
                Log.d("서버 연동","${t.message}")
            }
        })






        Log.d("로그-배열_1","$itemList")
        Log.d("로그-배열_2","$itemList2")
        Log.d("로그-배열_3","$itemList3")

        /*val itemList = arrayListOf(
            recuit(basicImg,"이상해씨","이상해씨-이상해풀-이상해꽃"),
            recuit(basicImg,"파이리","파이리-리자드-리자몽"),
            recuit(basicImg,"꼬부기","꼬부기-어니부기-거북왕"),
            recuit(basicImg,"이상해씨","이상해씨-이상해풀-이상해꽃"),
            recuit(basicImg,"이상해씨","이상해씨-이상해풀-이상해꽃"),
            recuit(basicImg,"파이리","파이리-리자드-리자몽"),
            recuit(basicImg,"꼬부기","꼬부기-어니부기-거북왕"),
            recuit(basicImg,"이상해씨","이상해씨-이상해풀-이상해꽃")

        )*/

       /* val basicImg2 = R.drawable.bulbasaur
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
        )*/

        searchAdapter = SearchItemRecyclerViewAdapter(view.context)
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
            val intent = Intent(context, CreateBoardActivity::class.java)
            startActivity(intent)
        }

    }
    fun bindAdpater(list : ArrayList<recuit>, context : Context){
        searchAdapter = SearchItemRecyclerViewAdapter(context)
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

