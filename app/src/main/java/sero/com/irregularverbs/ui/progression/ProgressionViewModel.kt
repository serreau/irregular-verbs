package sero.com.irregularverbs.ui.progression

import androidx.lifecycle.ViewModel
import sero.com.irregularverbs.data.repository.LocalVerbsRepository

class ProgressionViewModel : ViewModel() {

    fun countByDay(day: Int): Float = LocalVerbsRepository.countByDay(day).toFloat()

    fun countByDay(): Float = LocalVerbsRepository.countByDay().toFloat()
}