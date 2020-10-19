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
            layout.button_one.isChecked = getStateFromVerb(verb, layout.button_one)
            layout.button_two.isChecked = getStateFromVerb(verb, layout.button_two)
            layout.button_three.isChecked = getStateFromVerb(verb, layout.button_three)
            layout.button_four.isChecked = getStateFromVerb(verb, layout.button_four)
        }

        private fun onSchedulerButtonClickListener(verb: Verbs, view: CheckBox){
            val updatedVerb = getUpdatedVerb(verb, view)
            model.updateVerb(updatedVerb)
            manageCheckboxes(view)
        }

        private fun getUpdatedVerb(verb: Verbs, view: CheckBox): Verbs =
            if(view.isChecked) verb.copy(day = DaySchedulerEnum.getSchedulerFromView(view).days, date = LocalDateTime.now())
            else verb.copy(day = null, date = null)

        private fun getStateFromVerb(verb : Verbs, view : CheckBox): Boolean =
            DaySchedulerEnum.getSchedulerFromView(view).days == verb.day

        private fun manageCheckboxes(v : View? = null){
            if(v != layout.button_one) layout.button_one.isChecked = false
            if(v != layout.button_two) layout.button_two.isChecked = false
            if(v != layout.button_three) layout.button_three.isChecked = false
            if(v != layout.button_four) layout.button_four.isChecked = false
        }
    }
}