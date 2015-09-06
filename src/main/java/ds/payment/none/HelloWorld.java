package ds.payment.none;
public class HelloWorld {
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public void printHello() {
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return "Hello"+name;
	}
	
	
}