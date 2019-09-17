# 8. 커맨드 패턴

# 8.1. 만능 버튼 만들기

눌리면 특정 기능을 수행하는 버튼을 구현해보자.

<br>

* **램프 켜는 버튼을 설계한 클래스 다이어그램**

  <img src="../../capture/스크린샷 2019-09-17 오후 7.35.12.png">

<br>

**코드**

* Button.java

  ```java
  public class Button {
  
    private Lamp theLamp;
  
    public Button(Lamp theLamp) {
      this.theLamp = theLamp;
    }
  
    public void pressed() {
      theLamp.turnOn();
    }
  
  }
  
  ```

* Lamp.java

  ```java
  public class Lamp {
  
    public void turnOn() {
      System.out.println("Lamp On");
    }
  
  }
  ```

* Client.java

  ```java
  public class Client {
  
    public static void main(String[] args) {
      Lamp lamp = new Lamp();
      Button lampButton = new Button(lamp);
      lampButton.pressed();
    }
  
  }
  ```

<br>

# 8.2. 문제점

1. 누군가 버튼을 눌렀을 때 램프가 켜지는 대신 다른 기능을 실행하게 하려면 어떤 변경 작업을 해야 하는가? 예를 들어 버튼을 눌렀을 때 알람이 시작되게 하려면?
2. 버튼을 누르는 동작에 따라 다른 기능을 실행하게 하려면 어떤 변경 작업을 해야 하는가? 예를 들어 버튼을 처음 눌렀을 때는 램프를 켜고, 두 번째 눌렀을 때는 알람을 동작하게 하려면?

<br>

## 8.2.1. 버튼을 눌렀을 때 다른 기능을 실행하는 경우

램프를 켜는 대신 알람을 시작하게 하려면 Button 클래스의 pressed() 메서드를 수정해야 한다.

<br>

**코드**

* Alarm.java

  ```java
  public class Alarm {
    
    public void start() {
      System.out.println("Alarming...");
    }
    
  }
  ```

* Button.java

  ```java
  public class Button {
  
  //  private Lamp theLamp;
    private Alarm theAlarm;
  
  //  public Button(Lamp theLamp) {
  //    this.theLamp = theLamp;
  //  }
  
    public Button(Alarm theAlarm) {
      this.theAlarm = theAlarm;
    }
  
    public void pressed() {
  //    theLamp.turnOn();
      theAlarm.start();
    }
  
  }
  ```

* Client.java

  ```java
  public class Client {
  
    public static void main(String[] args) {
  //    Lamp lamp = new Lamp();
  //    Button lampButton = new Button(lamp);
  //    lampButton.pressed();
      Alarm alarm = new Alarm();
      Button alarmButton = new Button(alarm);
      alarmButton.pressed();
    }
  
  }
  ```

Button의 기능을 변경하려고 **기존 Button 클래스의 코드를 수정하는 것은 OCP에 위배된다.** 즉, pressed 메서드 전체를 변경해야 기능이 변경되므로 OCP에 위배된다.

<br>

## 8.2.2. 버튼을 누르는 동작에 따라 다른 기능을 실행하는 경우

버튼을 처음 눌렀을 때는 램프를 켜고 두 번 눌렀을 때는 알람을 동작하게 할 경우에 Button 클래스는 2가지 기능(램프 켜기와 알람 동작)을 모두 구현할 수 있어야 한다.

<br>

**코드**

* Mode.enum

  ```java
  public enum Mode {
    LAMP, ALARM
  }
  ```

* Lamp.class

  ```java
  public class Lamp {
  
    public void turnOn() {
      System.out.println("Lamp On");
    }
  
  }
  ```

* Alarm.class

  ```java
  public class Alarm {
  
    public void start() {
      System.out.println("Alarming...");
    }
  
  }
  ```

* Button.class

  ```java
  public class Button {
  
    private Lamp theLamp;
    private Alarm theAlarm;
    private Mode theMode;
  
    public Button(Lamp theLamp, Alarm theAlarm) {
      this.theLamp = theLamp;
      this.theAlarm = theAlarm;
    }
  
    // 램프 모드 또는 알람 모드를 설정
    public void setMode(Mode theMode) {
      this.theMode = theMode;
    }
  
    // 설정된 모드에 따라 램프를 켜거나 알람을 울림
    public void pressed() {
      switch (theMode) {
        case LAMP:
          theLamp.turnOn();
          break;
        case ALARM:
          theAlarm.start();
          break;
      }
    }
  
  }
  ```

* Client.class

  ```java
  public class Client {
  
    public static void main(String[] args) {
      Lamp lamp = new Lamp();
      Alarm alarm = new Alarm();
      Button button = new Button(lamp, alarm);
  
      button.setMode(Mode.LAMP);
      button.pressed();
  
      button.setMode(Mode.ALARM);
      button.pressed();
    }
  
  }
  ```

이 경우도 역시 버튼을 눌렀을 때의 기능을 변경하기 위해 다시 Button 클래스의 코드를 수정했다. 이러한 수정은 버튼 기능을 새로 추가할 때마다 반복적으로 발생할 것이다. 즉, Button 클래스에 **새로 기능을 추가할 때마다 코드를 수정해야 한다면 Button 클래스는 재사용하기 어렵다.**

<br>

# 8.3. 해결책

위와 같은 문제점들을 보안하기 위해서는 Button 클래스의 pressed 메서드에서 구체적인 기능을 직접 구현하는 대신 실행될 기능을 Button 클래스 외부에서 제공받아 캡슐화해 pressed 메서드에서 호출하는 방법을 사용해야 한다.

<br>

**개선된 Button 클래스의 다이어그램**

<img src="../../capture/스크린샷 2019-09-17 오후 8.25.41.png">

* Button 클래스는 기능을 실행할 때 Lamp나 Alarm을 직접 실행하는 것이 아니라 **Command 인터페이스의 execute 메서드를 호출하여 실행한다.**

<br>

**코드**

* Command.interface

  ```java
  public interface Command {
    public abstract void execute();
  }
  ```

* LampOnCommand.class

  ```java
  public class LampOnCommand implements Command {
  
    private Lamp theLamp;
  
    public LampOnCommand(Lamp theLamp) {
      this.theLamp = theLamp;
    }
  
    @Override
    public void execute() {
      theLamp.turnOn();
    }
  
  }
  ```

* Lamp.class

  ```java
  public class Lamp {
  
    public void turnOn() {
      System.out.println("Lamp On");
    }
  
  }
  ```

* AlarmOnCommand.class

  ```java
  public class AlarmOnCommand implements Command {
  
    private Alarm theAlarm;
  
    public AlarmOnCommand(Alarm theAlarm) {
      this.theAlarm = theAlarm;
    }
  
    @Override
    public void execute() {
      theAlarm.start();
    }
  
  }
  ```

* Alarm.class

  ```java
  public class Alarm {
  
    public void start() {
      System.out.println("Alarming...");
    }
  
  }
  ```

* Button.class

  ```java
  public class Button {
  
    private Command theCommand;
  
    public Button(Command theCommand) {
      setCommand(theCommand);
    }
  
    public void setCommand(Command newCommand) {
      this.theCommand = newCommand;
    }
  
    public void pressed() {
      theCommand.execute();
    }
  
  }
  ```

* Client.class

  ```java
  public class Client {
  
    public static void main(String[] args) {
      Lamp lamp = new Lamp();
      Command lampOnCommand = new LampOnCommand(lamp);
  
      Button button1 = new Button(lampOnCommand);
      button1.pressed();
  
      Alarm alarm = new Alarm();
      Command alarmOnCommand = new AlarmOnCommand(alarm);
  
      Button button2 = new Button(alarmOnCommand);
      button2.pressed();
  
      button2.setCommand(lampOnCommand);
      button2.pressed();
    }
  
  }
  ```

버튼을 눌렀을 때 필요한 임의의 기능은 Command 인터페이스를 구현한 클래스의 객체를 Button 객체에 설정해서 실행할 수 있다. 따라서 Button 클래스는 소스 코드를 변경하지 않으면서도 다양한 동작을 구현할 수 있게 된다.

<br>

**램프를 켜거나 끄는 기능을 추가한 Button 클래스**

<img src="../../capture/스크린샷 2019-09-17 오후 8.59.46.png" width=500>

<br>

**코드**

* Command.interface

  ```java
  public interface Command {
    public abstract void execute();
  }
  ```

* Button.class

  ```java
  // 위의 예시의 Button 클래스와 동일
  ```

* Lamp.class

  ```java
  public class Lamp {
    public void turnOn() {
      System.out.println("Lamp On");
    }
    
    public void turnOff() {
      System.out.println("Lamp Off");
    }
  }
  ```

* LampOnCommand.class

  ```java
  public class LampOnCommand implements Command {
    private Lamp theLamp;
    
    public LampOnCommand(Lamp theLamp) {
      this.theLamp = theLamp;
    }
    
    public void execute() {
      theLamp.turnOn();
    }
  }
  ```

* LampOffCommand.class

  ```java
  public class LampOffCommand implements Command {
    private Lamp theLamp;
    
    public LampOnCommand(Lamp theLamp) {
      this.theLamp = theLamp;
    }
    
    public void execute() {
      theLamp.turnOff();
    }
  }
  ```

* Client.class

  ```java
  package command_pattern;
  
  public class Client {
  
    public static void main(String[] args) {
      Lamp lamp = new Lamp();
      Command lampOnCommand = new LampOnCommand(lamp);
      Command lampOffCommand = new LampOffCommand(lamp);
  
      Button button = new Button(lampOnCommand);
      button.pressed();
      
      button.setCommand(lampOffCommand);
      button.pressed();
    }
  
  }
  ```

<br>

