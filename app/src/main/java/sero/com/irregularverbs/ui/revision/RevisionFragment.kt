package sero.com.irregularverbs.ui.revision

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_revision.*
import kotlinx.android.synthetic.main.item_revision_back_cardview.view.*
import kotlinx.android.synthetic.main.item_revision_front_cardview.view.*
import sero.com.irregularverbs.R
import sero.com.irregularverbs.data.db.Verbs
import sero.com.irregularverbs.ui.config.DaySchedulerEnum
import sero.com.irregularverbs.ui.config.DaySchedulerEnum.*
import java.time.LocalDateTime

class RevisionFragment : Fragment() {

    private val model: RevisionViewModel by viewModels()
    private lateinit var verb : Verbs
    private lateinit var card : View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_revision, container, false).apply { setHasOptionsMenu(true) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_revision_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        model.scheduledVerbs.observe(viewLifecycleOwner, Observer {
            popRandomCard(it)
        })
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        model.verbsToRevise.observe(this.viewLifecycleOwner, Observer {
            popRandomCard(it)
        })
    }

    private fun popRandomCard(it: List<Verbs>) {
        if (it.isEmpty()) return

        verb = it.random()
        card = layoutInflater.inflate(R.layout.item_revision_layout, item_revision_parent, false)
        initCard()

        animateCard(0f)

        card.back_cardview_container.setOnClickListener {
            animateCard(-item_revision_parent.width.toFloat())
                ?.doOnEnd {
                    item_revision_parent.removeAllViewsInLayout()
                    model.updateVerb(verb)
                }
        }
    }

    private fun animateCard(target : Float): ObjectAnimator? =
        ObjectAnimator.ofFloat(item_revision_parent, "translationX", target).apply {
            duration = CARD_TRANSLATION_DURATION
            start()
        }


    private fun initCard() : View {
        card.front_translation_revision_text.text = verb.frenchTranslation
        card.infinitive_revision_text.text = verb.infinitive
        card.past_revision_text.text = verb.past
        card.past_participle_revision_text.text = verb.pastParticiple
        card.back_translation_revision_text.text = verb.frenchTranslation

        card.revision_button_one.text = getString(FIRST_SCHEDULER.daySchedulerText)
        card.revision_button_two.text = getString(SECOND_SCHEDULER.daySchedulerText)
        card.revision_button_three.text = getString(THIRD_SCHEDULER.daySchedulerText)
        card.revision_button_four.text = getString(FOURTH_SCHEDULER.daySchedulerText)

        card.revision_button_one.isChecked = verb.day == FIRST_SCHEDULER.days
        card.revision_button_two.isChecked = verb.day == SECOND_SCHEDULER.days
        card.revision_button_three.isChecked = verb.day == THIRD_SCHEDULER.days
        card.revision_button_four.isChecked = verb.day == FOURTH_SCHEDULER.days

        card.revision_button_one.setOnClickListener { onSchedulerButtonClickListener(card.revision_button_one, FIRST_SCHEDULER) }
        card.revision_button_two.setOnClickListener { onSchedulerButtonClickListener(card.revision_button_two, SECOND_SCHEDULER) }
        card.revision_button_three.setOnClickListener { onSchedulerButtonClickListener(card.revision_button_three, THIRD_SCHEDULER) }
        card.revision_button_four.setOnClickListener { onSchedulerButtonClickListener(card.revision_button_four, FOURTH_SCHEDULER) }

        item_revision_parent.translationX = item_revision_parent.width.toFloat()
        item_revision_parent.addView(card)

        return card
    }

    private fun onSchedulerButtonClickListener(checkbox: CheckBox, daySchedulerEnum: DaySchedulerEnum) {
        if(!checkbox.isChecked)
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.revision_fragment_remove_scheduler_title))
                .setMessage(resources.getString(R.string.revision_fragment_remove_scheduler_message))
                .setNegativeButton(resources.getString(R.string.revision_fragment_remove_scheduler_cancel)) {dialog, which ->
                    checkbox.isChecked = true
                    manageCheckboxes(checkbox)
                }
                .setOnDismissListener {
                    checkbox.isChecked = true
                    manageCheckboxes(checkbox)
                }
                .setPositiveButton(resources.getString(R.string.revision_fragment_remove_scheduler_accept)) { dialog, which ->
                    nextCard(verb.copy(day = null, date = null), checkbox)
                }
                .show()
        else {
            nextCard(verb.copy(day = daySchedulerEnum.days, date = LocalDateTime.now()), checkbox)
        }
    }

    private fun nextCard(verb: Verbs, checkbox: CheckBox) {
        manageCheckboxes(checkbox)
        val animation = animateCard(-item_revision_parent.width.toFloat())
        animation?.doOnEnd {
            item_revision_parent.removeAllViewsInLayout()
            model.updateVerb(verb)
        }
        animation?.start()
    }

    private fun manageCheckboxes(checkbox : View? = null){
        if(checkbox != card.revision_button_one) card.revision_button_one.isChecked = false
        if(checkbox != card.revision_button_two) card.revision_button_two.isChecked = false
        if(checkbox != card.revision_button_three) card.revision_button_three.isChecked = false
        if(checkbox != card.revision_button_four) card.revision_button_four.isChecked = false
    }

}


