package es.maincode.openbanktest.view.viewModels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.maincode.domain.detail.GetDetailUseCase
import es.maincode.openbanktest.base.BaseViewModel
import es.maincode.openbanktest.view.vo.CharacterVO
import es.maincode.openbanktest.view.vo.toPresentation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailUseCase: GetDetailUseCase
): BaseViewModel() {

    private val _character = MutableStateFlow<CharacterVO?>(null)
    val character: StateFlow<CharacterVO?> get() = _character

    fun getDetail(id: Int) {
        viewModelScope.launch {
            detailUseCase(id).collect { response ->
                response.success?.let {
                    val vo = it.toPresentation()
                    _character.value = vo.data.results.first()
                }
            }
        }
    }
}