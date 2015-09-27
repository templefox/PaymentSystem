package ds.payment.transaction

import ds.payment.SpringContext
import ds.payment.affiliation.Affiliation
import ds.payment.affiliation.UnionAffiliation
import ds.payment.database.DB
import ds.payment.model.Employee
import groovy.transform.TupleConstructor
import org.springframework.context.ApplicationContext

/**
 * Created by Administrator on 2015/9/13.
 */
@TupleConstructor(includeSuperProperties = true)
class ChangeMemberTransaction extends ChangeAffiliationTransaction {
    int memberId
    float fee

    @Override
    protected void recordMembership(Employee employee) {
        ApplicationContext context = SpringContext.instance;
        DB db = context.getBean(DB);

        db.addUnionMember(memberId, employee)
    }

    @Override
    protected Affiliation getAffiliation() {
        new UnionAffiliation(memberId, fee)
    }
}
