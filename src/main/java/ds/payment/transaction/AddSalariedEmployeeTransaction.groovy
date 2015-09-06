package ds.payment.transaction

import java.security.PublicKey
import org.springframework.context.ApplicationContext

import ds.payment.SpringContext
import ds.payment.classificaiton.PaymentClassification;
import ds.payment.classificaiton.SalariedClassification
import ds.payment.database.DB
import ds.payment.model.Employee;
import ds.payment.paymentmethod.HoldMethod
import ds.payment.paymentmethod.PaymentMethod;
import ds.payment.schedule.MonthlySechedule
import ds.payment.schedule.PaymentSchedule;

class AddSalariedEmployeeTransaction extends AddEmployeeTransaction {
	float salary;
	
	public AddSalariedEmployeeTransaction(int id,String name,String address,float salary){
		super(id,name,address)
		this.salary = salary;
	}

	@Override
	protected PaymentMethod GetPaymentMethod() {
		new HoldMethod()
	}

	@Override
	protected PaymentSchedule GetSchedule() {
		new MonthlySechedule();
	}

	@Override
	protected PaymentClassification GetClassification() {
		new SalariedClassification(salary);
	}
}
