package project.shoppingmall;

import project.shoppingmall.dao.member.MemberDao;
import project.shoppingmall.dao.member.PostgresSqlMemberDao;
import project.shoppingmall.dto.Member;

import java.util.Scanner;

public class Client {

  Member accessMember;
  MemberDao memberDao = PostgresSqlMemberDao.getInstance();

  private void logIn() {
    Scanner scanner = new Scanner(System.in);

    printLine();
    System.out.print("아이디 >> ");
    String username = scanner.nextLine();
    System.out.print("비밀번호 >> ");
    String password = scanner.nextLine();
    Member member = new Member();
    printLine();

    member.setUsername(username);
    member.setPassword(password);

    accessMember = memberDao.selectOne(member);
  }

  private void logOut() {
    accessMember = null;
  }

  private void join() {
    Scanner scanner = new Scanner(System.in);

    printLine();
    System.out.print("이름 >> ");
    String name = scanner.nextLine();
    System.out.print("아이디 >> ");
    String username = scanner.nextLine();
    System.out.print("비밀번호 >> ");
    String password = scanner.nextLine();
    System.out.print("전화번호 >> ");
    String phoneNumber = scanner.nextLine();
    System.out.print("주소 >> ");
    String address = scanner.nextLine();
    printLine();

    Member member = new Member(name, username, password, phoneNumber, address);
    memberDao.insert(member);
  }

  private void myInfo() {
    printLine();
    System.out.println("이름 >> " + accessMember.getName());
    System.out.println("아이디 >> " + accessMember.getUsername());
    System.out.println("전화번호 >> " + accessMember.getPhoneNumber());
    System.out.println("주소 >> " + accessMember.getAddress());
    printLine();
  }

  private void printLine() {
    System.out.println("=============================");
  }

  private void printIntroMenu() {
    printLine();
    System.out.println("메뉴를 선택하세요.");
    System.out.println("1. 로그인");
    System.out.println("2. 회원가입");
    System.out.println("3. 종료");
    printLine();
    System.out.print(">>>> ");
  }

  private void printMemberMenu() {
    printLine();
    System.out.println("메뉴를 선택하세요.");
    System.out.println("1. 내정보 보기");
    System.out.println("2. 로그아웃");
    printLine();
    System.out.print(">>>> ");
  }

  public Member getAccessMember() {
    return accessMember;
  }

  public static void main(String[] args) {
    Client client = new Client();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      client.printIntroMenu();
      String choice = scanner.nextLine();
      switch (choice) {
        case "1":
          client.logIn();
          if (client.getAccessMember() != null) {
            boolean logIn = true;
            while (logIn) {
              client.printMemberMenu();
              String memberChoice = scanner.nextLine();
              switch (memberChoice) {
                case "1":
                  client.myInfo();
                  break;
                case "2":
                  client.logOut();
                  logIn = false;
                  break;
                default:
                  System.out.println("잘못된 입력입니다.");
                  break;
              }
            }
          }
          break;
        case "2":
          client.join();
          break;
        case "3":
          return;
        default:
          System.out.println("잘못된 입력입니다.");
          break;
      }
    }
  }

}
