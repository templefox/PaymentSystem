package ds.payment.database

import ds.payment.model.Employee

interface DB {
    Employee getEmployee(int id)
	void addEmployee(Employee e)

	void addEmployee(Employee e, boolean override)
	void deleteEmployee(int id)

	void addUnionMember(int memberId, Employee e)

	void deleteUnionMember(int memberId)

	List<Employee> getAllEmployees()
	Employee getAffiliationMember(int memberId)
}
