package ds.payment.transaction

import ds.payment.classificaiton.PaymentClassification
import ds.payment.model.Employee
import ds.payment.schedule.PaymentSchedule

/**
 * Created by Administrator on 2015/9/13.
 */

abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {
    private Employee employee

    @Override
    protected Employee Change(Employee employee) {
        this.employee = employee
        employee.paymentClassification = getPaymentClassification()
        employee.schedule = getSchedule()
        employee
    }

    protected PaymentClassification getPaymentClassification() {
        employee.paymentClassification
    }

    protected PaymentSchedule getSchedule() {
        employee.schedule
    }
}
