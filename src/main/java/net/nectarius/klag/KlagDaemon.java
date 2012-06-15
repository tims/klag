package net.nectarius.klag;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main class that loads the application context
 */
public class KlagDaemon implements Daemon {

  private static final Logger log = LoggerFactory.getLogger(KlagDaemon.class);

  private ConfigurableApplicationContext context = null;

  public void init(DaemonContext daemoncontext) throws DaemonInitException {
  }

  public void start() throws Exception {
    log.info("[*] ===============================================================");
    log.info("[*] Starting Klag");
    log.info("[*] ===============================================================");
    context = new ClassPathXmlApplicationContext("spring-Klag.xml");
    context.registerShutdownHook();
  }

  public void stop() throws Exception {
    log.info("Stopping Klag...");
  }

  public void destroy() {
  }

  public static void main(String[] argv) throws Exception {
    new KlagDaemon().start();
  }
}
