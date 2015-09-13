package ds.payment.transaction

import ds.payment.SpringContext
import ds.payment.affiliation.Affiliation
import ds.payment.affiliation.UnionAffiliation
import ds.payment.database.DB
import ds.payment.model.Employee
import org.springframework.context.ApplicationContext

/**
 * Created by Administrator on 2015/9/13.
 */
class ChangeUnffiliatedTransaction extends ChangeAffiliationTransaction {
    @Override
    protected void recordMembership(Employee employee) {
        ApplicationContext context = SpringContext.instance;
        DB db = context.getBean(DB);

        if (employee.affiliation instanceof UnionAffiliation) {
            db.deleteUnionMember((employee.affiliation as UnionAffiliation).memberId)
        }
    }

    @Override
    protected Affiliation getAffiliation() {
        return null
    }
}
