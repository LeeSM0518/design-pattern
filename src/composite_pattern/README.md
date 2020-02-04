# 14. 컴퍼지트 패턴

## 14.1. 컴퓨터에 추가 장치 지원하기

* **컴퓨터를 모델링해보자.**

  * 키보드(Keyboard 클래스)
  * 본체(Body 클래스)
  * 모니터(Monitor 클래스)

* Computer 클래스는 Keyboard, Body, Monitor 클래스 사이의 합성 관계로 표현한 클래스 다이어그램으로 나타낼 수 있다.

  ![image](https://user-images.githubusercontent.com/43431081/73741940-a822c300-478e-11ea-831b-06188985606e.png)

  * 마름모 표시를 함으로써 합성 관계를 표기한다.
  * 마름모가 표시된 클래스는 전체의 의미가 있고 반대쪽 클래스는 부분의 의미가 있다.
  * 컴퓨터는 가격(price 변수)과 전력 소비량(power 변수)를 갖는다.

* **코드**

  * Keyboard

    ```java
    public class Keyboard {
    
      private int price;
      private int power;
    
      public Keyboard(int price, int power) {
        this.price = price;
        this.power = power;
      }
    
      public int getPrice() {
        return price;
      }
    
      public int getPower() {
        return power;
      }
      
    }
    ```

  * Body

    ```java
    public class Body {
    
      private int price;
      private int power;
    
      public Body(int price, int power) {
        this.price = price;
        this.power = power;
      }
    
      public int getPrice() {
        return price;
      }
    
      public int getPower() {
        return power;
      }
    
    }
    ```

  * Monitor

    ```java
    public class Monitor {
    
      private int price;
      private int power;
    
      public Monitor(int price, int power) {
        this.price = price;
        this.power = power;
      }
    
      public int getPrice() {
        return price;
      }
    
      public int getPower() {
        return power;
      }
    
    }
    ```

  * Computer

    ```java
    public class Computer {
    
      private Body body;
      private Keyboard keyboard;
      private Monitor monitor;
    
      public void addBody(Body body) {
        this.body = body;
      }
    
      public void addKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
      }
    
      public void adddMonitor(Monitor monitor) {
        this.monitor = monitor;
      }
    
      public int getPrice() {
        int bodyPrice = body.getPrice();
        int keyboardPrice = keyboard.getPrice();
        int monitorPrice = monitor.getPrice();
        return bodyPrice + keyboardPrice + monitorPrice;
      }
    
      public int getPower() {
        int bodyPower = body.getPower();
        int keyboardPower = keyboard.getPower();
        int monitorPower = monitor.getPower();
        return bodyPower + keyboardPower + monitorPower;
      }
    
    }
    ```

  * Client

    ```java
    public class Client {
    
      public static void main(String[] args) {
        Body body = new Body(100, 70);
        Keyboard keyboard = new Keyboard(5, 2);
        Monitor monitor = new Monitor(20, 30);
    
        Computer computer = new Computer();
        computer.addBody(body);
        computer.addKeyboard(keyboard);
        computer.adddMonitor(monitor);
    
        int computerPrice = computer.getPrice();
        int computerPower = computer.getPower();
        System.out.println("Computer Power: " + computerPower + "W");
        System.out.println("Computer Price: " + computerPrice + "만 원");
      }
    
    }
    ```

<br>

## 14.2. 문제점

* 현재 Computer 클래스는 Body, Keyboard, Monitor 객체로 구성되어 있다. 만약 Computer 클래스의 부품으로 Speaker 클래스를 추가한다면? 또는 Mouse 클래스를 추가한다면?

![image](https://user-images.githubusercontent.com/43431081/73742877-bc67bf80-4790-11ea-97c4-80392ad8850b.png)

<br>

* **Speaker 클래스의 코드**

  ```java
  public class Speaker {
    
    private int price;
    private int power;
  
    public Speaker(int price, int power) {
      this.price = price;
      this.power = power;
    }
  
    public int getPrice() {
      return price;
    }
  
    public int getPower() {
      return power;
    }
    
  }
  ```

* **Compuer 클래스 코드**

  ```java
  public class Computer {
  
    private Body body;
    private Keyboard keyboard;
    private Monitor monitor;
    private Speaker speaker;
  
    public void addBody(Body body) {
      this.body = body;
    }
  
    public void addKeyboard(Keyboard keyboard) {
      this.keyboard = keyboard;
    }
  
    public void adddMonitor(Monitor monitor) {
      this.monitor = monitor;
    }
    
    public void addSpeaker(Speaker speaker) {
      this.speaker = speaker;
    }
  
    public int getPrice() {
      int bodyPrice = body.getPrice();
      int keyboardPrice = keyboard.getPrice();
      int monitorPrice = monitor.getPrice();
      int speakerPrice = speaker.getPrice();
      return bodyPrice + keyboardPrice + monitorPrice + speakerPrice;
    }
  
    public int getPower() {
      int bodyPower = body.getPower();
      int keyboardPower = keyboard.getPower();
      int monitorPower = monitor.getPower();
      int speakerPower = speaker.getPower();
      return bodyPower + keyboardPower + monitorPower + speakerPower;
    }
  
  }
  ```

<br>

즉, 새로운 부품을 추가하려면 다음과 같이 수정해야 한다.

* 새로운 부품에 대한 참조를 필드로 추가한다.
* 새로운 부품 객체를 설정하는 setter 메소드로 addDevice와 같은 메서드를 추가한다.
* getPrice, getPower 등과 같이 컴퓨터의 부품을 이용하는 모든 메서드에서는 새롭게 추가된 부품 객체를 이용할 수 있도록 수정한다.

이처럼 Computer 클래스에 부품을 추가(확장) 할 때 Computer 클래스의 코드가 변경되므로 **OCP를 위반하는 것이다.**

<br>

## 14.3. 해결책

위의 문제점의 핵심은 **Computer 클래스에 속한 부품의 구체적인 객체를 가리키게 되면 OCP를 위반하게 된다는 점이다.**

그러므로 **구체적인 부품들을 일반화한 클래스를 정의하고** 이를 Computer 클래스가 가리키게 하는 것이 올바른 설계다.

<br>

### 일반화된 부품을 갖도록 개선한 Computer 클래스 설계

![image](https://user-images.githubusercontent.com/43431081/73743856-ddc9ab00-4792-11ea-9c9d-494dc372bd39.png)

* **주요 개선점**
  * Computer가 가질 수 있는 부품들을 일반화해 ComputerDevice라는 클래스를 정의.
  * Keyboard, Body, Monitor 등 구체적인 부품 클래스들은 ComputerDevice의 하위 클래스로 정의
  * Computer 클래스는 ComputerDevice 클래스의 하위 클래스인 Keyboard, Body, Monitor를 부분 클래스로 가질 수 있다.
  * ComputerDevice 클래스를 이용하면 클라이언트 프로그램은 Body, Keyboard 등과 동일한 방식으로 Computer 클래스를 사용할 수 있게 된다.

<br>

### 코드

* **ComputerDevice**

  ```java
  public abstract class ComputerDevice {
  
    public abstract int getPrice();
    public abstract int getPower();
  
  }
  ```

* **Body**

  ```java
  public class Body extends ComputerDevice {
  
    public Body(int price, int power) {
      this.price = price;
      this.power = power;
    }
  
    private int price;
    private int power;
  
    @Override
    public int getPrice() {
      return price;
    }
  
    @Override
    public int getPower() {
      return power;
    }
  
  }
  ```

* **Keyboard**

  ```java
  public class Keyboard extends ComputerDevice {
  
    private int price;
    private int power;
  
    public Keyboard(int price, int power) {
      this.price = price;
      this.power = power;
    }
  
    @Override
    public int getPrice() {
      return price;
    }
  
    @Override
    public int getPower() {
      return power;
    }
  
  }
  ```

* **Monitor**

  ```java
  public class Monitor extends ComputerDevice {
  
    private int price;
    private int power;
  
    public Monitor(int price, int power) {
      this.price = price;
      this.power = power;
    }
  
    @Override
    public int getPrice() {
      return price;
    }
  
    @Override
    public int getPower() {
      return power;
    }
  
  }
  ```

* **Computer**

  ```java
  public class Computer extends ComputerDevice {
  
    private List<ComputerDevice> components = new ArrayList<>();
  
    public void addComponent(ComputerDevice component) {
      components.add(component);
    }
  
    public void removeComponent(ComputerDevice component) {
      components.remove(component);
    }
  
    @Override
    public int getPrice() {
      return components.stream().mapToInt(ComputerDevice::getPrice).sum();
    }
  
    @Override
    public int getPower() {
      return components.stream().mapToInt(ComputerDevice::getPower).sum();
    }
  }
  ```

* **Client**

  ```java
  public class Client {
  
    public static void main(String[] args) {
      Body body = new Body(100, 70);
      Keyboard keyboard = new Keyboard(5, 2);
      Monitor monitor = new Monitor(20, 30);
  
      Computer computer = new Computer();
      computer.addComponent(body);
      computer.addComponent(monitor);
      computer.addComponent(keyboard);
  
      int computerPrice = computer.getPrice();
      int computerPower = computer.getPower();
      System.out.println("Computer Power: " + computerPower);
      System.out.println("Computer Price: " + computerPrice);
    }
  
  }
  ```

<br>

Computer 객체에 부품 객체를 추가하는 메서드가 변경되었다. 즉, 기존에는 addMonitor, addKeyboard 등과 같이 부품에 해당하는 별도의 메서드가 Computer에 있었지만 이제는 **addComponent 메서드와 같이 일반화된 이름을 사용함으로써** 부품 종류에 관계 없이 동일한 메서드로 부품을 추가할 수 있도록 개선되었다.

 그러므로 ComputerDevice 클래스를 상속받는 클래스를 부품으로 사용한다면 Computer 클래스는 임의의 부품을 추가하면서도 코드를 변경하지 않아도 된다.

<br>

## 14.4. 컴퍼지트 패턴

**컴퍼지트 패턴(Composite Pattern)은** 부분-전체의 관계를 갖는 객체들을 정의할 때 유용하다. 즉, 부분 객체의 추가나 삭제 등이 있어도 전체 객체의 클래스 코드를 변경하지 않으면 컴퍼지트 패턴은 유용하다.