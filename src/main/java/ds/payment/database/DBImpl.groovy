package ds.payment.database

import ds.payment.model.Employee;


class DBImpl implements DB{
	private Map<Integer, Employee> employees = new HashMap<Integer, Employee>();
	
	@Override
    public Employee getEmployee(int id){
		employees.get(id)
	}
	
	@Override
	public void addEmployee(Employee e){
		employees.put(e.id, e)
	}
	
	@Override
	public void deleteEmployee(int id){
		employees.remove(id);
	}
}
