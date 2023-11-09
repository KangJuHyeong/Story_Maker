package com.example.teststorymaker

import android.util.Log
import retrofit2.Response

class StoryInformRepository {
    suspend fun sendInform(data: SubmitInform):Response<InformResponse>
    {
        Log.d("repository","sendInform함수")
       return RetrofitClient.apiService.sendData(data)
    }

}