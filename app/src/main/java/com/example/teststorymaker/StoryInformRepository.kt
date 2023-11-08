package com.example.teststorymaker

import retrofit2.Response

class StoryInformRepository {
    suspend fun sendInform(data: SubmitInform):Response<InformResponse>
    {
       return RetrofitClient.apiService.sendData(data)
    }

}