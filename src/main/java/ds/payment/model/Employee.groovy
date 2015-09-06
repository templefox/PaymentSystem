package ds.payment.model

import ds.payment.classificaiton.PaymentClassification
import ds.payment.paymentmethod.PaymentMethod;
import ds.payment.schedule.PaymentSchedule;

class Employee {
	Integer id;
	String name;
	String address;
	PaymentClassification paymentClassification;
	PaymentSchedule schedule;
	PaymentMethod method;
}
