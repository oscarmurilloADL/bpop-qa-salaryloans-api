package co.com.bancopopular.automation.utils.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LogPrinter {
  private static final Logger LOGGER = LogManager.getLogger(LogPrinter.class);

  private LogPrinter() {
    super();
  }

  public static void printLog(String message) {
      LOGGER.info(message);
  }

  public static void printError(String message ,String error) {
      LOGGER.error( message, error);
  }
}
