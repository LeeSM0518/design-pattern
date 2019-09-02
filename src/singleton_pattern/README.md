# 6. Singleton pattern

싱글톤 패턴

<br/>

# 6.1. 프린터 관리자 만들기

* **리소스를 받아 이를 출력하는 print 메서드를 제공하는 Printer 클래스**

  ```java
  public class Printer {
    public Printer() {}
   
    public void print(Resource r) {
      ...
    }
  }
  ```

  * 그러나 프린터는 하나 이기 때문에, Printer 클래스를 사용해 프린터를 이용하려면 클라이언트 프로그램에서 **new Printer() 가 반드시 한 번만 호출되도록** 해야 한다.

<br/>

* **생성자 수정 코드**

  ```java
  public class Printer {
    private Printer() {}
    public void print(Resource r) {
      ...
    }
  }
  ```

  * 이렇게 **생성자를 private으로 선언하면** 다른 곳에서 생성할 수 없도록 할 수 있다.

<br/>

* **인스턴스를 만들어주는 메서드**

  ```java
  public class Printer {
    private static Printer printer = null;
    private Printer() {}
    
    public static Printer getPrinter() {
      if (printer == null)
        printer = new Printer();
      
      return printer;
    }
    
    public void print(Resource r) {
      ...
    }
  }
  ```

  * getPrinter 메서드를 통해 프린터 **인스턴스가 이미 생성되어 있는지를 검사한다.** 만약 이미 인스턴스가 생성되었다면 printer 변수에서 참조하는 인스턴스를 반환한다.
  * 이 코드에서 주의할 점은 getPrinter 메서드와 **printer 변수가 static 타입으로** 선언되었다는 점이다.
  * **정적 메서드, 정적 변수** : 구체적인 인스턴스에 속하는 영역이 아니고 클래스 자체에 속한다는 의미다.

<br/>

* **완성된 코드**

  Printer.class

  ```java
  public class Printer {
  
    private static Printer printer = null;
    private Printer() {}
  
    public static Printer getPrinter() {
      if (printer == null) {
        printer = new Printer();
      }
      return printer;
    }
  
    public void print(String str) {
      System.out.println(str);
    }
  
  }
  ```

  User.class

  ```java
  public class User {
  
    private String name;
  
    public User(String name) {
      this.name = name;
    }
  
    public void print() {
      Printer printer = Printer.getPrinter();
      printer.print(this.name + " print using " + printer.toString() + ".");
    }
  
  }
  ```

  Main.class

  ```java
  public class Main {
  
    private static final int User_NUM = 5;
  
    public static void main(String[] args) {
      User[] users = new User[User_NUM];
      for (int i = 0; i < User_NUM; i++) {
        users[i] = new User((i + 1) + "-user");
        users[i].print();
      }
    }
  
  }
  ```

  **실행 결과**

  ```
  1-user print using singleton_pattern.Printer@1540e19d.
  2-user print using singleton_pattern.Printer@1540e19d.
  3-user print using singleton_pattern.Printer@1540e19d.
  4-user print using singleton_pattern.Printer@1540e19d.
  5-user print using singleton_pattern.Printer@1540e19d.
  ```

<br/>

# 6.2. 문제점

다중 스레드에서 Printer 클래스를 이용할 때 인스턴스가 1개 이상 생성되는 경우가 발생할 수 있다.

* **시나리오**
  1. Printer 인스턴스가 아직 생성되지 않았을 때 스레드 1이 getPrinter 메서드의 if문을 실행해 이미 인스턴스가 생성되었는지 확인한다. 현재 printer 변수는 null인 상태다.
  2. 만약 스레드 1이 생성자를 호출해 인스턴스를 만들기 전 스레드 2가 if문을 실행해 printer 변수가 null인지 확인한다. 현재 null이므로 인스턴스를 생성하는 코드, 즉 생성자를 호출하는 코드를 실행하게 된다.
  3. 스레드 1도 스레드 2와 마찬가지로 인스턴스를 생성하는 코드를 실행하게 되면 결과적으로 Printer 클래스의 인스턴스가 2개 생성된다.

> 위의 시나리오는 **경합 조건(race condition)을** 발생시킨다. 경합 조건이란 메모리와 같은 동일한 자원을 2개 이상의 스레드가 이용하려고 경합하는 현상을 말한다.

<br/>

* **시나리오대로 코드 변경**

  UserThread.class

  ```java
  public class UserThread extends Thread {
  
    public UserThread(String name) {
      super(name);
    }
  
    public void run() {
      Printer printer = Printer.getPrinter();
      printer.print(Thread.currentThread().getName() +
          " print using " + printer.toString() + ".");
    }
  
  }
  ```

  Printer.class

  ```java
  public class Printer {
  
    private static Printer printer = null;
    private Printer() {}
  
    public static Printer getPrinter() {
      if (printer == null) {
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {}
        printer = new Printer();
      }
      return printer;
    }
  
    public void print(String str) {
      System.out.println(str);
    }
  
  }
  ```

  Main.class

  ```java
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
  ```

  **실행 결과**

  ```
  1-thread print using singleton_pattern.Printer@6c841b31.
  3-thread print using singleton_pattern.Printer@78f0e569.
  4-thread print using singleton_pattern.Printer@c01b05e.
  5-thread print using singleton_pattern.Printer@29e0f336.
  2-thread print using singleton_pattern.Printer@29e0f336.
  ```

  > 각 스레드마다 각기 다른 Printer 인스턴스를 사용해 출력하는 것을 볼 수 있다.  이는 Printer 클래스가 상태를 유지해야 하는 경우에 문제가 발생한다.

<br/>

# 6.3. 해결책

다중 스레드 애플리케이션에서 발생하는 문제를 해결하는 방법 2가지

* **정적 변수에 인스턴스를 만들어 초기화하는 방법**
* **인스턴스를 만드는 메서드에 동기화하는 방법**

<br/>

* **printer라는 정적 변수에 Printer 인스턴스를 만들어 초기화하는 방법의 코드**

  Printer.class

  ```java
  public class Printer {
  
    private static Printer printer = new Printer();
    private int counter = 0;
    private Printer() {}
  
    public static Printer getPrinter() {
      return printer;
    }
  
    public void print(String str) {
      counter++;
      System.out.println(str);
    }
  
  }
  ```

  **Main 실행 결과**

  ```
  1-thread print using singleton_pattern.Printer@493ee214.
  5-thread print using singleton_pattern.Printer@493ee214.
  2-thread print using singleton_pattern.Printer@493ee214.
  3-thread print using singleton_pattern.Printer@493ee214.
  4-thread print using singleton_pattern.Printer@493ee214.
  ```

  * **정적 변수는** 객체가 생성되기 전 **클래스가 메모리에 로딩될 때 만들어져** 초기화가 한 번만 실행된다. 또한 정적 변수는 **프로그램이 시작될 때부터 종료될 때까지 없어지지 않고 메모리에 계속 상주하며** 클래스에서 생성된 모든 객체에서 참조할 수 있다.

<br/>

* **Printer 클래스의 객체를 얻는 getPrinter 메서드를 동기화하는 코드**

  Printer.class

  ```java
  public class Printer {
  
    private static Printer printer = null;
    private Printer() {}
  
    public synchronized static Printer getPrinter() {
      if (printer == null)
        printer = new Printer();
      return printer;
    }
  
    public void print(String str) {
      System.out.println(str);
    }
  
  }
  ```

  **Main 실행 결과**

  ```
  3-thread print using singleton_pattern.Printer@fcc3aac.
  2-thread print using singleton_pattern.Printer@fcc3aac.
  4-thread print using singleton_pattern.Printer@fcc3aac.
  1-thread print using singleton_pattern.Printer@fcc3aac.
  5-thread print using singleton_pattern.Printer@fcc3aac.
  ```

<br/>

* **Printer 클래스의 counter 변수 동기화**

  Printer.class

  ```java
  public class Printer {
  
    private static Printer printer = null;
    private int counter = 0;
    private Printer() {}
  
    public synchronized static Printer getPrinter() {
      if (printer == null)
        printer = new Printer();
      return printer;
    }
  
    public void print(String str) {
      synchronized (this) {
        counter++;
        System.out.println(str + counter);
      }
    }
  
  }
  ```

  **Main 실행 결과**

  ```
  2-thread print using singleton_pattern.Printer@6f753c95.1
  3-thread print using singleton_pattern.Printer@6f753c95.2
  5-thread print using singleton_pattern.Printer@6f753c95.3
  4-thread print using singleton_pattern.Printer@6f753c95.4
  1-thread print using singleton_pattern.Printer@6f753c95.5
  ```

<br/>

# 6.4. 싱글턴 패턴

**싱글턴 패턴(Singleton Pattern)은** 인스턴스가 오직 하나만 생성되는 것을 보장하고 어디에서든 이 인스턴스에 접근할 수 있도록 하는 디자인 패턴이다.

* **싱글턴 패턴 컬레보레이션**

  <img src="../../capture/스크린샷 2019-09-02 오전 10.19.30.png" width=500>

<br/>

* **싱글턴 패턴의 순차 다이어그램**

  <img src="http://cfile236.uf.daum.net/image/22534B4D51CB953D118FED">

<br/>

# 6.5. 싱글턴 패턴과 정적 클래스

실제로 굳이 싱글턴 패턴을 사용하지 않고 정적 메서드로만 이루어진 **정적 클래스(static class)를 사용해도** 동일한 효과를 얻을 수 있다.

* **정적 클래스 구현 예시**

  Printer.class

  ```java
  public class Printer {
  
    private static int counter = 0;
  
    public synchronized static void print(String str) {
        counter++;
        System.out.println(str + counter);
    }
  
  }
  ```

  UserThread.class

  ```java
  public class UserThread extends Thread {
  
    public UserThread(String name) {
      super(name);
    }
  
    public void run() {
      Printer.print(Thread.currentThread().getName() +
          " print using.");
    }
  
  }
  ```

  Main.class

  ```java
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
  ```

  **실행 결과**

  ```
  1-thread print using.1
  2-thread print using.2
  3-thread print using.3
  4-thread print using.4
  5-thread print using.5
  ```

  * 정적 클래스를 이용하는 방법이 싱글턴 패턴을 이용하는 방법과 가장 차이가 나는 점은 **객체를 전혀 생성하지 않고 메서드를 사용한다는 점이다.** 정적 메서드를 사용하므로 일반적으로 실행할 때 인스턴스 메서드를 사용하는 것 보다 **성능 면에서 우수하다고** 할 수 있다.

<br/>

그러나, 정적 클래스를 사용할 수 없는 경우가 있다. 가장 대표적인 경우가 인터페이스를 구현해야 하는 경우다. 정적 메서드는 인터페이스에서 사용할 수 없다.

- **허용되지 않는 코드**

  ```java
  public interface Printer {
    public static void print(String str);	// 허용되지 않음
  }
  
  public class RealPrinter315 implements Printer {
    public synchronized static void print(String str) {
      ... // 실제 프린터 하드웨어를 조작하는 코드
    }
  }
  ```

<br/>

단위 테스트를 실행할 때는 실제 프린터를 테스트용 가짜 프린터 객체로 대체하는 것이 좋다.

- **Printer 인터페이스를 참조하는 UsePrinter 클래스**

  <img src="../../capture/스크린샷 2019-09-02 오전 10.44.28.png">

  - 이렇게 설계를 하면 UsePrinter 클래스는 필요에 따라 실제의 프린터 하드웨어를 구동하는 RealPrinter315나 FakePrinter 클래스를 사용할 수 있게 된다.

- **코드**

  UsePrinter.class

  ```java
  public class UsePrinter {
    public void doSomething(Printer printer) {
      String str;
      ...
      str = ...;
      
      printer.print(str);
      ...
    }
  }
  ```

  Printer.class

  ```java
  public interface Printer {
    public void print(String str);
  } 
  ```

  RealPrinter.class

  ```java
  public class RealPrinter315 implements Printer {	// 싱글턴 패턴 사용
    private static Printer printer = null;
    private RealPrinter315() {}
    
    public synchronized static Printer getPrinter() {
      if (printer == null) {
        printer = new RealPrinter315();
      }
      return printer;
    }
    
    public void print(String str) {
      ... // 실제 프린터 하드웨어를 조작하는 코드
    }
  }
  ```

  FakePrinter.class

  ```java
  public class FakePrinter implements Printer {	// 테스트용 가짜 프린터
    private String str;
    
    public void print(String str) {
      this.str = str;
    }
    
    public String get() {
      return str;
    }
  }
  ```

  - 이 방법 외에도 정적 setter 메서드를 사용해 테스트용 대역 클래스를 만들 수 있다. 이렇게 하려면 싱글턴 클래스에 정적 setter 메서드를 추가하면 된다.