package sero.com.irregularverbs.ui.revision

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sero.com.irregularverbs.data.db.Verbs
import sero.com.irregularverbs.data.repository.LocalVerbsRepository
import java.time.LocalDateTime

class RevisionViewModel : ViewModel() {

    val verbsToRevise : LiveData<List<Verbs>> by lazy {
        LocalVerbsRepository.getVerbsToRevise()
    }

    val scheduledVerbs : LiveData<List<Verbs>> by lazy {
        LocalVerbsRepository.getScheduledVerbs()
    }

    fun updateVerb(randomVerb: Verbs) = viewModelScope.launch {
        val day = (randomVerb.day ?: 1).toLong()
        val updatedRandomVerb = randomVerb.copy(date = LocalDateTime.now().plusDays(day))
        LocalVerbsRepository.updateVerb(updatedRandomVerb)
    }
}