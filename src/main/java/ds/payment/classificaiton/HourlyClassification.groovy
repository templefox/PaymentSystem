package ds.payment.classificaiton

import ds.payment.model.TimeCard;

class HourlyClassification implements PaymentClassification{
    private	def timeCards = [:]
	
	void addTimeCard(TimeCard tc){
		timeCards[tc.date] = tc
		
	}
	
	TimeCard getTimeCard(Date date){
		timeCards[date]
	}
}
