package ds.payment

import ds.payment.affiliation.UnionAffiliation
import ds.payment.classificaiton.CommissionedClassification
import ds.payment.classificaiton.HourlyClassification
import ds.payment.classificaiton.PaymentClassification
import ds.payment.classificaiton.SalariedClassification
import ds.payment.database.DB
import ds.payment.model.Employee
import ds.payment.model.ServiceCharge
import ds.payment.schedule.WeeklySchedule
import ds.payment.transaction.*
import org.springframework.context.ApplicationContext
import spock.lang.Shared
import spock.lang.Specification

import static spock.util.matcher.HamcrestMatchers.closeTo

class PayrollTest extends Specification{
	@Shared DB db
	
	def setupSpec() {
		ApplicationContext context = SpringContext.instance
		db = context.getBean(DB)
	}
	
	def "TestAddSalariedEmployee!"() {
		when:
		AddEmployeeTransaction t = new AddSalariedEmployeeTransaction(id, name, address, salary)
			t.execute()
		Employee e = db.getEmployee(id)

		then: "Test basic fields"
			e.getName().equals(name)
			
		and: "Test payment classification"
			def sc = e.getPaymentClassification()
			sc instanceof SalariedClassification
			(sc.getSalary() as BigDecimal) closeTo(salary, 0.001)
			
		where:
		id | name | address | salary
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
		1 | "Bob"   | "home"   | 1000.00f
		2 | "Alice" | "office" | 2000.00f
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
		AddCommissionedEmployeeTransaction t = new AddCommissionedEmployeeTransaction(id, name, address, salary, commissionRate)
			t.execute()

		SalesReceiptTransaction srt = new SalesReceiptTransaction(id, amount, date)
		srt.execute()

		then:
		Employee e = db.getEmployee(id)
		e

		def p = e.getPaymentClassification()
		CommissionedClassification cc = p as CommissionedClassification

		cc.getSalesReceipt(date)


		where:
		id | name     | address  | salary | amount | commissionRate | date
		1  | "Jason"  | "home"   | 120.00 | 100    | 0.2            | new Date()
		2  | "Homers" | "office" | 230.43 | 200    | 0.3            | new Date().parse('yyyy/MM/dd', '1973/07/09')
	}

	def "TestAddServiceCharge"() {
		when:
		def t = new AddHourlyEmployeeTransaction(empId, name, address, salary);
		t.execute();

		then:
			Employee e = db.getEmployee(empId)

		when:
		UnionAffiliation af = new UnionAffiliation(memberId, fee)
		e.affiliation = af
		db.addUnionMember(memberId, e)
		ServiceChargeTransaction sct = new ServiceChargeTransaction(memberId, date, fee)
		sct.execute()

		then:
		ServiceCharge sc = af.serviceCharges.find { it.date.equals(date) }
		sc
		(sc.amount as Number) closeTo(fee, 0.001)

		where:
		empId | name     | address  | salary | memberId | fee | date
		3     | "Jason"  | "home"   | 120.00 | 100      | 0.2 | new Date()
		4     | "Homers" | "office" | 230.43 | 200      | 0.3 | new Date().parse('yyyy/MM/dd', '1973/07/09')
	}

	def "immutableTest"() {
		when:
		AddEmployeeTransaction t = new AddSalariedEmployeeTransaction(empId, name, address, 0)
		t.execute()

		Employee e = db.getEmployee(empId)
		e.id = 9999;
		then:
		db.getEmployee(empId).id == empId


		where:
		empId | name     | address
		1     | "Jason"  | "home"
		2     | "Homers" | "office"
	}

	def 'ChangeNameTransactionTest'() {
		when:
		def t = new AddHourlyEmployeeTransaction(id, name, address, salary)
		t.execute()

		def tt = new ChangeNameTransaction(id, newName)
		tt.execute()

		then:
		def e = db.getEmployee(id)
			e
		e.name == newName

		where:
		id | name     | address  | newName   | salary
		1  | "Jason"  | "home"   | "Jason2"  | 1
		2  | "Homers" | "office" | "Homers2" | 2
	}

	def 'ChangehourlyTransactionTest'() {
		when:
		def t = new AddCommissionedEmployeeTransaction(empid, name, address, salary, commissionRate)
		t.execute()
		Employee e = db.getEmployee(empid)
		then:
		e

		when:
		def cht = new ChangeHourlyTransaction(empid, hourlyRate)
		cht.execute()
		def e2 = db.getEmployee(empid)

		then:
		e2
		def hc = e2.paymentClassification
		hc instanceof HourlyClassification

		(hc.rate as Number) closeTo(hourlyRate, 0.001)

		e2.schedule instanceof WeeklySchedule

		where:
		empid | name     | address  | salary | hourlyRate | commissionRate
		11    | "Jason"  | "home"   | 120.00 | 100        | 0.2
		22    | "Homers" | "office" | 230.43 | 200        | 0.3
	}

	def "TestChangeMemberTransaction"() {
		when:
		def t = new AddHourlyEmployeeTransaction(empid, name, address, salary)
		t.execute()

		def cmt = new ChangeMemberTransaction(empid, memberId, fee)
		cmt.execute()

		def e = db.getEmployee(empid)
		def af = e.affiliation
		def uf = af as UnionAffiliation
		def member = db.getAffiliationMember(memberId)
		then:
		e
		af
		uf
		(uf.getFee() as Number) closeTo(fee, 0.001)
		member
		member == e

		where:
		empid | name     | address  | salary | fee | memberId
		11    | "Jason"  | "home"   | 120.00 | 100 | 1234
		22    | "Homers" | "office" | 230.43 | 200 | 2145
	}
}
