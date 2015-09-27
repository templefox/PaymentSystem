package ds.payment.classificaiton

import ds.payment.model.PayCheck
import ds.payment.model.TimeCard
import groovy.transform.TupleConstructor

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

	@Override
	float calculatePay(PayCheck payCheck) {
		def total = 0
		timeCards.each { k, v ->
			TimeCard tc = v
			if (isInPayPeriod(tc.date, payCheck)) {
				def hours = tc.hours
				if (hours <= 8) {
					total += rate * hours
				} else {
					total += rate * 8 + rate * (hours - 8) * 1.5
				}
			}
		}
		total
	}
}
