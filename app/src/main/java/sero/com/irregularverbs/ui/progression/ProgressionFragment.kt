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
import sero.com.irregularverbs.ui.config.DaySchedulerEnum
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
        legend_fourth_scheduler.text = getString(FOURTH_SCHEDULER.daySchedulerText)
    }

    private fun initPieChart() {
        with(pie_chart) {
            setUsePercentValues(true)
            holeRadius = 60f
            description.isEnabled = false
            setDrawEntryLabels(false)
            setHoleColor(rgb( resources.getColor(R.color.white, context.theme).toString()))
            setTouchEnabled(false)
            legend.isEnabled = false
        }


        val entries = ArrayList<PieEntry>()
        with(entries){
            add(PieEntry(model.countByDay(20) / 170f, getString(FOURTH_SCHEDULER.daySchedulerText)))
            add(PieEntry(model.countByDay(10) / 170f, getString(THIRD_SCHEDULER.daySchedulerText)))
            add(PieEntry(model.countByDay(3) / 170f, getString(SECOND_SCHEDULER.daySchedulerText)))
            add(PieEntry(model.countByDay(1) / 170f, getString(FIRST_SCHEDULER.daySchedulerText)))
            add(PieEntry(model.countByDay() / 170f, ""))
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

        progression_fraction.text = getString(R.string.progression_fragment_fraction, model.countAll().toInt())
    }
}
