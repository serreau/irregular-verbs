package sero.com.irregularverbs.ui.explorer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sero.com.irregularverbs.data.db.Verbs
import sero.com.irregularverbs.data.repository.LocalVerbsRepository

class ExplorerViewModel : ViewModel() {

    val getAll : LiveData<List<Verbs>>  by lazy { LocalVerbsRepository.getAll() }
    fun updateVerb(verb : Verbs) =  viewModelScope.launch { LocalVerbsRepository.updateVerb(verb) }
}