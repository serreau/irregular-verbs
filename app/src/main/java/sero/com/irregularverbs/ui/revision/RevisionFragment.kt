package sero.com.irregularverbs.ui.revision

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
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
    private var noEndingModeActivated = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_revision, container, false).apply { setHasOptionsMenu(true) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_revision_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(noEndingModeActivated) noEndingOptionDisable(item)
        else noEndingOptionEnable(item)
        return super.onOptionsItemSelected(item)
    }

    private fun noEndingOptionDisable(item: MenuItem) {
        noEndingModeActivated = !noEndingModeActivated
        val noEndingIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_pool_24)
        item.icon = noEndingIcon

        model.scheduledVerbs.removeObservers(this.viewLifecycleOwner)
        item_revision_parent.removeAllViewsInLayout()
        model.verbsToRevise.observe(this.viewLifecycleOwner, Observer {
            popRandomCard(it)
        })
    }

    private fun noEndingOptionEnable(item: MenuItem) {
        noEndingModeActivated = !noEndingModeActivated
        val noEndingIconActivated = ContextCompat.getDrawable(requireContext(), R.drawable.no_ending_icon)
        item.icon = noEndingIconActivated

        model.verbsToRevise.removeObservers(this.viewLifecycleOwner)
        item_revision_parent.removeAllViewsInLayout()
        model.scheduledVerbs.observe(this.viewLifecycleOwner, Observer {
            popRandomCard(it)
        })
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
        card.back_cardview_container.setOnClickListener { nextCard(verb) }
    }

    private fun initCard() {
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
        if(item_revision_parent.childCount == 0) // TO REMOVE
            item_revision_parent.addView(card)
    }

    private fun onSchedulerButtonClickListener(checkbox: CheckBox, daySchedulerEnum: DaySchedulerEnum) {
        if(!checkbox.isChecked)
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.revision_fragment_remove_scheduler_title))
                .setMessage(resources.getString(R.string.revision_fragment_remove_scheduler_message))
                .setNegativeButton(resources.getString(R.string.revision_fragment_remove_scheduler_cancel)) {_, _ -> checkbox.isChecked = true }
                .setOnDismissListener { checkbox.isChecked = true }
                .setPositiveButton(resources.getString(R.string.revision_fragment_remove_scheduler_accept)) { _, _ -> nextCard(verb.copy(day = null, date = null)) }
                .show()
        else {
            nextCard(verb.copy(day = daySchedulerEnum.days, date = LocalDateTime.now()))
        }
        manageCheckboxes(checkbox)
    }

    private fun nextCard(verb: Verbs) {
        val animation = animateCard(-item_revision_parent.width.toFloat())
        animation?.doOnEnd {
            item_revision_parent.removeAllViewsInLayout()
            model.updateVerb(verb)
        }
        animation?.start()
    }

    private fun animateCard(target : Float): ObjectAnimator? =
        ObjectAnimator.ofFloat(item_revision_parent, "translationX", target).apply {
            duration = CARD_TRANSLATION_DURATION
            start()
        }

    private fun manageCheckboxes(checkbox : View? = null){
        if(checkbox != card.revision_button_one) card.revision_button_one.isChecked = false
        if(checkbox != card.revision_button_two) card.revision_button_two.isChecked = false
        if(checkbox != card.revision_button_three) card.revision_button_three.isChecked = false
        if(checkbox != card.revision_button_four) card.revision_button_four.isChecked = false
    }

}


