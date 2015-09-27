package ds.payment

import groovy.time.TimeCategory

/**
 * Created by Administrator on 2015/9/19.
 */
class Util {
    static boolean isLastDayOfMonth(Date date) {
        dayEqual(date, getLastDayOfDate(date))
    }

    static boolean dayEqual(Date ldate, Date rdate) {
        0 == TimeCategory.minus(ldate, rdate).days
    }

    static Date getLastDayOfDate(Date date) {
        Calendar c = Calendar.instance
        c.setTime(date)
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH))
        c.getTime()
    }

    static boolean isFriday(Date date) {
        Calendar c = Calendar.instance
        c.setTime(date)
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        dayEqual(c.getTime(), date)

    }

    static boolean between(final Date date, Date ldate, Date rdate) {
        TimeCategory.minus(date, ldate).days >= 0 && TimeCategory.minus(rdate, date).days >= 0
    }
}
