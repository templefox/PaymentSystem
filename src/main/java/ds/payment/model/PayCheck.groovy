package ds.payment.model

/**
 * Created by Administrator on 2015/9/19.
 */
class PayCheck {
    Date payDate
    float grossPay
    float netPay
    float deductions
    Date payPeriodStartDate

    public Date getPayPeriodEndDate() {
        payDate
    }

    public String getField(String disposition) {
        "Hold"
    }
}
