package com.example.project_applepie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_applepie.databinding.FragmentPersonalInformationBinding
import com.example.project_applepie.model.boardList
import com.example.project_applepie.model.dao.modiOpen
import com.example.project_applepie.model.dao.personalAll
import com.example.project_applepie.model.dao.userTeamData
import com.example.project_applepie.model.myBoard
import com.example.project_applepie.model.myTeam
import com.example.project_applepie.model.recuit
import com.example.project_applepie.recyclerview.homeRecycle.SearchItemRecyclerViewAdapter
import com.example.project_applepie.recyclerview.profileRecycle.MyBoardAdapter
import com.example.project_applepie.recyclerview.profileRecycle.MyTeamAdapter
import com.example.project_applepie.recyclerview.profileRecycle.MyTeamAdapter2
import com.example.project_applepie.retrofit.ApiService
import com.example.project_applepie.retrofit.domain.BasicResponse
import com.example.project_applepie.retrofit.domain.SearchUserAllDataResponse
import com.example.project_applepie.sharedpref.SharedPref
import com.example.project_applepie.utils.Url
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
 * Use the [PersonalInformation.newInstance] factory method to
 * create an instance of this fragment.
 */
class PersonalInformation : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var searchAdapter : SearchItemRecyclerViewAdapter
    private lateinit var teamAdapter : MyTeamAdapter
    private lateinit var valunteerAdapter: MyTeamAdapter2
    private lateinit var boardAdapter : MyBoardAdapter
    private var _recruitBinding : FragmentPersonalInformationBinding? = null
    private val recruitBinding get() = _recruitBinding!!

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _recruitBinding = FragmentPersonalInformationBinding.inflate(inflater, container, false)
        val view = recruitBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()

        val url = Url.BASE_URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var server = retrofit.create(ApiService::class.java)

        // 사용자의 uid & pid를 가져옴
        val uid = SharedPref.getUserId(homeActivity)
        val pid = SharedPref.getPid(homeActivity)

//        Log.d("로그 - uid","$uid")
//        Log.d("로그 - pid","$pid")

        var boards : ArrayList<boardList> = ArrayList()
        var complete : ArrayList<userTeamData> = ArrayList()
        var incomplete : ArrayList<userTeamData> = ArrayList()
        var applys : ArrayList<userTeamData> = ArrayList()

        var uTeam : ArrayList<myTeam> = ArrayList()
        var uVolunteer : ArrayList<myTeam> = ArrayList()
        var uBoards : ArrayList<myBoard> = ArrayList()
        var uHistory : ArrayList<recuit> = ArrayList()

        var lesson = false
        var outsourcing = false
        var project = false

        val basicImg = "https://firebasestorage.googleapis.com/v0/b/applepie-f030c.appspot.com/o/file36-1?alt=media"
        val basicImg2 = "https://firebasestorage.googleapis.com/v0/b/applepie-f030c.appspot.com/o/file36-1?alt=media"
        val basicImg3 = "https://firebasestorage.googleapis.com/v0/b/applepie-f030c.appspot.com/o/file36-1?alt=media"

        //사용자 정보 모두 가져오기
        server.userAllData(uid).enqueue(object : Callback<SearchUserAllDataResponse>{
            override fun onResponse(call: Call<SearchUserAllDataResponse>, response: Response<SearchUserAllDataResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("로그 - 성공","${response.body().toString()}")
                    val jsonBoard = response.body()?.boards
//                    Log.d("로그123 - boards","$jsonBoard")
                    for(i in 0 until jsonBoard!!.size()){
                        val jsonObj = jsonBoard.get(i).asJsonObject
                        try{
                            var board = boardList(jsonObj.getAsJsonPrimitive("id").asInt,
                                jsonObj.getAsJsonPrimitive("title").asString, jsonObj.getAsJsonPrimitive("content").asString ,
                                jsonObj.getAsJsonPrimitive("viewCount").asInt,jsonObj.getAsJsonPrimitive("categoryId").asString,
                                jsonObj.getAsJsonPrimitive("file").asString,jsonObj.getAsJsonPrimitive("deadline").asString,
                                jsonObj.getAsJsonPrimitive("status").asBoolean)
                            var userBoard = myTeam(basicImg,board.title,board.id)
                            boards.add(board)
                            uTeam.add(userBoard)
                        }catch (e : RuntimeException){
                            Log.d("로그 - boards에러","${e.localizedMessage}")
                        }
                    }
                    val jsonComplete = response.body()?.complete
                    for(i in 0 until jsonComplete!!.size()){
                        val jsonObj = jsonComplete.get(i).asJsonObject
                        try{
                            val com = userTeamData(jsonObj.getAsJsonPrimitive("createAt").asString,
                            jsonObj.getAsJsonPrimitive("updateAt").asString,jsonObj.getAsJsonPrimitive("status").asInt,
                            jsonObj.getAsJsonPrimitive("id").asInt,jsonObj.getAsJsonPrimitive("teamName").asString,
                            jsonObj.getAsJsonPrimitive("teamContent").asString,
                            jsonObj.getAsJsonPrimitive("totalCount").asString.substring(1,jsonObj.getAsJsonPrimitive("totalCount").asString.lastIndex-1).split(","),
                            jsonObj.getAsJsonPrimitive("count").asString.substring(1,jsonObj.getAsJsonPrimitive("count").asString.lastIndex-1).split(","),
                            jsonObj.getAsJsonPrimitive("teamStatus").asString
                            )
                            val vol = myTeam(basicImg,com.teamName,com.id)
                            uTeam.add(vol)
                            complete.add(com)
                        }catch (e : RuntimeException){
                            Log.d("로그 - complete에러","${e.localizedMessage}")
                        }
                    }
                    val jsonInComplete = response.body()?.incomplete
                    for(i in 0 until jsonInComplete!!.size()){
                        val jsonObj = jsonInComplete.get(i).asJsonObject
                        Log.d("뭐지", "$jsonObj")
                        try{
                            val inCom = userTeamData(jsonObj.getAsJsonPrimitive("createAt").asString,
                                jsonObj.getAsJsonPrimitive("updateAt").asString,jsonObj.getAsJsonPrimitive("status").asInt,
                                jsonObj.getAsJsonPrimitive("id").asInt,jsonObj.getAsJsonPrimitive("teamName").asString,
                                jsonObj.getAsJsonPrimitive("teamContent").asString,
                                jsonObj.getAsJsonPrimitive("totalCount").asString.substring(1,jsonObj.getAsJsonPrimitive("totalCount").asString.lastIndex-1).split(","),
                                jsonObj.getAsJsonPrimitive("count").asString.substring(1,jsonObj.getAsJsonPrimitive("count").asString.lastIndex-1).split(","),
                                jsonObj.getAsJsonPrimitive("teamStatus").asString
                            )
                            incomplete.add(inCom)
                        }catch (e : RuntimeException){
                            Log.d("로그 - incomplete에러","${e.localizedMessage}")
                        }
                    }
                    val jsonApply = response.body()?.apply
                    for(i in 0 until jsonApply!!.size()){
                        val jsonObj = jsonApply.get(i).asJsonObject
                        try{
                            val apply = userTeamData(jsonObj.getAsJsonPrimitive("createAt").asString,
                                jsonObj.getAsJsonPrimitive("updateAt").asString,jsonObj.getAsJsonPrimitive("status").asInt,
                                jsonObj.getAsJsonPrimitive("id").asInt,jsonObj.getAsJsonPrimitive("teamName").asString,
                                jsonObj.getAsJsonPrimitive("teamContent").asString,
                                jsonObj.getAsJsonPrimitive("totalCount").asString.substring(1,jsonObj.getAsJsonPrimitive("totalCount").asString.lastIndex-1).split(","),
                                jsonObj.getAsJsonPrimitive("count").asString.substring(1,jsonObj.getAsJsonPrimitive("count").asString.lastIndex-1).split(","),
                                jsonObj.getAsJsonPrimitive("teamStatus").asString
                            )
                            val app = myTeam(basicImg,apply.teamName,apply.id)
                            uVolunteer.add(app)
                            applys.add(apply)
                        }catch (e : RuntimeException){
                            Log.d("로그 - apply에러","${e.localizedMessage}")
                        }
                    }
                    lesson = response.body()!!.lesson
                    outsourcing = response.body()!!.outsourcing
                    project = response.body()!!.project

                    Log.d("로그 - boards","$boards")
                    Log.d("로그 - uBoards","$uBoards")
                    Log.d("로그 - complete","$complete")
                    Log.d("로그 - incomplete","$incomplete")
                    Log.d("로그 - applys","$applys")
                    Log.d("로그 - lesson","$lesson")
                    Log.d("로그 - outsourcing","$outsourcing")
                    Log.d("로그 - project","$project")

                    //활동이력
                    searchAdapter = SearchItemRecyclerViewAdapter(view.context)
                    searchAdapter.submitList(uHistory)
                    recruitBinding.rvRecruit.layoutManager = LinearLayoutManager(view.context,
                        LinearLayoutManager.VERTICAL,false)
                    recruitBinding.rvRecruit.adapter = searchAdapter

                    // 나의 팀 Adapter
                    teamAdapter = MyTeamAdapter(view.context)
                    teamAdapter.submitList(uTeam)
                    recruitBinding.rvCreateMyTeam.layoutManager = LinearLayoutManager(view.context,
                        LinearLayoutManager.VERTICAL,false)
                    recruitBinding.rvCreateMyTeam.adapter = teamAdapter

                    teamAdapter.setOnItemClickListener(object : MyTeamAdapter.OnItemClickListener{
                        override fun onItemClick(v: View, data: myTeam, pos: Int) {
                            val intent = Intent(context, ViewVolunteerAcitviy::class.java)
                            intent.putExtra("data",data)
                            startActivity(intent)
                        }
                    })

                    // 내가 지원한 팀 Adapter
                    valunteerAdapter = MyTeamAdapter2(view.context)
                    valunteerAdapter.submitList(uVolunteer)
                    recruitBinding.rvApplyMyTeam.layoutManager = LinearLayoutManager(view.context,
                        LinearLayoutManager.VERTICAL,false)
                    recruitBinding.rvApplyMyTeam.adapter = valunteerAdapter


                    // 나의 글 Adapter
                    boardAdapter = MyBoardAdapter(view.context)
                    boardAdapter.submitList(uBoards)
                    recruitBinding.rvMyBoard.layoutManager = LinearLayoutManager(view.context,
                        LinearLayoutManager.VERTICAL,false)
                    recruitBinding.rvMyBoard.adapter = boardAdapter

                    boardAdapter.setOnItemClickListener(object : MyBoardAdapter.OnItemClickListener{
                        override fun onItemClick(v: View, data: myBoard, pos: Int) {
                            val intent = Intent(context, ViewVolunteerAcitviy::class.java)
                            intent.putExtra("data",data)
                            startActivity(intent)
                        }
                    })

                    // 나의 글 확인
                    /*boardAdapter = MyBoardAdapter(view.context)
                    boardAdapter.submitList(uBoards)
                    Log.d("uBoards 확인", "$uBoards")
                    recruitBinding.rvMyBoard.layoutManager = LinearLayoutManager(view.context,
                        LinearLayoutManager.VERTICAL,false)
                    recruitBinding.rvMyBoard.adapter = boardAdapter*/
                }
            }
            override fun onFailure(call: Call<SearchUserAllDataResponse>, t: Throwable) {
                Log.d("로그 - 서버실패","${t.localizedMessage}")
            }
        })

//        for(i in 0 .. boards!!.size){
//            server.
//        }

        server.searchAllFile(uid).enqueue(object : Callback<personalAll>{
            override fun onResponse(call: Call<personalAll>, response: Response<personalAll>) {
                if(response.isSuccessful){
                    Log.d("전체 조회 로그 - 성공","${response.body().toString()}")
                }else{
                    Log.d("전체 로그 - 실패","${response.body().toString()}")
                }
            }

            override fun onFailure(call: Call<personalAll>, t: Throwable) {
                Log.d("전체 조회 로그 - 서버실패","${t.localizedMessage}, $uid")
            }
        })

        val itemList = ArrayList<recuit>()

        /*val itemList = arrayListOf(
            recuit(basicImg,"이상해씨","이상해씨-이상해풀-이상해꽃"),
            recuit(basicImg3,"파이리","파이리-리자드-리자몽"),
            recuit(basicImg2,"꼬부기","꼬부기-어니부기-거북왕"),
            recuit(basicImg3,"이상해씨","이상해씨-이상해풀-이상해꽃")
        )*/
        /*val itemList2 = arrayListOf(
            myTeam(basicImg,"이상해씨",17),
            myTeam(basicImg3,"파이리",17),
            myTeam(basicImg2,"꼬부기",17),
            myTeam(basicImg3,"이상해씨",17)
        )*/
//        val itemList3 = arrayListOf(
//            myBoard(basicImg,"이상해씨", 10000),
//            myBoard(basicImg3,"파이리", 10000),
//            myBoard(basicImg2,"꼬부기", 10000),
//            myBoard(basicImg3,"이상해씨", 10000)
//        )


        // 프로필 수정 버튼 클릭 시
        recruitBinding.btnModifyProfile.setOnClickListener {
            val intent = Intent(context,ModifyProfileActivity::class.java)
            startActivity(intent)
        }

        // 로그아웃 버튼 클릭 시 처음 화면으로 이동
        recruitBinding.userLogOut.setOnClickListener {
            activity?.finish()
        }

        // 자기소개 수정 버튼 클릭 시 modify_detail_profile 화면으로 이동
        recruitBinding.btnModifyIntro.setOnClickListener {
            val intent = Intent(context, modifyDetailProfile::class.java)
            startActivity(intent)
        }

        // OPEN 여부 판단 (SWITCH)
        recruitBinding.swOutsourcing.setOnCheckedChangeListener { buttonView, idChecked ->
            // 사용자 정보 조회하기
            val url = Url.BASE_URL
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            var server = retrofit.create(ApiService::class.java)

            if(!idChecked){
                val modiOpen = modiOpen(0, 0)
                server.modiOpenProfile(pid, modiOpen).enqueue(object : Callback<BasicResponse>{
                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.d("OPEN-OUT-ON", "Fail")
                        Log.d("OPEN-OUT-ON", "$t")
                    }

                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                        Log.d("OPEN-OUT-ON", "CLOSE")
                        Log.d("OPEN-OUT-ON", "${response.body()?.message.toString()} + $idChecked")
                    }
                })
            }else{
                val modiOpen = modiOpen(0, 1)
                server.modiOpenProfile(pid, modiOpen).enqueue(object : Callback<BasicResponse>{
                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.d("OPEN-OUT-OFF", "Fail")
                        Log.d("OPEN-OUT-OFF", "$t")
                    }

                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                        Log.d("OPEN-OUT-OFF", "OPEN")
                        Log.d("OPEN-OUT-OFF", "${response.body()?.message.toString()} + $idChecked")
                    }
                })
            }
        }

        recruitBinding.swAssignment.setOnCheckedChangeListener { _, idChecked ->
            if(!idChecked){
                val modiOpen = modiOpen(1, 0)
                server.modiOpenProfile(pid, modiOpen).enqueue(object : Callback<BasicResponse>{
                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.d("OPEN-LES-ON", "Fail")
                        Log.d("OPEN-LES-ON", "$t")
                    }

                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                        Log.d("OPEN-LES-ON1", "CLOSE")
                        Log.d("OPEN-LES-ON2", "${response.body()?.message.toString()}")
                    }
                })
            }else{
                val modiOpen = modiOpen(1, 1)
                server.modiOpenProfile(pid, modiOpen).enqueue(object : Callback<BasicResponse>{
                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.d("OPEN-LES-OFF", "Fail")
                        Log.d("OPEN-LES-OFF", "$t")
                    }

                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                        Log.d("OPEN-LES-OFF", "OPEN")
                        Log.d("OPEN-LES-OFF", "${response.body()?.message.toString()}")
                    }
                })
            }
        }

        recruitBinding.swCompetition.setOnCheckedChangeListener { buttonView, idChecked ->
            if(!idChecked){
                val modiOpen = modiOpen(2, 0)
                server.modiOpenProfile(pid, modiOpen).enqueue(object : Callback<BasicResponse>{
                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.d("OPEN-PRO-ON", "Fail")
                        Log.d("OPEN-PRO-ON", "$t")
                    }

                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                        Log.d("OPEN-PRO-ON", "CLOSE")
                        Log.d("OPEN-PRO-ON", "${response.body()?.message.toString()}")
                    }
                })
            }else{
                val modiOpen = modiOpen(2, 1)
                server.modiOpenProfile(pid, modiOpen).enqueue(object : Callback<BasicResponse>{
                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.d("OPEN-PRO-OFF", "Fail")
                        Log.d("OPEN-PRO-OFF", "$t")
                    }

                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                        Log.d("OPEN-PRO-OFF", "OPEN")
                        Log.d("OPEN-PRO-OFF", "${response.body()?.message.toString()}")
                    }
                })
            }
        }


        // 회원탈퇴 클릭 시
        recruitBinding.btnDeleteAccount.setOnClickListener {
            val url = Url.BASE_URL
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            var server = retrofit.create(ApiService::class.java)

            server.deleteUser(uid).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                    Log.d("성공 확인", "$response")
                    Toast.makeText(homeActivity, "회원탈퇴가 성공적으로 처리됐습니다.", Toast.LENGTH_SHORT).show()
                    activity?.finish()
                    // TODO: 다이얼로그 만들기?
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    Log.d("실패 확인", "$t")
                    Toast.makeText(homeActivity, "(서버오류) 회원가입 탈퇴에 실패했습니다", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _recruitBinding = null
    }

    private fun setOnClickListener(){
//        val btnSequence = _recruitBinding.container.children
    }


    override fun onClick(v: View) {

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PersonalInformation.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PersonalInformation().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}