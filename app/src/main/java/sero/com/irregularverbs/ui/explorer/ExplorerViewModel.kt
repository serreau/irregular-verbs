package sero.com.irregularverbs.ui.explorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import sero.com.irregularverbs.data.db.Verbs
import sero.com.irregularverbs.data.repository.LocalVerbsRepository

class ExplorerViewModel : ViewModel() {

    fun getAll() : List<Verbs>  = viewModelScope.run { LocalVerbsRepository.getAll() }
    fun updateVerb(verb : Verbs) = viewModelScope.run { LocalVerbsRepository.updateVerb(verb) }
}