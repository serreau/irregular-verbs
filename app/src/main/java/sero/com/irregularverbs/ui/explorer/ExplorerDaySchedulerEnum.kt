package sero.com.irregularverbs.ui.explorer

import android.view.View
import sero.com.irregularverbs.R

enum class ExplorerDaySchedulerEnum(
    val days: Int,
    val daySchedulerText: Int,
    val viewId: Int
) {
    FIRST_SCHEDULER(
        FIRST_SCHEDULER_NUMBER_OF_DAY,
        R.string.first_day_scheduler_text,
        R.id.button_one),
    SECOND_SCHEDULER(
        SECOND_SCHEDULER_NUMBER_OF_DAY,
        R.string.second_day_scheduler_text,
        R.id.button_two
    ),
    THIRD_SCHEDULER(
        THIRD_SCHEDULER_NUMBER_OF_DAY,
        R.string.third_day_scheduler_text,
        R.id.button_three
    ),
    FOURTH_SCHEDULER(
        FOURTH_SCHEDULER_NUMBER_OF_DAY,
        R.string.fourth_day_scheduler_text,
        R.id.button_four
    ),
    NO_SCHEDULER(-1, -1, -1);

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