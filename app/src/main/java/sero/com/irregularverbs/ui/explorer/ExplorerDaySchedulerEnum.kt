package sero.com.irregularverbs.ui.explorer

import sero.com.irregularverbs.R

enum class ExplorerDaySchedulerEnum(
    val days: Int,
    val daySchedulerText: Int,
    val color: Int
) {
    FIRST_SCHEDULER(
        FIRST_SCHEDULER_NUMBER_OF_DAY,
        R.string.first_day_scheduler_text,
        R.color.red),
    SECOND_SCHEDULER(
        SECOND_SCHEDULER_NUMBER_OF_DAY,
        R.string.second_day_scheduler_text,
        R.color.orange),
    THIRD_SCHEDULER(
        THIRD_SCHEDULER_NUMBER_OF_DAY,
        R.string.third_day_scheduler_text,
        R.color.yellow),
    FOURTH_SCHEDULER(
        FOURTH_SCHEDULER_NUMBER_OF_DAY,
        R.string.fourth_day_scheduler_text,
        R.color.green)
}