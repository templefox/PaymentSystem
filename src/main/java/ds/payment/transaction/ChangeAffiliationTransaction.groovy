package ds.payment.transaction

import ds.payment.affiliation.Affiliation
import ds.payment.model.Employee

/**
 * Created by Administrator on 2015/9/13.
 */
abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {

    @Override
    protected Employee Change(Employee employee) {
        recordMembership(employee)
        employee.affiliation = getAffiliation()
        employee
    }

    protected abstract Affiliation getAffiliation();

    protected abstract void recordMembership(Employee employee);
}
