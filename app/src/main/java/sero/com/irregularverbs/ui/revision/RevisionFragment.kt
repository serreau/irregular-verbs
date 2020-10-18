package sero.com.irregularverbs.ui.revision

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_revision.*
import kotlinx.android.synthetic.main.item_revision_back_cardview.view.*
import kotlinx.android.synthetic.main.item_revision_front_cardview.view.*
import sero.com.irregularverbs.R
import sero.com.irregularverbs.data.db.Verbs

class RevisionFragment : Fragment() {

    private val model: RevisionViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_revision, container, false)
    }

    override fun onStart() {
        super.onStart()
        model.verbsToRevise.observe(this.viewLifecycleOwner, Observer {
            if(it.isEmpty()) return@Observer

            val randomVerb = it.random()
            val card = initCard(randomVerb)

            animateCard(0f)

            card.back_cardview.setOnClickListener {
                animateCard(-item_revision_parent.width.toFloat())
                    ?.doOnEnd {
                        item_revision_parent.removeAllViewsInLayout()
                        model.updateVerb(randomVerb)
                    }
            }
        })
    }

    private fun animateCard(target : Float): ObjectAnimator? =
        ObjectAnimator.ofFloat(item_revision_parent, "translationX", target).apply {
            duration = CARD_TRANSLATION_DURATION
            start()
        }


    private fun initCard(verb : Verbs) : View {
        val card = layoutInflater.inflate(R.layout.item_revision_layout, item_revision_parent, false)

        card.front_translation_revision_text.text = verb.frenchTranslation
        card.infinitive_revision_text.text = verb.infinitive
        card.past_revision_text.text = verb.infinitive
        card.past_participle_revision_text.text = verb.infinitive
        card.back_translation_revision_text.text = verb.infinitive

        item_revision_parent.translationX = item_revision_parent.width.toFloat()
        item_revision_parent.addView(card)

        return card
    }


}


