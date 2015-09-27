package ds.payment.affiliation

import ds.payment.model.PayCheck
import ds.payment.model.ServiceCharge
import groovy.time.TimeCategory
import groovy.transform.Canonical

@Canonical
class UnionAffiliation implements Affiliation {
    int memberId
    float fee

    def serviceCharges = []

    public void AddServiceCharge(ServiceCharge sc) {
        serviceCharges << sc
    }

    @Override
    float calculateDeductions(PayCheck payCheck) {
        numberOfFridayBetween(payCheck.payPeriodStartDate, payCheck.payPeriodEndDate) * fee
    }

    private int numberOfFridayBetween(Date start, Date end) {
        int fridays = 0
        for (Date d = start; TimeCategory.minus(d, end).days < 0; d++) {
            Calendar c = Calendar.instance
            c.setTime(d)
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                fridays++
        }
        fridays
    }
}
