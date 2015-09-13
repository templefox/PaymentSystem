package ds.payment.affiliation

import ds.payment.model.ServiceCharge
import groovy.transform.Canonical

@Canonical
class UnionAffiliation implements Affiliation {
    int memberId
    double fee

    def serviceCharges = []

    public void AddServiceCharge(ServiceCharge sc) {
        serviceCharges << sc
    }
}
