package net.nectarius.klag;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext-test.xml")
public class QueryServiceTest {

  @Before
  public void setup() throws SecurityException, NoSuchFieldException {
  }

}
