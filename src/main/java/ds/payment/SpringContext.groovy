package ds.payment
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Singleton(strict=false)
class SpringContext extends ClassPathXmlApplicationContext{
	SpringContext(){
		super("SpringBeans.xml");
	}
}
