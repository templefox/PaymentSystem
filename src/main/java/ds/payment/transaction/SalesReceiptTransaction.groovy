package ds.payment.transaction

import ds.payment.SpringContext
import ds.payment.classificaiton.CommissionedClassification
import ds.payment.database.DB
import ds.payment.model.Employee
import ds.payment.model.SalesReceipt
import groovy.transform.Canonical
import org.springframework.context.ApplicationContext

@Canonical
class SalesReceiptTransaction implements Transaction {
    int empid
    float amount
    Date date

    @Override
    public void execute() {
        ApplicationContext context = SpringContext.instance;
        DB db = context.getBean(DB)

        Employee e = db.getEmployee(empid)

        def cc = e.getPaymentClassification() as CommissionedClassification
        cc.addSalesReceipt(new SalesReceipt(amount, date))
    }

}
