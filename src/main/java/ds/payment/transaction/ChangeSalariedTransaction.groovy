package ds.payment.transaction

import ds.payment.classificaiton.PaymentClassification
import ds.payment.classificaiton.SalariedClassification
import ds.payment.schedule.MonthlySchedule
import ds.payment.schedule.PaymentSchedule
import groovy.transform.TupleConstructor

/**
 * Created by Administrator on 2015/9/13.
 */
@TupleConstructor(includeSuperProperties = true)
class ChangeSalariedTransaction extends ChangeClassificationTransaction {
    float salary

    @Override
    protected PaymentSchedule getSchedule() {
        new MonthlySchedule()
    }

    @Override
    protected PaymentClassification getPaymentClassification() {
        new SalariedClassification(salary)
    }
}
