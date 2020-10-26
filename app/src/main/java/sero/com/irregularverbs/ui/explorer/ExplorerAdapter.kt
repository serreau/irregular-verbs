package sero.com.irregularverbs.ui.explorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_explorer_layout.view.*
import sero.com.irregularverbs.R
import sero.com.irregularverbs.data.db.Verbs
import sero.com.irregularverbs.ui.config.DaySchedulerEnum
import java.time.LocalDateTime

class ExplorerAdapter(
    private var verbs: MutableList<Verbs>,
    private val model: ExplorerViewModel
) : RecyclerView.Adapter<ExplorerAdapter.ExplorerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExplorerViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_explorer_layout, parent, false) as CardView
        return ExplorerViewHolder(item, model, this)
    }

    override fun onBindViewHolder(holder: ExplorerViewHolder, position: Int) =
        holder.initExplorerViewHolder(verbs[position])

    override fun getItemCount() = verbs.size

    class ExplorerViewHolder(
        private val layout: CardView,
        private val model: ExplorerViewModel,
        private val adapter: ExplorerAdapter
    ) : RecyclerView.ViewHolder(layout){

        fun  initExplorerViewHolder (verb : Verbs){

            layout.button_one.setOnClickListener { onSchedulerButtonClickListener(verb, layout.button_one) }
            layout.button_two.setOnClickListener { onSchedulerButtonClickListener(verb, layout.button_two) }
            layout.button_three.setOnClickListener { onSchedulerButtonClickListener(verb, layout.button_three) }
            layout.button_four.setOnClickListener { onSchedulerButtonClickListener(verb, layout.button_four) }

            layout.infinitive_textview.text = verb.infinitive
            layout.past_textview.text = verb.past
            layout.past_participle_textview.text = verb.pastParticiple
            layout.translation_textview.text = verb.frenchTranslation

            layout.button_one.text = layout.context.getString(DaySchedulerEnum.FIRST_SCHEDULER.daySchedulerText)
            layout.button_two.text = layout.context.getString(DaySchedulerEnum.SECOND_SCHEDULER.daySchedulerText)
            layout.button_three.text = layout.context.getString(DaySchedulerEnum.THIRD_SCHEDULER.daySchedulerText)
            layout.button_four.text = layout.context.getString(DaySchedulerEnum.FOURTH_SCHEDULER.daySchedulerText)

            layout.button_one.isChecked = verb.day == FIRST_SCHEDULER_NUMBER_OF_DAY
            layout.button_two.isChecked = verb.day == SECOND_SCHEDULER_NUMBER_OF_DAY
            layout.button_three.isChecked = verb.day == THIRD_SCHEDULER_NUMBER_OF_DAY
            layout.button_four.isChecked = verb.day == FOURTH_SCHEDULER_NUMBER_OF_DAY
        }

        private fun onSchedulerButtonClickListener(verb: Verbs, view: CheckBox){
            val updatedVerb = getUpdatedVerb(verb, view)
            model.updateVerb(updatedVerb)
            adapter.verbs.replaceAll { if(it.id == verb.id) updatedVerb else it }
            manageCheckboxes(view)
        }

        private fun getUpdatedVerb(verb: Verbs, view: CheckBox): Verbs =
            if(view.isChecked) verb.copy(day = DaySchedulerEnum.getSchedulerFromView(view).days, date = LocalDateTime.now())
            else verb.copy(day = null, date = null)

        private fun manageCheckboxes(v : View? = null){
            if(v != layout.button_one) layout.button_one.isChecked = false
            if(v != layout.button_two) layout.button_two.isChecked = false
            if(v != layout.button_three) layout.button_three.isChecked = false
            if(v != layout.button_four) layout.button_four.isChecked = false
        }
    }
}