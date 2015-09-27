package ds.payment.paymentmethod

import ds.payment.model.PayCheck

interface PaymentMethod {

    void pay(PayCheck payCheck)
}
