package sero.com.irregularverbs.ui.progression

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sero.com.irregularverbs.data.db.Verbs
import sero.com.irregularverbs.data.repository.LocalVerbsRepository

class ProgressionViewModel : ViewModel() {

    val allVerbs : LiveData<List<Verbs>> by lazy { LocalVerbsRepository.getAll() }
    val countAll : LiveData<Int> by lazy { LocalVerbsRepository.countAll() }
}