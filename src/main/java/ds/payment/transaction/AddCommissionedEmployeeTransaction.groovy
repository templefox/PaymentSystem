package ds.payment.transaction

import ds.payment.classificaiton.CommissionedClassification
import ds.payment.classificaiton.PaymentClassification
import ds.payment.paymentmethod.HoldMethod
import ds.payment.paymentmethod.PaymentMethod
import ds.payment.schedule.BiweeklySchedule
import ds.payment.schedule.PaymentSchedule
import groovy.transform.TupleConstructor

@TupleConstructor(includeSuperProperties = true)
class AddCommissionedEmployeeTransaction extends AddEmployeeTransaction {
	float salary
	float commissionRate

	@Override
	protected PaymentMethod getPaymentMethod() {
		new HoldMethod()
	}

	@Override
	protected PaymentSchedule getSchedule() {
		new BiweeklySchedule()
	}

	@Override
	protected PaymentClassification getClassification() {
		new CommissionedClassification(salary, commissionRate)
	}


}
