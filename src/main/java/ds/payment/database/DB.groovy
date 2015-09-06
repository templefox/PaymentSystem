package ds.payment.database

import ds.payment.model.Employee;


interface DB {
    Employee getEmployee(int id)
	void addEmployee(Employee e)
	void deleteEmployee(int id)
}
