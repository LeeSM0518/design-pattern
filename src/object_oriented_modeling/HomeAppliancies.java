package object_oriented_modeling;

public abstract class HomeAppliancies {

  private int serialNo;         // 제조 번호
  private String manufacturer;  // 제조 회사
  private int year;             // 제조 년도

  public abstract void turnOn();
  public abstract void turnOff();

}
