package singleton_pattern;

public class Main {

  private static final int THREAD_NUM = 5;

  public static void main(String[] args) {
    UserThread[] users = new UserThread[THREAD_NUM];
    for (int i = 0; i < THREAD_NUM; i++) {
      users[i] = new UserThread((i + 1) + "-thread");
      users[i].start();
    }
  }

}
