# 2. 객체지향 원리

# 2.1. 추상화

**추상화란** 어떤 영역에서 필요로 하는 속성이나 행동을 추출하는 작업을 의미한다.

* 추상화가 가능한 개체들은 개체가 소유한 특성의 이름으로 하나의 **집합(class)을** 이룬다.



* **예시) 자동차 종류마다 엔진 오일을 교환하는 방식**

  * 자동차 종류에 따른 swith문

    ```java
    switch(자동차 종류)
      case 아우디:			// 아우디 엔진 오일을 교환하는 과정을 기술
    	case 벤츠:			 // 벤츠 엔진 오일을 교환하는 과정을 기술
    	case BMW:				// BMW 엔진 오일을 교환하는 과정을 기술
    end switch
    ```

  * 자동차 대신 이들의 추상화 개념인 자동차를 이용한 코드

    ```java
    void changeEngineOil(Car c) {
      c.changeEngineOil();
    }
    ```

    > 인자의 아우디, 벤츠, BMW의 추상화 개념인 Car를 사용했기 때문에, 이 코드는 어떤 새로운 자동차가 추가되더라도 변경할 필요가 없다. 



# 2.2. 캡슐화

소프트웨어 공학에서 요구사항 변경에 대처하는 고전적인 설계 원리로는 **응집도(cohesion) 와 결합도(coupling)가** 있다.

* **응집도** : 클래스나 모듈안의 요소들이 얼마나 밀접하게 관련되어 있는지를 나타낸다.
* **결합도** : 어떤 기능을 실행하는 데 다른 클래스나 모듈들에 얼마나 의존적인지를 나타낸다.

> **높은 응집도와 낮은 결합도를** 유지할 수 있도록 설계해야 요구사항을 변경할 때 유연하게 대처할 수 있다.



**캡슐화는** 정보 은닉(information hiding)을 통해 높은 응집도와 낮은 결합도를 갖도록 한다.

* **정보 은닉** : 알 필요가 없는 정보는 외부에서 접근하지 못하도록 제한하는 것이다.



* **예시) 배열 스택**

  ```java
  package object_oriented_principle;
  
  public class ArrayStack {
  
    public int top;
    public int[] itemArray;
    public int stackSize;
  
    public ArrayStack(int stackSize) {
      itemArray = new int[stackSize];
      top = -1;
      this.stackSize = stackSize;
    }
  
    public boolean isEmpty() {
      return (top == -1);
    }
  
    public boolean isFull() {
      return (top == this.stackSize - 1);
    }
  
    public void push(int item) {
      if (isFull()) {
        System.out.println("Inserting fail! Array Stack is full!");
      } else {
        itemArray[++top] = item;
        System.out.println("Inserted Item : " + item);
      }
    }
  
    public int pop() {
      if (isEmpty()) {
        System.out.println("Deleting fail! Array Stack is empty!");
        return -1;
      } else {
        return itemArray[top--];
      }
    }
  
    public int peek() {
      if (isEmpty()) {
        System.out.println("Peeking fail! Array Stack is empty");
        return -1;
      } else {
        return itemArray[top];
      }
    }
  
  }
  ```

  > 이 코드의 주의할 점은 자료구조에 모두 **public 키워드를** 붙여 외부에 공개되어 있다는 점이다. 즉, push 메서드나 pop 메서드를 사용하지 않고 직접 배열에 값을 저장하거나 수정할 수 있다. 그래서 이 코드는 **정보 은닉을 하지 못 하고 있는 코드이다.**

* **정보 은닉 코드**

  자료구조의 형태와 관련이 있는 것들은 **private 키워드를** 붙여 은닉한다.

  ```java
  private int top;
  private int[] itemArray;
  private int top;
  ```

* **main 메소드**

  정보 은닉한 ArrayStack 클래스를 사용하는 방법

  ```java
  public class StackClient {
    public static void main(String[] args) {
      ArrayListStack st = new ArrayListStack(10);
      st.push(20);
      System.out.println(st.peek());
    }
  }
  ```



# 2.3. 일반화 관계

## 2.3.1. 일반화는 또 다른 캡슐화

일반화 관계는 객체지향 프로그래밍 관점에서는 상속 관계라 한다. 따라서 속성이나 기능의 재사용만 강조해서 사용하는 경우가 많다. **이는 일반화 관계를 극히 한정되게 바라보는 시각이다.** 

**일반화(generalization)는** "여러 개체들이 가진 공통된 특성을 부각시켜 하나의 개념이나 법칙으로 성립시키는 과정"이라 한다.

* **예시) 과일**

  : 사과, 배, 바나, 오렌지 등은 과일의 한 종류이므로 과일을 **특수화(specialization)한** 개념이다.

  <img src="../../capture/스크린샷 2019-07-29 오후 8.21.22.png" width=500>



* **예시) 장바구니에 있는 과일 가격의 총합 구하는 코드**

  * **switch 활용**

    ```java
    가격 총합 = 0;
    while(장바구니에 과일이 있다) {
      switch(과일 종류) {
        case 사과:
          가격 총합 = 가격 총합 + 사과 가격;
        case 배:
          가격 총합 = 가격 총합 + 배 가격;
        case 바나나:
          가격 총합 = 가격 총합 + 바나나 가격;
        case 오렌지:
          가격 총합 = 가격 총합 + 오렌지 가격
      }
    }
    ```

    > 위의 코드는 새로운 과일의 종류가 나타날 때마다 항상 코드를 수정해야 하므로 변경사항에 **유연성 있게 대처하지 못한다.**

  * **다형성 활용**

    ```java
    int computeTotalPrice(LinkedList<Fruit> f) {
      int total = 0;
      Iterator<Fruit> itr = f.iterator();
      
      while(itr.hasNext()) {
        Fruit curFruit = itr.next();
        // calculatePrice 메소드는 실제 과일 객체의 종류에 따라 다르게 실행된다.
        total = total + curFruit.calculatePrice();
      }
      return total;
    }
    ```

    > 일반화 관계는 외부 세계에 자식 클래스를 캡슐화하는 개념으로 볼 수 있으며, 이때 캡슐화 개념은 한 클래스 안에 있는 속성 및 연산들의 캡슐화에 한정되지 않고 **일반화 관계를 통해 클래스 자체를 캡슐화하는 것으로 확장된다.**



* **예시) 일반화는 또 다른 캡슐화**

  사람이 자동차를 운전하는 상황

  <img src="../../capture/스크린샷 2019-07-29 오후 8.35.43.png">

  