package com.admiral.demo.features.home.gun.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.admiral.demo.features.home.gun.data.GunApiConfig
import com.admiral.demo.features.home.gun.data.repository.GunRepository
import com.admiral.demo.features.home.gun.data.service.GunServiceFactory
import com.admiral.demo.features.home.gun.domain.useCase.GetTemplateUseCase
import com.admiral.demo.features.home.gun.domain.useCase.IGetTemplateUseCase
import com.admiral.demo.features.home.gun.presentation.model.GunState
import com.admiral.network.RetrofitFactory
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class GunFragmentVm: ViewModel() {
    private val mutableStateFlow = MutableStateFlow<GunState>(GunState.SearchState(""))
    val stateFlow: StateFlow<GunState> = mutableStateFlow.asStateFlow()

    private lateinit var getTemplateUseCase: IGetTemplateUseCase

    fun init(context: Context) {
        val service = GunServiceFactory(
            retrofitFactory = RetrofitFactory(
                apiConfig = GunApiConfig,
                context = context,
                gson = GsonBuilder().create()
            )
        ).serviceHolder.service

        getTemplateUseCase = GetTemplateUseCase(repository = GunRepository(gunService = service))
//        getTemplateUseCase = MockGetTemplateUseCase()
    }

    fun onOpenSearchAction(idTemplate: String) {
        mutableStateFlow.value = GunState.SearchState(idTemplate)
    }

    fun loadTemplate(templateId: String) {
        mutableStateFlow.value = GunState.LoadingState

        viewModelScope.launch {
            try {
                val gunTemplate = getTemplateUseCase.execute(templateId)
                    .toTemplatePresentation()

                mutableStateFlow.value = GunState.SuccessState(
                    gunTemplate = gunTemplate
                )
            } catch (ex: Exception) {
                mutableStateFlow.value = GunState.ErrorState(
                    description = ex.toString() //"Что-то пошло не так."
                )
            }
        }
    }
}