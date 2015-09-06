package ds.payment.transaction

import ds.payment.classificaiton.HourlyClassification
import ds.payment.classificaiton.PaymentClassification;
import ds.payment.paymentmethod.HoldMethod
import ds.payment.paymentmethod.PaymentMethod;
import ds.payment.schedule.MonthlySechedule
import ds.payment.schedule.PaymentSchedule;

class AddHourlyEmployeeTransaction extends AddEmployeeTransaction {
	float salary;
	
	AddHourlyEmployeeTransaction(int id,String name,String address,float salary){
		super(id,name,address);
		this.salary = salary;	
	}
	
	@Override
	protected PaymentMethod GetPaymentMethod() {
		new HoldMethod();
	}

	@Override
	protected PaymentSchedule GetSchedule() {
		new MonthlySechedule();
	}

	@Override
	protected PaymentClassification GetClassification() {
		new HourlyClassification()
	}
}
