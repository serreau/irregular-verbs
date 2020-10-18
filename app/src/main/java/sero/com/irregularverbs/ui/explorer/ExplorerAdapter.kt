package sero.com.irregularverbs.ui.explorer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_explorer_layout.view.*
import kotlinx.android.synthetic.main.item_explorer_layout.view.button_four
import kotlinx.android.synthetic.main.item_explorer_layout.view.button_one
import kotlinx.android.synthetic.main.item_explorer_layout.view.button_three
import kotlinx.android.synthetic.main.item_explorer_layout.view.button_two
import sero.com.irregularverbs.R
import sero.com.irregularverbs.data.db.Verbs
import java.time.LocalDateTime

class ExplorerAdapter(
    private val myDataset: List<Verbs>,
    private val model: ExplorerViewModel
) : RecyclerView.Adapter<ExplorerAdapter.ExplorerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExplorerViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_explorer_layout, parent, false) as CardView
        return ExplorerViewHolder(item, model)
    }

    override fun onBindViewHolder(holder: ExplorerViewHolder, position: Int) =
        holder.initExplorerViewHolder(myDataset[position])

    override fun getItemCount() = myDataset.size

    class ExplorerViewHolder(
        private val layout: CardView,
        private val model: ExplorerViewModel
    ) : RecyclerView.ViewHolder(layout){

        fun  initExplorerViewHolder (verb : Verbs){
            layout.button_one.setOnClickListener {
                val updatedVerb = verb.copy(day = ExplorerDaySchedulerEnum.FIRST_SCHEDULER.days, date = LocalDateTime.now())
                model.updateVerb(updatedVerb)
            }
            layout.button_two.setOnClickListener {
                val updatedVerb = verb.copy(day = ExplorerDaySchedulerEnum.SECOND_SCHEDULER.days, date = LocalDateTime.now())
                model.updateVerb(updatedVerb)
            }
            layout.button_three.setOnClickListener {
                val updatedVerb = verb.copy(day = ExplorerDaySchedulerEnum.THIRD_SCHEDULER.days, date = LocalDateTime.now())
                model.updateVerb(updatedVerb)
            }
            layout.button_four.setOnClickListener {
                val updatedVerb = verb.copy(day = ExplorerDaySchedulerEnum.FOURTH_SCHEDULER.days, date = LocalDateTime.now())
                model.updateVerb(updatedVerb)
            }

            layout.infinitive_textview.text = verb.infinitive
            layout.past_textview.text = verb.past
            layout.past_participle_textview.text = verb.pastParticiple
            layout.translation_textview.text = verb.frenchTranslation

            layout.button_one.text = layout.context.getString(ExplorerDaySchedulerEnum.FIRST_SCHEDULER.daySchedulerText)
            layout.button_two.text = layout.context.getString(ExplorerDaySchedulerEnum.SECOND_SCHEDULER.daySchedulerText)
            layout.button_three.text = layout.context.getString(ExplorerDaySchedulerEnum.THIRD_SCHEDULER.daySchedulerText)
            layout.button_four.text = layout.context.getString(ExplorerDaySchedulerEnum.FOURTH_SCHEDULER.daySchedulerText)
        }
    }
}