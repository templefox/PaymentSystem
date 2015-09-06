package ds.payment

import org.hamcrest.core.IsInstanceOf;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext

import ds.payment.classificaiton.HourlyClassification;
import ds.payment.classificaiton.PaymentClassification
import ds.payment.classificaiton.SalariedClassification;
import ds.payment.database.DB;
import ds.payment.model.Employee
import ds.payment.model.TimeCard
import ds.payment.transaction.AddCommissionedEmployeeTransaction;
import ds.payment.transaction.AddEmployeeTransaction
import ds.payment.transaction.AddHourlyEmployeeTransaction;
import ds.payment.transaction.AddSalariedEmployeeTransaction
import ds.payment.transaction.DeleteEmployeeTransaction
import ds.payment.transaction.TimeCardTransaction;
import spock.lang.*;
import static spock.util.matcher.HamcrestMatchers.closeTo

class PayrollTest extends Specification{
	@Shared DB db
	
	def setupSpec() {
		ApplicationContext context = SpringContext.instance
		db = context.getBean(DB)
	}
	
	def "TestAddSalariedEmployee!"() {
		when:
			AddEmployeeTransaction t = new AddSalariedEmployeeTransaction(empId,name,address,salary)
			t.execute()
			Employee e = db.getEmployee(empId)

		then: "Test basic fields"
			e.getName().equals(name)
			
		and: "Test payment classification"
			def sc = e.getPaymentClassification()
			sc instanceof SalariedClassification
			(sc.getSalary() as BigDecimal) closeTo(salary, 0.001)
			
		where:
			empId	|	name	|	address	|	salary
			1		|	"Bob"	|	"home" 	|	1000.00
			2		|	"Alice"	|	"office"|	2000.00
		}
	
	def "TestRemoveEmployee"(){
		when:
			{
				AddEmployeeTransaction t = new AddSalariedEmployeeTransaction(empId,name,address,salary)
				t.execute()
			}
		then:
		    db.getEmployee(empId)
			
		when:
			{
		    	DeleteEmployeeTransaction t = new DeleteEmployeeTransaction(empId);
				t.execute()
			}
		then:
			!db.getEmployee(empId)
			
		where:
			empId	|	name	|	address	|	salary
			1		|	"Bob"	|	"home" 	|	1000.00
			2		|	"Alice"	|	"office"|	2000.00
	}
	
	
	def "TestTimeCardTransaction"(){
		when:
			AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, name, address, salary)
			t.execute()
			
			TimeCardTransaction tct = new TimeCardTransaction(date,hours,empId)
			tct.execute()
			
			
		then:
			Employee e = db.getEmployee(empId);
			e
			
			PaymentClassification classification = e.getPaymentClassification()
			classification instanceof HourlyClassification
			HourlyClassification hc = classification as HourlyClassification
			
			hc.getTimeCard(date)
			
			
		where:
			empId	|name		|address	|salary	|hours	|date		
			1		|"Jason"	|"home" 	|10.00	|8		|new Date()	
			2		|"Homers"	|"office"	|20.43	|10		|new Date().parse('yyyy/MM/dd', '1973/07/09')	
	}
	
	
	def "TestSalesReceiptTransaction"(){
		when:
			AddCommissionedEmployeeTransaction t = new AddCommissionedEmployeeTransaction(empId,name,address,salary,commissionRate)
			t.execute()
			
		then:
			Employee e = db.getEmployee(empId)
			e
	}
}
