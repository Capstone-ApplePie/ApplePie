package com.example.project_applepie.retrofit.domain

import com.example.project_applepie.model.boardDetailBoard
import com.example.project_applepie.model.dao.userTeamData
import com.example.project_applepie.model.volunteerList

data class UserAllData(
    var status : Int,
    var code : String,
    var message : String,
    var board : List<boardDetailBoard>,
    var complete : List<userTeamData>,
    var incomplete : List<userTeamData>,
    var apply : List<userTeamData>
)
