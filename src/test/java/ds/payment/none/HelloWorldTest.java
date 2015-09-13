package ds.payment.none;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;

public class HelloWorldTest {

	@Test
	public void testHelloWorld(){
    	ApplicationContext context = new ClassPathXmlApplicationContext(
			"SpringBeans.xml");

		HelloWorld obj = (HelloWorld) context.getBean("helloBean");
		
		assertEquals(obj.toString(), "HelloWorld");
	}
	
	@Test
	public void testGroovy(){
		GelloWorld gwGelloWorld = new GelloWorld();
		gwGelloWorld.setName("fuck");
		
		assertEquals(gwGelloWorld.toString(), "Gellofuck");
	}
	
	@Test
	public void testGelloWorld(){
    	ApplicationContext context = new ClassPathXmlApplicationContext(
			"SpringBeans.xml");

    	GelloWorld obj = (GelloWorld) context.getBean("GBean");
		
		assertEquals(obj.toString(), "GelloWorld");
	}
	
}
