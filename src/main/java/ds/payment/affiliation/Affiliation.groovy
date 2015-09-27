package ds.payment.affiliation

import ds.payment.model.PayCheck

interface Affiliation {

    float calculateDeductions(PayCheck payCheck)
}
