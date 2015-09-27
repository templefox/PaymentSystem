package ds.payment.affiliation

import ds.payment.model.PayCheck

class NonAffiliation implements Affiliation {

    @Override
    float calculateDeductions(PayCheck payCheck) {
        return 0
    }
}
