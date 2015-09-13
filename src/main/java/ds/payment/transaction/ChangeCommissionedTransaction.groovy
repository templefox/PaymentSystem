package ds.payment.transaction

import ds.payment.classificaiton.CommissionedClassification
import ds.payment.classificaiton.PaymentClassification
import ds.payment.schedule.BiweeklySchedule
import ds.payment.schedule.PaymentSchedule
import groovy.transform.TupleConstructor

/**
 * Created by Administrator on 2015/9/13.
 */
@TupleConstructor
class ChangeCommissionedTransaction extends ChangeClassificationTransaction {
    float salary
    float commissionRate

    @Override
    protected PaymentSchedule getSchedule() {
        new BiweeklySchedule()
    }

    @Override
    protected PaymentClassification getPaymentClassification() {
        new CommissionedClassification(salary, commissionRate)
    }
}
