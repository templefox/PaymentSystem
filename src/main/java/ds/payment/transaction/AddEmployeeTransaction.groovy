package ds.payment.transaction

import ds.payment.SpringContext;
import ds.payment.classificaiton.PaymentClassification;
import ds.payment.database.DB
import ds.payment.model.Employee
import ds.payment.paymentmethod.PaymentMethod
import ds.payment.schedule.PaymentSchedule;

import org.springframework.context.ApplicationContext

abstract class AddEmployeeTransaction implements Transaction{
	int id;
	String name;
	String address;
	
	AddEmployeeTransaction(int id,String name,String address){
		this.id = id;
		this.name = name;
		this.address = address;
	}
	
	@Override
	public void execute(){
		ApplicationContext context = SpringContext.instance;
		DB db = context.getBean(DB);
		
		def e = new Employee();
		e.id = id;
		e.name = name;
		e.address = address;
		
		e.paymentClassification = GetClassification();
		e.schedule = GetSchedule();
		e.method = GetPaymentMethod();
		
		db.addEmployee(e);
	}
	
	abstract protected PaymentMethod GetPaymentMethod();
	abstract protected PaymentSchedule GetSchedule();
	abstract protected PaymentClassification GetClassification();
}
