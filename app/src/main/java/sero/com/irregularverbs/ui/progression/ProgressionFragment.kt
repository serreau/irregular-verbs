package sero.com.irregularverbs.ui.progression

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate.rgb
import kotlinx.android.synthetic.main.fragment_progression.*
import sero.com.irregularverbs.R
import sero.com.irregularverbs.ui.config.DaySchedulerEnum.*

class ProgressionFragment : Fragment() {

    private val model: ProgressionViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_progression, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPieChart()
        initLegend()
    }

    private fun initLegend() {
        legend_first_scheduler.text = getString(FIRST_SCHEDULER.daySchedulerText)
        legend_second_scheduler.text = getString(SECOND_SCHEDULER.daySchedulerText)
        legend_third_scheduler.text = getString(THIRD_SCHEDULER.daySchedulerText)
        legend_fourth_scheduler.text = getString(R.string.progression_last_level_legend_text)
    }

    private fun initPieChart() {
        model.allVerbs.observe(viewLifecycleOwner, Observer { it ->
            with(pie_chart) {
                setUsePercentValues(true)
                holeRadius = 60f
                description.isEnabled = false
                setDrawEntryLabels(false)
                setHoleColor(rgb( resources.getColor(R.color.white, context.theme).toString()))
                setTouchEnabled(false)
                legend.isEnabled = false
            }

            val count = it.count()
            val entries = ArrayList<PieEntry>().apply {
                add(PieEntry(it.count { it.day == FOURTH_SCHEDULER.days }.toFloat() / count, getString(FOURTH_SCHEDULER.daySchedulerText)))
                add(PieEntry(it.count { it.day == THIRD_SCHEDULER.days }.toFloat() / count, getString(THIRD_SCHEDULER.daySchedulerText)))
                add(PieEntry(it.count { it.day == SECOND_SCHEDULER.days }.toFloat() / count, getString(SECOND_SCHEDULER.daySchedulerText)))
                add(PieEntry(it.count { it.day == FIRST_SCHEDULER.days }.toFloat() / count, getString(FIRST_SCHEDULER.daySchedulerText)))
                add(PieEntry(it.count { it.day == NO_SCHEDULER.days }.toFloat() / count, ""))
            }

            val set = PieDataSet(entries, "")

            set.colors = listOf(
                rgb(getString(FOURTH_SCHEDULER.color)),
                rgb(getString(THIRD_SCHEDULER.color)),
                rgb(getString(SECOND_SCHEDULER.color)),
                rgb(getString(FIRST_SCHEDULER.color)),
                rgb(getString(NO_SCHEDULER.color)))
            set.selectionShift = 0f
            set.setDrawValues(false)

            val data = PieData(set)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(11f)

            pie_chart.data = data
            pie_chart.invalidate()
        })


        model.countAll.observe(viewLifecycleOwner, Observer {
            progression_fraction.text = getString(R.string.progression_fragment_fraction, it)
        })
    }
}
