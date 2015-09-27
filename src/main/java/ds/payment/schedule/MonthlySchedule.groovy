package ds.payment.schedule

import ds.payment.Util

class MonthlySchedule implements PaymentSchedule {
    boolean isPayDay(Date date) {
        Util.isLastDayOfMonth(date)
    }

    @Override
    Date calculatePayPeriodStartDate(Date date) {
        if (isPayDay(date)) {
            Calendar c = Calendar.instance
            c.setTime(date)
            c.set(Calendar.DAY_OF_MONTH, 1)
            c.getTime()
        }
    }
}
