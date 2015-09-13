package ds.payment.transaction

import ds.payment.SpringContext
import ds.payment.database.DB
import ds.payment.model.Employee
import org.springframework.context.ApplicationContext

/**
 * Created by Administrator on 2015/9/13.
 */
abstract class ChangeEmployeeTransaction implements Transaction {
    int empId

    @Override
    public void execute() {
        ApplicationContext context = SpringContext.instance;
        DB db = context.getBean(DB);

        def e = db.getEmployee(empId)
        if (e) {
            db.addEmployee(Change(e), true)
        }
    }

    abstract protected Employee Change(Employee employee)
}
