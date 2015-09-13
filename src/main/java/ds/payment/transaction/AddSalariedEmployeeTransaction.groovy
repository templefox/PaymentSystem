package ds.payment.transaction

import ds.payment.classificaiton.PaymentClassification
import ds.payment.classificaiton.SalariedClassification
import ds.payment.paymentmethod.HoldMethod
import ds.payment.paymentmethod.PaymentMethod
import ds.payment.schedule.MonthlySchedule
import ds.payment.schedule.PaymentSchedule
import groovy.transform.TupleConstructor

@TupleConstructor(includeSuperProperties = true)
class AddSalariedEmployeeTransaction extends AddEmployeeTransaction {
	float salary;

	@Override
	protected PaymentMethod getPaymentMethod() {
		new HoldMethod()
	}

	@Override
	protected PaymentSchedule getSchedule() {
		new MonthlySchedule();
	}

	@Override
	protected PaymentClassification getClassification() {
		new SalariedClassification(salary);
	}
}
