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
import com.example.project_applepie.databinding.FragmentPersonalInformationBinding
import com.example.project_applepie.model.dao.inquireUserInfo
import com.example.project_applepie.model.myBoard
import com.example.project_applepie.model.myTeam
import com.example.project_applepie.model.recuit
import com.example.project_applepie.recyclerview.homeRecycle.SearchItemRecyclerViewAdapter
import com.example.project_applepie.recyclerview.profileRecycle.MyTeamAdapter
import com.example.project_applepie.recyclerview.profileRecycle.myBoardAdapter
import com.example.project_applepie.retrofit.ApiService
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
    private lateinit var valunteerAdapter: MyTeamAdapter
    private lateinit var boardAdapter : myBoardAdapter
    private var _recruitBinding : FragmentPersonalInformationBinding? = null
    private val recruitBinding get() = _recruitBinding!!

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

        var uid = "10007"
        server.inquireUserInfo(uid).enqueue(object : Callback<inquireUserInfo>{
            override fun onResponse(call: Call<inquireUserInfo>, response: Response<inquireUserInfo>
            ) {
                Log.d("로그","${response.body().toString()}")
                var eamil = response.body()?.data?.get("email");
                Log.d("로그","$eamil")
            }

            override fun onFailure(call: Call<inquireUserInfo>, t: Throwable) {
                Log.d("로그_서버연동 실패","${t.message}")
            }

        })

        val basicImg = R.drawable.charmander
        val basicImg2 = R.drawable.bulbasaur
        val basicImg3 = R.drawable.turtle

        val itemList = arrayListOf(
            recuit(basicImg,"이상해씨","이상해씨-이상해풀-이상해꽃"),
            recuit(basicImg3,"파이리","파이리-리자드-리자몽"),
            recuit(basicImg2,"꼬부기","꼬부기-어니부기-거북왕"),
            recuit(basicImg3,"이상해씨","이상해씨-이상해풀-이상해꽃")
        )
        val itemList2 = arrayListOf(
            myTeam(basicImg,"이상해씨"),
            myTeam(basicImg3,"파이리"),
            myTeam(basicImg2,"꼬부기"),
            myTeam(basicImg3,"이상해씨")
        )
        val itemList3 = arrayListOf(
            myBoard(basicImg,"이상해씨"),
            myBoard(basicImg3,"파이리"),
            myBoard(basicImg2,"꼬부기"),
            myBoard(basicImg3,"이상해씨")
        )

        searchAdapter = SearchItemRecyclerViewAdapter()
        searchAdapter.submitList(itemList)
        recruitBinding.rvRecruit.layoutManager = LinearLayoutManager(view.context,
            LinearLayoutManager.VERTICAL,false)
        recruitBinding.rvRecruit.adapter = searchAdapter

        teamAdapter = MyTeamAdapter()
        teamAdapter.submitList(itemList2)
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

        valunteerAdapter = MyTeamAdapter()
        valunteerAdapter.submitList(itemList2)
        recruitBinding.rvApplyMyTeam.layoutManager = LinearLayoutManager(view.context,
            LinearLayoutManager.VERTICAL,false)
        recruitBinding.rvApplyMyTeam.adapter = valunteerAdapter

        valunteerAdapter.setOnItemClickListener(object : MyTeamAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: myTeam, pos: Int) {
                val intent = Intent(context, ViewTeamActivity::class.java)
                intent.putExtra("data",data)
                startActivity(intent)
            }
        })

        boardAdapter = myBoardAdapter()
        boardAdapter.submitList(itemList3)
        recruitBinding.rvMyBoard.layoutManager = LinearLayoutManager(view.context,
            LinearLayoutManager.VERTICAL,false)
        recruitBinding.rvMyBoard.adapter = boardAdapter

        recruitBinding.btnModifyProfile.setOnClickListener {
            val intent = Intent(context,ModifyProfileActivity::class.java)
            startActivity(intent)
        }

        recruitBinding.swOutsourcing.setOnCheckedChangeListener { buttonView, idChecked ->
            if(buttonView.isChecked){
                Log.d("로그","외주 on")
            }else{
                Log.d("로그","외주 off")
            }
        }
        recruitBinding.swAssignment.setOnCheckedChangeListener { buttonView, idChecked ->
            if(buttonView.isChecked){
                Log.d("로그","과제/과외 on")
            }else{
                Log.d("로그","과제/과외 off")
            }
        }
        recruitBinding.swCompetition.setOnCheckedChangeListener { buttonView, idChecked ->
            if(buttonView.isChecked){
                Log.d("로그","공모전 on")
            }else{
                Log.d("로그","공모전 off")
            }
        }


        // 로그아웃 버튼 클릭 시 처음 화면으로 이동
        recruitBinding.userLogOut.setOnClickListener {
//            var intent = Intent(context, MainActivity::class.java)
//            startActivity(intent)
            activity?.finish()
        }

        //switch 체크 유무
        recruitBinding.swOutsourcing.setOnCheckedChangeListener { compoundButton, b ->
            Log.d("로그","외주 체크 유무 : $b")
        }
        recruitBinding.swCompetition.setOnCheckedChangeListener { compoundButton, b ->
            Log.d("로그","공모전 체크 유무 : $b")
        }
        recruitBinding.swAssignment.setOnCheckedChangeListener { compoundButton, b ->
            Log.d("로그","과외/과제 체크 유무 : $b")
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _recruitBinding = null
    }

    private fun setOnClickListener(){
//        val btnSequence = _recruitBinding.container.children
    }

    // Fragment 클릭 이벤트 (적용이 안돼서 노력중...)
    override fun onClick(v: View) {
        when(v.id){
            R.id.userLogOut -> {
                Log.d("check", "check")
            }
        }
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