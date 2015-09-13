package ds.payment.transaction

import ds.payment.model.Employee
import ds.payment.paymentmethod.PaymentMethod

/**
 * Created by Administrator on 2015/9/13.
 */
abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {
    private Employee employee

    @Override
    protected Employee Change(Employee employee) {
        this.employee = employee
        employee.method = getPaymentMethod()
        employee
    }

    protected PaymentMethod getPaymentMethod() {
        employee.method
    }
}
