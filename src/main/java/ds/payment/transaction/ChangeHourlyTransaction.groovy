package ds.payment.transaction

import ds.payment.classificaiton.HourlyClassification
import ds.payment.classificaiton.PaymentClassification
import ds.payment.schedule.PaymentSchedule
import ds.payment.schedule.WeeklySchedule
import groovy.transform.TupleConstructor

/**
 * Created by Administrator on 2015/9/13.
 */
@TupleConstructor(includeSuperProperties = true)
class ChangeHourlyTransaction extends ChangeClassificationTransaction {
    float rate

    @Override
    protected PaymentClassification getPaymentClassification() {
        new HourlyClassification(rate)
    }

    @Override
    protected PaymentSchedule getSchedule() {
        new WeeklySchedule()
    }
}
