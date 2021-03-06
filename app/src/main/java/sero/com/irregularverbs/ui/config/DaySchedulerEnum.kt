package sero.com.irregularverbs.ui.config

import android.view.View
import sero.com.irregularverbs.R
import sero.com.irregularverbs.ui.explorer.FIRST_SCHEDULER_NUMBER_OF_DAY
import sero.com.irregularverbs.ui.explorer.FOURTH_SCHEDULER_NUMBER_OF_DAY
import sero.com.irregularverbs.ui.explorer.SECOND_SCHEDULER_NUMBER_OF_DAY
import sero.com.irregularverbs.ui.explorer.THIRD_SCHEDULER_NUMBER_OF_DAY

enum class DaySchedulerEnum(
    val days: Int?,
    val daySchedulerText: Int,
    val viewId: Int?,
    val color: Int
) {
    FIRST_SCHEDULER(
        FIRST_SCHEDULER_NUMBER_OF_DAY,
        R.string.first_day_scheduler_text,
        R.id.button_one,
        R.color.red),
    SECOND_SCHEDULER(
        SECOND_SCHEDULER_NUMBER_OF_DAY,
        R.string.second_day_scheduler_text,
        R.id.button_two,
        R.color.orange
    ),
    THIRD_SCHEDULER(
        THIRD_SCHEDULER_NUMBER_OF_DAY,
        R.string.third_day_scheduler_text,
        R.id.button_three,
        R.color.yellow
    ),
    FOURTH_SCHEDULER(
        FOURTH_SCHEDULER_NUMBER_OF_DAY,
        R.string.fourth_day_scheduler_text,
        R.id.button_four,
        R.color.blue
    ),
    NO_SCHEDULER(null, R.string.no_choice_day_scheduler_text, null, R.color.light_grey);

    companion object{
        fun getSchedulerFromView(view : View) =
            when(view.id){
                FIRST_SCHEDULER.viewId -> FIRST_SCHEDULER
                SECOND_SCHEDULER.viewId -> SECOND_SCHEDULER
                THIRD_SCHEDULER.viewId -> THIRD_SCHEDULER
                FOURTH_SCHEDULER.viewId -> FOURTH_SCHEDULER
                else -> NO_SCHEDULER
            }

    }
}