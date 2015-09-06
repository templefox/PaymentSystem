package ds.payment.transaction

import ds.payment.SpringContext;
import ds.payment.classificaiton.HourlyClassification;
import ds.payment.database.DB;
import ds.payment.model.Employee
import ds.payment.model.TimeCard;
import groovy.transform.Canonical;
import org.springframework.context.ApplicationContext

@Canonical
class TimeCardTransaction implements Transaction{
	Date date
	float hours
	int empid
	
	@Override
	public void execute() {
		ApplicationContext context = SpringContext.instance;
		DB db = context.getBean(DB);
		
		Employee e = db.getEmployee(empid)
		HourlyClassification hc = e.getPaymentClassification() as HourlyClassification
		
		TimeCard tc = new TimeCard(date, hours);
		
		hc.addTimeCard(tc)
		
	}
}
