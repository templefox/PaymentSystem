package ds.payment.transaction

import ds.payment.classificaiton.HourlyClassification
import ds.payment.classificaiton.PaymentClassification
import ds.payment.paymentmethod.HoldMethod
import ds.payment.paymentmethod.PaymentMethod
import ds.payment.schedule.PaymentSchedule
import ds.payment.schedule.WeeklySchedule
import groovy.transform.TupleConstructor
;

@TupleConstructor(includeSuperProperties = true)
class AddHourlyEmployeeTransaction extends AddEmployeeTransaction {
	float salary;

	
	@Override
	protected PaymentMethod getPaymentMethod() {
		new HoldMethod();
	}

	@Override
	protected PaymentSchedule getSchedule() {
		new WeeklySchedule();
	}

	@Override
	protected PaymentClassification getClassification() {
		new HourlyClassification(salary)
	}
}
