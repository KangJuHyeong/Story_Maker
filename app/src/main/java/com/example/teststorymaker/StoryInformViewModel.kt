package com.example.teststorymaker

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Response

class StoryInformViewModel(private val repository: StoryInformRepository) :ViewModel(){
    val response1: MutableLiveData<Response<InformResponse>> = MutableLiveData()



    fun sendInform(data: SubmitInform){
       viewModelScope.launch {
           val response=repository.sendInform(data)
           response1.value=response
       }
    }

}
class StoryInformViewModelFactory(
    private val repository: StoryInformRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StoryInformViewModel(repository) as T
    }
}