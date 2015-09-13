package ds.payment.transaction

import ds.payment.SpringContext
import ds.payment.database.DB
import groovy.transform.TupleConstructor
import org.springframework.context.ApplicationContext

@TupleConstructor
class DeleteEmployeeTransaction implements Transaction {
	int id;
	
	@Override
	public void execute() {
		ApplicationContext context = SpringContext.instance;
		DB db = context.getBean(DB);
		
		db.deleteEmployee(id)
	}
}
