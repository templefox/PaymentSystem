package ds.payment.classificaiton

import ds.payment.model.PayCheck

class SalariedClassification implements PaymentClassification {
	Float salary
	
	SalariedClassification(Float salary){
		this.salary = salary
	}

	@Override
	float calculatePay(PayCheck payCheck) {
		salary
	}
}
