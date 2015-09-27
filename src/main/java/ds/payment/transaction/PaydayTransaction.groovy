package ds.payment.transaction

import ds.payment.SpringContext
import ds.payment.database.DB
import ds.payment.model.PayCheck
import groovy.transform.TupleConstructor
import org.springframework.context.ApplicationContext

/**
 * Created by Administrator on 2015/9/13.
 */
@TupleConstructor
class PaydayTransaction implements Transaction {
    Date date
    private def paychecks = [:]

    @Override
    void execute() {
        ApplicationContext context = SpringContext.instance;
        DB db = context.getBean(DB);

        def employees = db.getAllEmployees()
        employees.each {
            if (it.isPayDate(date)) {
                PayCheck pc = new PayCheck(payDate: date, payPeriodStartDate: it.getPayPeriodStartDate(date))
                paychecks[it.id] = pc
                it.PayDay(pc)
            }
        }
    }

    public PayCheck getPayCheck(int empId) {
        paychecks[empId]
    }
}
