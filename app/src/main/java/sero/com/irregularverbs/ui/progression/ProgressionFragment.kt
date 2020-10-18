package sero.com.irregularverbs.ui.progression

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_progression.*
import sero.com.irregularverbs.R

class ProgressionFragment : Fragment() {

    private val model: ProgressionViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_progression, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pie_chart.setUsePercentValues(true)
        pie_chart.holeRadius = 75f
        pie_chart.description.isEnabled = false
        pie_chart.dragDecelerationFrictionCoef = 0.98f
        pie_chart.setDrawEntryLabels(false)


        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(model.countByDay(1) / 170f, "1 jour"))
        entries.add(PieEntry(model.countByDay(2) / 170f, "2 jours"))
        entries.add(PieEntry(model.countByDay(3) / 170f, "3 jours"))
        entries.add(PieEntry(model.countByDay() / 170f, "null"))

        val set = PieDataSet(entries, "")
        set.colors = ColorTemplate.MATERIAL_COLORS.asList()
        set.selectionShift = 0f
        set.sliceSpace = 3f

        val data = PieData(set)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)

        pie_chart.data = data
    }
}
