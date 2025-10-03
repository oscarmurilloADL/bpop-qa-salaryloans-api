package co.com.bancopopular.automation.utils;


public enum QuestionEventEnum {

  QUESTION_PEP_REQUEST("PEP_NEGATIVE_DECLARATION"),
  QUESTION_PEP_RESPONSE("PEP_DECLARATION"),
  QUESTION_USGAP_REQUEST("US_GAP_NEGATIVE_DECLARATION"),
  QUESTION_USGAP_RESPONSE("US_GAP_DECLARATION"),
  QUESTION_BOARD_MEMBER_REQUEST("BOARD_MEMBER_NEGATIVE_DECLARATION"),
  QUESTION_BOARD_MEMBER_RESPONSE("BOARD_MEMBER_DECLARATION"),
  QUESTION_ACCEPT_TERMS_AND_CONDITIONS("ACCEPT_TERMS_AND_CONDITIONS");

  private final String text;

  private QuestionEventEnum(String value) {
    text = value;
  }

  @Override
  public String toString(){
    return this.text;
  }
}
