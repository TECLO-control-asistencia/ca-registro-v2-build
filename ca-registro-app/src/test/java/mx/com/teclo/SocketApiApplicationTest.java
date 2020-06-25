package mx.com.teclo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mx.com.teclo.inicializar.LectorHuellaApiApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {LectorHuellaApiApplication.class})
public class SocketApiApplicationTest {

	@Test
	public void ddd() {
		System.out.println("Todo bien!!");
	}
}
