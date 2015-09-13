package ds.payment.classificaiton

import ds.payment.model.TimeCard
import groovy.transform.TupleConstructor;

@TupleConstructor
class HourlyClassification implements PaymentClassification{
	float rate
	private def timeCards = [:]
	
	void addTimeCard(TimeCard tc){
		timeCards[tc.date] = tc
		
	}
	
	TimeCard getTimeCard(Date date){
		timeCards[date]
	}
}
