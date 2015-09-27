package ds.payment

import groovy.time.TimeCategory
import spock.lang.Specification

/**
 * Created by Administrator on 2015/9/19.
 */
class UtilTest extends Specification {
    void testIsLastDayOfMonth() {
        expect:
        Util.isLastDayOfMonth(new Date().parse("yyyy.MM.dd", "2015.9.30"))
    }

    void testGetLastDayOfDate() {
        expect:
        0 == TimeCategory.minus(Util.getLastDayOfDate(new Date()), new Date().parse("yyyy.MM.dd", "2015.9.30")).days
    }

    void testDayequal() {
        expect:
        Util.dayEqual(new Date(), new Date())
    }

    void testIsFriday() {
        expect:
        Util.isFriday(new Date().parse("yyyy.MM.dd", "2015.9.18"))
        !Util.isFriday(new Date().parse("yyyy.MM.dd", "2015.9.16"))
    }
}
