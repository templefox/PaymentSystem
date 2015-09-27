package ds.payment.database

import ds.payment.model.Employee

class DBImpl implements DB{
	private Map<Integer, Employee> employees = new HashMap<Integer, Employee>();
	private Map<Integer, Employee> affiliationMap = new HashMap<Integer, Employee>()
	
	@Override
    public Employee getEmployee(int id){
		employees.get(id)?.copy()
	}
//
//	@Override
//	public void addEmployee(Employee e){
//		addEmployee(e,false)
//	}
	
	@Override
	public void deleteEmployee(int id){
		employees.remove(id);
	}

	@Override
	public void addUnionMember(int memberId, Employee e) {
		affiliationMap.put(memberId, e);
	}

	@Override
	public Employee getAffiliationMember(int memberId) {
		affiliationMap.get(memberId).copy()
	}

	@Override
	void addEmployee(Employee e) {
		addEmployee(e, false)
	}

	@Override
	public void addEmployee(Employee e, boolean override) {
		e = e.copy()
		if (override) {
			employees.put(e.id, e)
		} else {
			employees.putIfAbsent(e.id, e)
		}
	}

	@Override
	void deleteUnionMember(int memberId) {
		affiliationMap.remove(memberId)
	}

	@Override
	List<Employee> getAllEmployees() {
		employees.values().collect { it.copy() }
	}
}
