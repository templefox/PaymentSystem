package ds.payment.transaction

import ds.payment.SpringContext
import ds.payment.affiliation.UnionAffiliation
import ds.payment.database.DB
import ds.payment.model.Employee
import ds.payment.model.ServiceCharge
import groovy.transform.Canonical
import org.springframework.context.ApplicationContext

@Canonical
public class ServiceChargeTransaction implements Transaction {
    int memberId
    Date date
    float fee

    @Override
    public void execute() {
        ApplicationContext context = SpringContext.instance;
        DB db = context.getBean(DB)

        Employee e = db.getAffiliationMember(memberId)

        UnionAffiliation ua = e.affiliation as UnionAffiliation

        ServiceCharge sc = new ServiceCharge(date, fee)

        ua.AddServiceCharge(sc)
    }

}
