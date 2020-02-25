package project.shoppingmall.dao.order;

public enum Status {

  SENT("SENT"),
  NOT_SENT("NOT_SENT");

  private String message;

  Status(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

}
