package com.example.teststorymaker

import android.util.Log
import retrofit2.Response

class StoryInformRepository {
    suspend fun sendInform(data: SubmitInform):Response<InformResponse>
    {
        val dataMap=mapOf(
            "name" to data.name,
            "sex" to data.sex,
            "age" to data.age,
            "personality" to data.personality,
            "name2" to data.name2,
            "subject" to data.subject

        )
        Log.d("repository","sendInform함수")
//       return RetrofitClient.apiService.sendData(data)
        return RetrofitClient.apiService.sendData2(dataMap)
//        return RetrofitClient.apiService.sendData3(data.name,data.sex,data.age,data.personality,data.name2,data.subject)
    }

}