package ds.payment.transaction

import ds.payment.database.DB
import ds.payment.SpringContext;
import org.springframework.context.ApplicationContext

class DeleteEmployeeTransaction implements Transaction {
	int id;
	
	DeleteEmployeeTransaction(int id){
		this.id = id
	}
	
	@Override
	public void execute() {
		ApplicationContext context = SpringContext.instance;
		DB db = context.getBean(DB);
		
		db.deleteEmployee(id)
	}
}
