package ds.payment.transaction

import ds.payment.model.Employee
import groovy.transform.TupleConstructor

/**
 * Created by Administrator on 2015/9/13.
 */
@TupleConstructor(includeSuperProperties = true)
class ChangeNameTransaction extends ChangeEmployeeTransaction {
    String newName

    @Override
    public Employee Change(Employee employee) {
        employee.name = newName
        employee
    }
}
