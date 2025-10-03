package co.com.bancopopular.automation.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestCasesInstance {

  private static TestCasesInstance instance;
  private Map<String, Integer> dataTestRail =
      Collections.synchronizedMap(new HashMap<>());

  private TestCasesInstance() {
  }

  public static TestCasesInstance getInstance() {
    if (instance == null) {
      instance = new TestCasesInstance();
    }
    return instance;
  }

  public Map<String, Integer> getData() {
    return dataTestRail;
  }

  public void setData(String testCase, int testCaseStatus) {
    this.dataTestRail.put(testCase, testCaseStatus);
  }

}
