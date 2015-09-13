package ds.payment.model

import ds.payment.affiliation.Affiliation
import ds.payment.affiliation.NonAffiliation
import ds.payment.classificaiton.PaymentClassification
import ds.payment.paymentmethod.PaymentMethod
import ds.payment.schedule.PaymentSchedule
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor
; ;

@TupleConstructor
@EqualsAndHashCode
class Employee {
	Integer id;
	String name;
	String address;
	PaymentClassification paymentClassification;
	PaymentSchedule schedule;
	PaymentMethod method;
	Affiliation affiliation = new NonAffiliation();

	public Employee copy() {
		new Employee(id, name, address, paymentClassification, schedule, method, affiliation)
	}
}
