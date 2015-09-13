package ds.payment.transaction

import ds.payment.SpringContext
import ds.payment.classificaiton.PaymentClassification
import ds.payment.database.DB
import ds.payment.model.Employee
import ds.payment.paymentmethod.PaymentMethod
import ds.payment.schedule.PaymentSchedule
import org.springframework.context.ApplicationContext

abstract class AddEmployeeTransaction implements Transaction{
	int id
	String name
	String address
	
	@Override
	public void execute(){
		ApplicationContext context = SpringContext.instance;
		DB db = context.getBean(DB);
		
		def e = new Employee();
		e.id = id;
		e.name = name;
		e.address = address;

		e.paymentClassification = getClassification();
		e.schedule = getSchedule();
		e.method = getPaymentMethod();
		
		db.addEmployee(e);
	}

	abstract protected PaymentMethod getPaymentMethod();

	abstract protected PaymentSchedule getSchedule();

	abstract protected PaymentClassification getClassification();
}
