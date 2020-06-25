package mx.com.teclo.digitalizacionomr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mx.com.teclo.DigitalPersonMain;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DigitalPersonMainTest {
	
	@Test
	public void contextLoads() {
		DigitalPersonMain dpm = new DigitalPersonMain();
		if(dpm != null) {
			System.out.println("OK");
		}
	}

}
	
	