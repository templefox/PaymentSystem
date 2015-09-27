package ds.payment.schedule

import ds.payment.Util

/**
 * Created by Administrator on 2015/9/13.
 */
class WeeklySchedule implements PaymentSchedule {
    @Override
    boolean isPayDay(Date date) {
        Util.isFriday(date)
    }

    @Override
    Date calculatePayPeriodStartDate(Date date) {
        if (isPayDay(date)) {
            Calendar c = Calendar.instance
            c.setTime(date)
            c.add(Calendar.WEEK_OF_MONTH, -1)
            c.getTime()
        }
    }
}
