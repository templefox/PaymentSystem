package ds.payment.schedule

/**
 * Created by Administrator on 2015/9/13.
 */
class BiweeklySchedule implements PaymentSchedule {
    @Override
    boolean isPayDay(Date date) {
        return false
    }

    @Override
    Date calculatePayPeriodStartDate(Date date) {
        if (isPayDay(date)) {
            Calendar c = Calendar.instance
            c.setTime(date)
            c.add(Calendar.WEEK_OF_MONTH, -2)
            c.getTime()
        }
    }
}
