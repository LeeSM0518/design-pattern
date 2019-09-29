# 9. 옵서버 패턴

# 9.1. 여러 가지 방식으로 성적 출력하기

입력된 성적 값을 출력하는 프로그램

* **구현 사항**
  * 입력된 점수를 저장하는 ScoreRecord 클래스
  * 점수를 목록의 형태로 출력하는 DataSheetView 클래스
* **구현 결과**

<img src="../../capture/스크린샷 2019-09-23 오후 4.43.33.png">

<br>

### *클래스 설계*

<img src="../../capture/스크린샷 2019-09-22 오전 11.56.21.png">

<br>

### *순차 다이어그램*

<img src="../../capture/스크린샷 2019-09-22 오후 12.01.24.png">

1. 점수 추가시, ScoreRecord 클래스의 addScore 메서드 호출
2. ScoreRecord 클래스의 scores 필드값이 변경되었으므로 DataSheetView 클래스의 update 메서드 호출
3. 성적들을 가져오기 위해 DataSheetView 클래스는 ScoreRecord 클래스의 getScoreRecord 메서드 호출
4. 성적들을 출력해주는 displayScores 메서드 호출

<br>

### *코드*

**ScoreRecord.java**

```java
public class ScoreRecord {

  private List<Integer> scores = new ArrayList<>();  // 점수를 저장할 리스트
  private DataSheetView dataSheetView;	// 목록 형태로 점수를 출력하는 클래스

  public void setDataSheetView(DataSheetView dataSheetView) {
    this.dataSheetView = dataSheetView;
  }

  public void addScore(int score) {		// 새로운 점수를 추가할 메서드
    scores.add(score);                // scores 목록에 점수를 추가
    dataSheetView.update();           // scores가 변경됨을 통보
  }

  public List<Integer> getScoreRecord() {
    return scores;
  }

}
```

<br>

**DataSheetView.java**

```java
public class DataSheetView {

  private ScoreRecord scoreRecord;
  private int viewCount;

  public DataSheetView(ScoreRecord scoreRecord, int viewCount) {
    this.scoreRecord = scoreRecord;
    this.viewCount = viewCount;
  }

  // 점수의 변경을 통보받는다.
  public void update() {
    List<Integer> record = scoreRecord.getScoreRecord();	// 점수를 조회
    displayScores(record, viewCount);											// 조회된 점수를 viewCount 만큼 출력
  }

  private void displayScores(List<Integer> record, int viewCount) {
    System.out.print("List of " + viewCount + " entries: ");
    for (int i = 0; i < viewCount && i < record.size(); i++) {
      System.out.print(record.get(i) + " ");
    }
    System.out.println();
  }

}
```

<br>

**Client.java**

```java
public class Client {

  public static void main(String[] args) {
    ScoreRecord scoreRecord = new ScoreRecord();

    // 3개까지의 점수만 출력
    DataSheetView dataSheetView = new DataSheetView(scoreRecord, 3);

    scoreRecord.setDataSheetView(dataSheetView);

    for (int index = 1; index <= 5; index++) {
      int score = index * 10;
      System.out.println("Adding " + score);

      // 10 20 30 40 50 추가
      scoreRecord.addScore(score);
    }
  }

}
```

<br>

**실행 결과**

```
Adding 10
List of 3 entries: 10 

Adding 20
List of 3 entries: 10 20 

Adding 30
List of 3 entries: 10 20 30 

Adding 40
List of 3 entries: 10 20 30 

Adding 50
List of 3 entries: 10 20 30
```

<br>

# 9.2. 문제점

1. 성적을 다른 형태로 출력하고 싶다면 어떤 변경 작업을 해야 하는가?
2. 여러 가지 형태의 성적을 동시 혹은 순차적으로 출력하려면 어떤 변경 작업을 해야 하는가?

<br>

## 9.2.1. 성적을 다른 형태로 출력하는 경우

점수가 입력되면 점수 목록을 출력하는 대신 최소/최대 값만 출력하도록 해보자.

<br>

### *코드*

**ScoreRecord.java**

```java
public class ScoreRecord {

  private List<Integer> scores = new ArrayList<>();
  private MinMaxView minMaxView;

  // MinMaxView 를 설정
  public void setMinMaxView(MinMaxView minMaxView) {
    this.minMaxView = minMaxView;
  }

  public void addScore(int score) {
    scores.add(score);
    minMaxView.update();		// MinMaxView	에게 점수의 변경을 통보
  }

  public List<Integer> getScoreRecord() {
    return scores;
  }

}
```

<br>

**MinMax.java**

```java
public class MinMaxView {

  private ScoreRecord scoreRecord;

  public MinMaxView(ScoreRecord scoreRecord) {
    this.scoreRecord = scoreRecord;
  }

  // 점수의 변경을 통보받음
  public void update() {
    List<Integer> record = scoreRecord.getScoreRecord();	// 점수를 조회함
    displayMinMax(record);	// 최소 값과 최대 값을 출력
  }

  private void displayMinMax(List<Integer> record) {
    int min = Collections.min(record, null);
    int max = Collections.max(record, null);
    System.out.println("Min: " + min + " Max: " + max);
  }

}
```

<br>

**Client.java**

```java
public class Client {

  public static void main(String[] args) {
    ScoreRecord scoreRecord = new ScoreRecord();
    MinMaxView minMaxView = new MinMaxView(scoreRecord);

    scoreRecord.setMinMaxView(minMaxView);

    for (int index = 1; index <= 5; index++) {
      int score = index * 10;
      System.out.println("Adding " + score);

      scoreRecord.addScore(score);
    }
  }

}
```

<br>

**실행 결과**

```
Adding 10
Min: 10 Max: 10

Adding 20
Min: 10 Max: 20

Adding 30
Min: 10 Max: 30

Adding 40
Min: 10 Max: 40

Adding 50
Min: 10 Max: 50
```

<br>

### *변경 사항*

점수가 입력되었을 때 **지정된 특정 대상 클래스(처음에는 DataSheetView 클래스)** 에게 고정적으로 통보하도록 코딩이 되었는데 **다른 대상 클래스(MinMaxView 클래스)** 에게 점수가 입력되었음을 통보하려면 **ScoreRecord 클래스의 변경이 불가피하다.** 즉, OCP에 위배 된다.

* **변경된 ScoreRecord 클래스**

  ```java
  public class ScoreRecord {
  
    private List<Integer> scores = new ArrayList<>();
    private MinMaxView minMaxView;
  //  private DataSheetView dataSheetView;
  
    public void setMinMaxView(MinMaxView minMaxView) {
      this.minMaxView = minMaxView;
    }
  
  //  public void setDataSheetView(DataSheetView dataSheetView) {
  //    this.dataSheetView = dataSheetView;
  //  }
  
    public void addScore(int score) {
      scores.add(score);
      minMaxView.update();
  //    dataSheetView.update();
    }
  
    public List<Integer> getScoreRecord() {
      return scores;
    }
  
  }
  ```

<br>

## 9.2.2. 동시 혹은 순차적으로 성적을 출력하는 경우

성적이 입력되었을 때 최대 3개 목록, 최대 5개 목록, 최소/최대 값을 동시에 출력하거나, 처음에는 목록으로 출력하고 나중에는 최소/최대 값을 출력하도록 해보자.

목록으로 출력하는 것은 DataSheetView 클래스를 활용하고, 최소/최대 값을 출력하는 것은 MinMaxView 클래스를 활용할 수 있다.

<br>

### *코드*

**ScoreRecord.java**

```java
public class ScoreRecord {

  private List<Integer> scores = new ArrayList<>();


  private List<DataSheetView> dataSheetViews = new ArrayList<>();  // 복수 개의 DataSheetView
  private MinMaxView minMaxView;  // 1개의 MinMaxView

  public void addDataSheetView(DataSheetView dataSheetView) {	// DataSheetView 추가
    dataSheetViews.add(dataSheetView);
  }

  public void setMinMaxView(MinMaxView minMaxView) {	// MinMaxView 설정
    this.minMaxView = minMaxView;
  }

  public void addScore(int score) {
    scores.add(score);
    for (DataSheetView dataSheetView : dataSheetViews)
      dataSheetView.update();	// 각 DataSheetView에 값의 변경을 통보
    minMaxView.update();			// MinMaxView에 값의 변경을 통보
  }

  public List<Integer> getScoreRecord() {
    return scores;
  }

}
```

<br>

**MinMaxView.java**

```java
// 이전 예시 코드와 동일
public class MinMaxView {

  private ScoreRecord scoreRecord;

  public MinMaxView(ScoreRecord scoreRecord) {
    this.scoreRecord = scoreRecord;
  }

  public void update() {
    List<Integer> record = scoreRecord.getScoreRecord();
    displayMinMax(record);
  }

  private void displayMinMax(List<Integer> record) {
    int min = Collections.min(record, null);
    int max = Collections.max(record, null);
    System.out.println("Min: " + min + " Max: " + max);
  }

}
```

<br>

**DataSheetView.java**

```java
// 이전 예시 코드와 동일
public class DataSheetView {

  private ScoreRecord scoreRecord;
  private int viewCount;

  public DataSheetView(ScoreRecord scoreRecord, int viewCount) {
    this.scoreRecord = scoreRecord;
    this.viewCount = viewCount;
  }

  public void update() {
    List<Integer> record = scoreRecord.getScoreRecord();
    displayScores(record, viewCount);
  }

  private void displayScores(List<Integer> record, int viewCount) {
    System.out.print("List of " + viewCount + " entries: ");
    for (int i = 0; i < viewCount && i < record.size(); i++) {
      System.out.print(record.get(i) + " ");
    }
    System.out.println();
  }

}
```

<br>

**Client.java**

```java
public class Client {

  public static void main(String[] args) {
    ScoreRecord scoreRecord = new ScoreRecord();

    // 3개 목록의 DataSheetView 생성
    DataSheetView dataSheetView3 = new DataSheetView(scoreRecord, 3);

    // 5개 목록의 DataSheetView 생성
    DataSheetView dataSheetView5 = new DataSheetView(scoreRecord, 5);
    MinMaxView minMaxView = new MinMaxView(scoreRecord);	// MinMaxView 생성

    scoreRecord.addDataSheetView(dataSheetView3);	// 3개 목록 DataSheetView 설정
    scoreRecord.addDataSheetView(dataSheetView5);	// 5개 목록 DataSheetView 설정
    scoreRecord.setMinMaxView(minMaxView);        // MinMaxView 설정

    for (int index = 1; index <= 5; index++) {
      int score = index * 10;
      System.out.println("Adding " + score);

      // 추가할 때마다 최대 3개 목록, 최대 5개 목록 그리고 최소/최대 값이 출력됨
      scoreRecord.addScore(score);
    }
  }

}
```

<br>

**실행 결과**

```
Adding 10
List of 3 entries: 10 
List of 5 entries: 10 
Min: 10 Max: 10

Adding 20
List of 3 entries: 10 20 
List of 5 entries: 10 20 
Min: 10 Max: 20

Adding 30
List of 3 entries: 10 20 30 
List of 5 entries: 10 20 30 
Min: 10 Max: 30

Adding 40
List of 3 entries: 10 20 30 
List of 5 entries: 10 20 30 40 
Min: 10 Max: 40

Adding 50
List of 3 entries: 10 20 30 
List of 5 entries: 10 20 30 40 50 
Min: 10 Max: 50
```

<br>

### *변경 사항*

성적의 통보 대상이 변경된 것을 반영하려고 ScoreRecord 클래스의 코드를 수정하게 되었다. 이런 상황은 **성적 변경을 새로운 클래스에 통보할 때마다 반복적으로 발생하게 될 것이다.** 예를 들어 평균/표준편차를 출력하는 StatisticsView 클래스에게 성적 변경을 통보하려면 ScoreRecord는 다시 변경되어야 한다.

<br>

# 9.3. 해결책

문제 해결의 핵심은 **성적 통보 대상(DataSheetView, MinMaxView)이 변경되더라도 ScoreRecord 클래스를 그대로 재사용할 수 있어야 한다는 점이다.** 따라서 ScoreRecord 클래스에서 변화되는 부분을 식별하고 이를 일반화시켜야 한다.

<br>

- **ScoreRecord의 변화되던 부분**

  ```java
  public class ScoreRecord {
  
    private List<Integer> scores = new ArrayList<>();
    private MinMaxView minMaxView;
  //  private DataSheetView dataSheetView;
  
    public void setMinMaxView(MinMaxView minMaxView) {
      this.minMaxView = minMaxView;
    }
  
  //  public void setDataSheetView(DataSheetView dataSheetView) {
  //    this.dataSheetView = dataSheetView;
  //  }
  
    public void addScore(int score) {
      scores.add(score);
      minMaxView.update();
  //    dataSheetView.update();
    }
  
    public List<Integer> getScoreRecord() {
      return scores;
    }
  
  }
  ```

  * 통보 대상이 필드값으로 존재하기 때문에 지속적으로 바뀐다.
  * 통보 대상에 대한 setter 메소드가 지속적으로 바뀐다.
  * 변경 통보에 대한 메소드가 지속적으로 바뀐다.

<br>

### *개선한 ScoreRecord 클래스 다이어 그램*

DataSheetView와 MinMaxView 클래스에게 성적 변경을 통보할 수 있도록 개선하였다.

<img src="../../capture/스크린샷 2019-09-22 오후 7.16.58.png">

* 원래 ScoreRecord에서 성적 추가 및 변경 통보를  모두 하였으나, **변경 통보를 Subject라는 클래스에서 구현해놓고 통보 대상들을 관리 하도록 변경하였다.** Subject 클래스는 attach 메서드와 detach 메소드로 성적 변경에 관심이 있는 대상 객체를 추가하거나 제거한다. 
* 성적 변경의 통보 수신이라는 측면에서 DataSheetView 클래스와 MinMaxView 클래스는 동일하므로 **Observer 인터페이스를 DataSheetView, MinMaxView 클래스가 구현하여 일반화를 하고, Subject 클래스가 Observer를 사용하여 성적 변경을 통보하는 연관 관계로 변경하였다.**

<br>

### *코드*

**Observer.java**

```java
public interface Observer {       // 추상화된 통보 대상

  public abstract void update();	// 데이터의 변경을 통보했을 때 처리하는 메서드

}
```

<br>

**Subject.java**

```java
public abstract class Subject {		// 추상화된 통보 대상을 추가, 제거 및 변경 사항을 통보하는 클래스

  // 추상화된 통보 대상 리스트
  private List<Observer> observers = new ArrayList<>();

  // 통보 대상 추가
  public void attach(Observer observer) {
    observers.add(observer);
  }

  // 통보 대상 제거
  public void detach(Observer observer) {
    observers.remove(observer);
  }

  // 통보 대상들에게 변경을 통보함
  public void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }

}
```

* 이처럼 성적 변경에 관심이 있는 대상 객체들의 관리를 Subject 클래스에서 구현하고 ScoreRecord가 상속받게 함으로써, **ScoreRecord 클래스는 이제 DataSheetView와 MinMaxView를 직접 참조할 필요가 없게 되었다.** 그러므로 통보 대상에 변경이 일어나도 ScoreRecord 클래스에는 영향이 없다.

<br>

**ScoreRecord.java**

```java
public class ScoreRecord extends Subject{		// 구체적인 변경 감시 대상 클래스

  private List<Integer> scores = new ArrayList<>();

  public void addScore(int score) {
    scores.add(score);
    
    // 데이터가 변경되면 Subject 클래스의 notifyObservers 메서드를 호출해
    // 각 옵서버에게 데이터의 변경을 통보한다.
    notifyObservers();
  }

  public List<Integer> getScoreRecord() {
    return scores;
  }

}
```

<br>

**DataSheetView.java**

```java
// Observer의 기능, 즉 update 메서드를 구현함으로써 통보 대상이 됨
public class DataSheetView implements Observer {

  private ScoreRecord scoreRecord;
  private int viewCount;

  public DataSheetView(ScoreRecord scoreRecord, int viewCount) {
    this.scoreRecord = scoreRecord;
    this.viewCount = viewCount;
  }

  public void update() {
    List<Integer> record = scoreRecord.getScoreRecord();
    displayScores(record, viewCount);
  }

  private void displayScores(List<Integer> record, int viewCount) {
    System.out.print("List of " + viewCount + " entries: ");
    for (int i = 0; i < viewCount && i < record.size(); i++) {
      System.out.print(record.get(i) + " ");
    }
    System.out.println();
  }

}
```

<br>

**MinMaxView.java**

```java
// Observer의 기능, 즉 update 메서드를 구현함으로써 통보 대상이 됨
public class MinMaxView implements Observer {

  private ScoreRecord scoreRecord;

  public MinMaxView(ScoreRecord scoreRecord) {
    this.scoreRecord = scoreRecord;
  }

  public void update() {
    List<Integer> record = scoreRecord.getScoreRecord();
    displayMinMax(record);
  }

  private void displayMinMax(List<Integer> record) {
    int min = Collections.min(record, null);
    int max = Collections.max(record, null);
    System.out.println("Min: " + min + " Max: " + max);
  }

}
```

<br>

**Client.java**

```java
public class Client {

  public static void main(String[] args) {
    ScoreRecord scoreRecord = new ScoreRecord();

    DataSheetView dataSheetView3 = new DataSheetView(scoreRecord, 3);
    DataSheetView dataSheetView5 = new DataSheetView(scoreRecord, 5);
    MinMaxView minMaxView = new MinMaxView(scoreRecord);

    // Observer 추가
    scoreRecord.attach(dataSheetView3);
    scoreRecord.attach(dataSheetView5);
    scoreRecord.attach(minMaxView);

    for (int index = 1; index <= 5; index++) {
      int score = index * 10;
      System.out.println("Adding " + score);
      scoreRecord.addScore(score);
    }
  }

}
```

<br>

**실행 결과**

```
Adding 10
List of 3 entries: 10 
List of 5 entries: 10 
Min: 10 Max: 10
Adding 20
List of 3 entries: 10 20 
List of 5 entries: 10 20 
Min: 10 Max: 20
Adding 30
List of 3 entries: 10 20 30 
List of 5 entries: 10 20 30 
Min: 10 Max: 30
Adding 40
List of 3 entries: 10 20 30 
List of 5 entries: 10 20 30 40 
Min: 10 Max: 40
Adding 50
List of 3 entries: 10 20 30 
List of 5 entries: 10 20 30 40 50 
Min: 10 Max: 50
```

<br>

### *합계/평균 출력 추가*

**StatisticsView.java**

```java
public class StatisticsView implements Observer {

  private ScoreRecord scoreRecord;

  public StatisticsView(ScoreRecord scoreRecord) {
    this.scoreRecord = scoreRecord;
  }

  @Override
  public void update() {
    List<Integer> record = scoreRecord.getScoreRecord();
    displayStatistics(record);
  }

  private void displayStatistics(List<Integer> record) {
    int sum = 0;
    for (int score : record)
      sum += score;
    float average = (float) sum / record.size();
    System.out.println("Sum: " + sum + " Average: " + average);
  }

}
```

<br>

**Client.java**

```java
public class Client {

  public static void main(String[] args) {
    ScoreRecord scoreRecord = new ScoreRecord();

    DataSheetView dataSheetView3 = new DataSheetView(scoreRecord, 3);
    DataSheetView dataSheetView5 = new DataSheetView(scoreRecord, 5);
    MinMaxView minMaxView = new MinMaxView(scoreRecord);
    StatisticsView statisticsView = new StatisticsView(scoreRecord);

    scoreRecord.attach(dataSheetView3);
    scoreRecord.attach(dataSheetView5);
    scoreRecord.attach(minMaxView);
    scoreRecord.attach(statisticsView);

    for (int index = 1; index <= 5; index++) {
      int score = index * 10;
      System.out.println("Adding " + score);
      scoreRecord.addScore(score);
      System.out.println();
    }
  }

}
```

- Subject 클래스의 attach 메서드를 호출해 **StatisticsView 객체를 ScoreRecord 클래스의 관심 객체로 등록한다.**
- 관심 객체의 관리는 Subject 클래스에서 수행하므로 **ScoreRecord 클래스는 어떤 변경도 하지 않고 합계/평균 출력을 할 수 있게 된다.**

<br>

**실행 결과**

```
Adding 10
List of 3 entries: 10 
List of 5 entries: 10 
Min: 10 Max: 10
Sum: 10 Average: 10.0

Adding 20
List of 3 entries: 10 20 
List of 5 entries: 10 20 
Min: 10 Max: 20
Sum: 30 Average: 15.0

Adding 30
List of 3 entries: 10 20 30 
List of 5 entries: 10 20 30 
Min: 10 Max: 30
Sum: 60 Average: 20.0

Adding 40
List of 3 entries: 10 20 30 
List of 5 entries: 10 20 30 40 
Min: 10 Max: 40
Sum: 100 Average: 25.0

Adding 50
List of 3 entries: 10 20 30 
List of 5 entries: 10 20 30 40 50 
Min: 10 Max: 50
Sum: 150 Average: 30.0
```

<br>

# 9.4. 옵서버 패턴

옵서버 패턴(Observer Pattern)은 데이터의 변경이 발생했을 경우 상대 클래스나 객체에 의존하지 않으면서 데이터 변경을 통보하고자 할 때 유용하다. 

<br>

### *옵서버 패턴의 컬레보레이션*

<img src="../../capture/스크린샷 2019-09-23 오후 6.03.22.png">

* **Observer** : <u>데이터의 변경을 통보 받는 인터페이스.</u> 즉, Subject에서는 Observer 인터페이스의 update 메서드를 호출함으로써 ConcreteSubject의 데이터 변경을 ConcreteObserver에게 통보한다.
* **Subject** : <u>ConcreteObserver 객체를 관리하는 요소.</u> Observer 인터페이스를 참조해서 ConcreteObserver를 관리하므로 ConcreteObserver의 변화에 독립적이다.
* **ConcreteSubject** : <u>변경 관리 대상이 되는 데이터가 있는 클래스.</u> 데이터 변경을 위한 메서드인 setState가 있으며 setState에서는 자신의 데이터인 subjectState를 변경하고 Subject의 notifyObserver 메서드를 호출해서 ConcreteObserver 객체에 변경을 통보한다.
* **ConcreteObserver** : <u>ConcreteSubject의 변경을 통보받는 클래스.</u> Observer 인터페이스의 update 메서드를 구현함으로써 변경을 통보받는다. 변경된 데이터는 ConcreteSubject의 getState 메서드를 호출함으로써 변경을 조회한다.

<br>

### *옵서버 패턴의 순차 다이어그램*

<img src="../../capture/스크린샷 2019-09-23 오후 2.59.24.png">

