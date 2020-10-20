package sero.com.irregularverbs.ui.progression

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate.rgb
import kotlinx.android.synthetic.main.fragment_progression.*
import sero.com.irregularverbs.R
import sero.com.irregularverbs.ui.config.DaySchedulerEnum

class ProgressionFragment : Fragment() {

    private val model: ProgressionViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_progression, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pie_chart.setUsePercentValues(true)
        pie_chart.holeRadius = 60f
        pie_chart.description.isEnabled = false
        pie_chart.setDrawEntryLabels(false)
        pie_chart.setHoleColor(rgb(resources.getString(DaySchedulerEnum.NO_SCHEDULER.color)))
        pie_chart.setTouchEnabled(false)
        pie_chart.legend.isEnabled = false

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(model.countByDay(1) / 170f, getString(DaySchedulerEnum.FIRST_SCHEDULER.daySchedulerText)))
        entries.add(PieEntry(model.countByDay(3) / 170f, getString(DaySchedulerEnum.SECOND_SCHEDULER.daySchedulerText)))
        entries.add(PieEntry(model.countByDay(10) / 170f, getString(DaySchedulerEnum.THIRD_SCHEDULER.daySchedulerText)))
        entries.add(PieEntry(model.countByDay(20) / 170f, getString(DaySchedulerEnum.FOURTH_SCHEDULER.daySchedulerText)))
        entries.add(PieEntry(model.countByDay() / 170f, ""))

        val set = PieDataSet(entries, "")
        set.colors = listOf(
            rgb(resources.getString(DaySchedulerEnum.FIRST_SCHEDULER.color)),
            rgb(resources.getString(DaySchedulerEnum.SECOND_SCHEDULER.color)),
            rgb(resources.getString(DaySchedulerEnum.THIRD_SCHEDULER.color)),
            rgb(resources.getString(DaySchedulerEnum.FOURTH_SCHEDULER.color)),
            rgb(resources.getString(DaySchedulerEnum.NO_SCHEDULER.color)))
        set.selectionShift = 0f
        set.setDrawValues(false)

        val data = PieData(set)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)

        pie_chart.data = data
    }
}
