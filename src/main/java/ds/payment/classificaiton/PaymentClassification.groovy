package ds.payment.classificaiton

import ds.payment.Util
import ds.payment.model.PayCheck

trait PaymentClassification {

    abstract float calculatePay(PayCheck payCheck)

    boolean isInPayPeriod(Date date, PayCheck payCheck) {
        Util.between(date, payCheck.payPeriodStartDate, payCheck.payPeriodEndDate)
    }
}
