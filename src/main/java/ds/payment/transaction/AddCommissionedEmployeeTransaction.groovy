package ds.payment.transaction

import ds.payment.classificaiton.PaymentClassification;
import ds.payment.paymentmethod.PaymentMethod;
import ds.payment.schedule.PaymentSchedule;

class AddCommissionedEmployeeTransaction extends AddEmployeeTransaction {

	@Override
	protected PaymentMethod GetPaymentMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PaymentSchedule GetSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PaymentClassification GetClassification() {
		// TODO Auto-generated method stub
		return null;
	}


}
