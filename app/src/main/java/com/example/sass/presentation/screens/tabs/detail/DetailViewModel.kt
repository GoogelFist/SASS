package com.example.sass.presentation.screens.tabs.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sass.domain.models.PictureDetail
import com.example.sass.domain.usecases.GetPictureDetailUseCase
import com.example.sass.presentation.screens.EventHandler
import com.example.sass.presentation.screens.tabs.detail.models.DetailEvent
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getPictureDetailUseCase: GetPictureDetailUseCase
) : ViewModel(), EventHandler<DetailEvent> {

    private var _pictureDetail = MutableLiveData<PictureDetail>()
    val pictureDetail: LiveData<PictureDetail>
        get() = _pictureDetail


    override fun obtainEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.OnGetDetailPicture -> getDetailPicture(event.id)
        }
    }

    private fun getDetailPicture(id: String) {
        viewModelScope.launch {
            val pictureDetail = getPictureDetailUseCase(id)
            _pictureDetail.value = pictureDetail
        }
    }
}
