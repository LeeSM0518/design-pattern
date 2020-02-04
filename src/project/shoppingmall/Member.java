package project.shopping_mall;

public class Member {

  private int id;
  private String name;
  private String username;
  private String password;
  private String phoneNumber;
  private String address;

  public Member(){}

  public Member(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public Member(String name, String username, String password, String phoneNumber, String address) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

}
