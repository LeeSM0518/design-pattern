# Strategy pattern

전략 패턴

</br>

# 5.1. 로봇 만들기

만들 로봇은 아톰(Atom 클래스)과 태권V (TaekwonV 클래스)다. 이 두 로봇은 공격 기능과 이동 기능이 있다. 아톰은 공격할 때 주먹만 사용하지만 하늘을 날 수가 있고 태권 V는 미사일로 공격할 수는 있지만 날아다니지 못하고 걷기만 할 수 있다.

* **로봇 설계**

  <img src="../../capture/스크린샷 2019-08-14 오후 9.53.56.png">

  > Atom 클래스와 TaekwonV 클래스는 Robot 이라는 추상 클래스의 자식 클래스로 설정

  * 아톰과 태권V은 둘 다 이동 기능과 공격 기능이 있지만, 서로 다르기 때문에 **Robot 클래스에서 attack과 move 메서드를 추상 메서드로 설정해 자식 클래스에서 각각 정의하도록** 했다.

</br>

* **Robot 클래스**

  ```java
  public abstract class Robot {
  
    private String name;
  
    public Robot(String name) {
      this.name = name;
    }
  
    public String getName() {
      return name;
    }
  
    public abstract void attack();
    public abstract void move();
  
  }
  ```

* **TaekwonV 클래스**

  ```java
  public class TaekwonV extends Robot {
  
    public TaekwonV(String name) {
      super(name);
    }
  
    @Override
    public void attack() {
      System.out.println("I have Missile and can attack with it");
    }
  
    @Override
    public void move() {
      System.out.println("I can only walk.");
    }
    
  }
  ```

* **Atom 클래스**

  ```java
  public class Atom extends Robot {
  
    public Atom(String name) {
      super(name);
    }
  
    @Override
    public void attack() {
      System.out.println("I have strong punch and can attack with it.");
    }
  
    @Override
    public void move() {
      System.out.println("I can fly.");
    }
    
  }
  ```

* **Client 클래스**

  ```java
  public class Client {
  
    public static void main(String[] args) {
      Robot taekwonV = new TaekwonV("TaekwonV ");
      Robot atom = new Atom("Atom");
  
      System.out.println("My name is " + taekwonV.getName());
      taekwonV.move();
      taekwonV.attack();
  
      System.out.println();
  
      System.out.println("My name is " + atom.getName());
      atom.move();
      atom.attack();
    }
  
  }
  ```

* **실행 결과**

  ```
  My name is TaekwonV 
  I can only walk.
  I have Missile and can attack with it
  
  My name is Atom
  I can fly.
  I have strong punch and can attack with it.
  ```

</br>

# 5.2. 문제점

* 기존 로봇의 공격 또는 이동 방법을 **수정하려면 어떤 변경 작업을 해야 하는가?**
* 새로운 로봇을 만들어 기존의 공격 또는 이동 방법을 **추가하거나 수정하려면?**

</br>

## 5.2.1. 기존 로봇의 공격과 이동 방법을 수정하는 경우

가령 아톰이 날 수는 없고 오직 걷게만 만들고 싶다면 Atom 클래스의 move 메서드를 수정해야 한다.

```java
public class Atom extends Robot {

  public Atom(String name) {
    super(name);
  }

  @Override
  public void attack() {
    System.out.println("I have strong punch and can attack with it.");
  }

  @Override
  public void move() {
    System.out.println("I can only walk.");	// 오직 걸을 수 밖에 없다.
  }

}
```

> 이는 새로운 기능으로 변경하려고 **기존 코드의 내용을 수정해야 하므로 OCP에 위배된다.** 또한 Atom 클래스의 move 메서드와 TaekwonV 클래스의 move 메서드의 기능이 중복되므로 많은 문제를 야기하는 원인이 된다.

</br>

## 5.2.2. 새로운 로봇에 공격/이동 방법을 추가/수정하는 경우

현재 설계는 로봇 자체가 캡슐화 단위이므로 새로운 로봇을 추가하기가 매우 쉽다.

선가드(Sungard 클래스)를 추가하려면 다음과 같이 선가드를 위한 클래스를 작성하고 이를 **로봇의 서브 클래스로 두면 된다.**

* **새로운 로봇의 추가**

<img src="../../capture/스크린샷 2019-08-14 오후 9.54.50.png">

그러나 새로운 로봇에 기존의 공격 또는 이동 방법을 **추가하거나 변경하려고 하면 문제가 발생한다.**

</br>

# 5.3 해결책

설계에서의 문제를 해결하려면 무엇이 변화되었는지 찾아야 한다. 변화된 것을 찾은 후에는 이를 클래스로 **캡슐화해야 한다.**

위의 예제에서 문제를 발생시키는 요인은 **로봇의 이동 방식과 공격 방식의 변화다.** 이러한 기능을 별다른 코드 변경 없이 **제공받거나** 기존의 공격이나 이동 방식을 다른 공격이나 이동 방식으로 **쉽게 변경할 수 있어야 한다.**

따라서, 캡슐화하려면 외부에서 구체적인 이동 방식과 공격 방식을 담은 **구체적인 클래스들을 은닉해야 한다.**

이를 위해 공격과 이동을 위한 **인터페이스를 각각 만들고 이들을 실제 실현한 클래스를 만들어야 한다.**

* **공격 전략 인터페이스**

<img src="../../capture/스크린샷 2019-08-14 오후 10.08.27.png" width=600>

* **이동 전략 인터페이스**

<img src="../../capture/스크린샷 2019-08-14 오후 10.06.23.png" width=600>

</br>

* **개선된 설계**

  <img src="../../capture/스크린샷 2019-08-14 오후 10.16.29.png">

  * Robot 클래스의 입장에서 보면 구체적인 이동 방식과 공격 방식이 MovingStrategy와 AttackStrategy **인터페이스에 의해 캡슐화되어 있다.** 따라서 이들 기능을 이용하는 로봇 객체와는 상관 없이 향후 등장할 이동 방식과 공격 방식의 변화뿐만 아니라 현재 **변화도 잘 처리할 수 있게 된다.**
  * 즉, 새로운 기능의 추가가 **기존의 코드에 영향에 미치지 못하게 하므로 OCP를 만족하는 설계가 된다.**
  * 이렇게 변경된 새로운 구조에서는 외부에서 로봇 객체의 이동 방식과 공격 방식을 **임의대로 바꾸도록 해주는 메서드(setMovingStrategy, setAttackStrategy)가 필요하다.**  이런 변경이 가능한 이유는 상속 대신 **집약 관계를 이용했기 때문이다.**

</br>

* **개선된 설계의 코드**

  ```java
  // 로봇 클래스
  public abstract class Robot {
  
    private String name;
    private MovingStrategy movingStrategy;
    private AttackStrategy attackStrategy;
  
    public Robot(String name) {
      this.name = name;
    }
  
    public String getName() {
      return name;
    }
  
    public void move() {
      movingStrategy.movie();
    }
    
    public void attack() {
      attackStrategy.attack();
    }
    
    public void setMovingStrategy(MovingStrategy movingStrategy) {
      this.movingStrategy = movingStrategy;
    }
    
    public void setAttackStrategy(AttackStrategy attackStrategy) {
      this.attackStrategy = attackStrategy;
    }
  
  }
  
  //
  ```

  