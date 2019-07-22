# 1. 객체지향 모델링

# 1.1. 모델링

* **모델의 역할**
  * 서로의 해석을 공유해 합의를 이루거나 해석의 타당성을 검토한다.
  * 현재 시스템 또는 앞으로 개발할 시스템의 원하는 모습을 가시화한다.
  * 시스템의 구조와 행위를 명세화할 수 있으며 시스템을 구축하는 틀을 제공한다.



모델은 **추상화(abstraction)** 에 바탕을 두고 만들어져야 한다.

* **추상화** : 대상에 대한 특정 관점에서 관련이 있는 점을 부각시키고 관련이 없는 면은 무시하는 것



# 1.2. UML

**UML(Unified Modeling Language)** : 시스템을 모델로 표현해주는 언어 중에 대표적인 언어이다. 이것은 요구 분석, 시스템 설계, 시스템 구현 등의 시스템 개발 과정에서 개발자 사이의 의사소통이 원활하게 이루어지도록 <u>표준화한 통합 모델링 언어이다.</u>



# 1.3. 클래스 다이어그램

**클래스 다이어그램** : 시스템을 구성하는 클래스 사이의 관계를 표현한다. 주요 구성 요소는 클래스와 관계다.



## 1.3.1. 클래스

**클래스란** 동일한 속성과 행위를 수행하는 객체의 집합이다. 그리고 객체를 생성하는 설계도로 간주할 수 있다.

* **Ex)** 학생 클래스는 이름, 전공, 학번, 수강 과목이라는 **공통적인 데이터를(속성)** 가지며 **과목을 수강할(행위)** 책임이 있다.

  

**UML 클래스 표현 예**

<img src="../capture/스크린샷 2019-07-19 오후 6.38.15.png" width=200>

* **가장 윗부분** : 클래스 이름 (학생)

* **중간 부분** : 클래스의 특징을 나타내는 속성 (이름, 전공, …)

* **마지막 부분** : 클래스가 수행하는 책임, 즉 연산들 (수강하다)

* **속성과 연산들의 접근 제어자 표현** : 위의 예시에서 속성과 연산을 보면 '-', '+' 와 같은 부호를 사용한다.

  | 접근 제어자 | 표시 | 설명                                                         |
  | ----------- | ---- | ------------------------------------------------------------ |
  | public      | +    | 어떤 클래스의 객체에서든 접근 가능                           |
  | private     | -    | 이 클래스에서 생성된 객체들만 접근 가능                      |
  | protected   | #    | 이 클래스와 동일 패키지에 있거나 상속 관계에 있는 하위 클래스의 객체들만 접근 가능 |
  | package     | ~    | 동일 패키지에 있는 클래스의 객체들만 접근 가능               |



**속성과 연산 UML 표기법** : '[ ]' 부분은 생략 가능

| 분류 | 표기 방법                                                    |
| ---- | ------------------------------------------------------------ |
| 속성 | [+\|-\|#\|~] 이름: 타임 [다중성 정보] [=초기값]              |
| 연산 | [+\|-\|#\|~] 이름 (인자1: 타입1, …, 인자n: 타입n): 반환 타입 |

* **예시)**

  <img src="../capture/스크린샷 2019-07-19 오후 6.58.14.png" width=300>

  ```java
  public class Course {
    private String id;
    private String name;
    private int numOfStudents = 0;
    
    public void addStudent(){};
    public void deleteStudent(){};
  }
  ```



## 1.3.2. 관계

객체지향 시스템은 여러 개의 클래스가 서로 긴밀한 관계를 맺어 기능을 수행한다.

* **관계**

| 관계                                 | 설명                                                         |
| ------------------------------------ | ------------------------------------------------------------ |
| 연관 관계 (association)              | 한 클래스가 다른 클래스에서 제공하는 기능을 사용하는 관계. 실선이나 화살표로 표시한다. |
| 일반화 관계 (generalization)         | 상속 관계. 속이 빈 화살표를 사용해 표시한다.                 |
| 집합 관계 (composition, aggregation) | 클래스들 사이의 전체 또는 부분 같은 관계.                    |
| 의존 관계 (dependency)               | 한 클래스가 다른 클래스에서 제공하는 기능을 사용할 때. 한 메서드를 실행하는 동안의 매우 짧은 시간만 유지된다. 점선 화살표를 사용해 표시한다. |
| 실체화 관계 (realization)            | 인터페이스를 구현한 클래스들 사이의 관계를 나타낸다. 상속과 유사하게 빈 삼각형을 사용하며 머리에 있는 실선 대신 점선을 사용해 표시한다. |



### 연관 관계

* **Professor 클래스와 Student 클래스의 연관 관계**

  <img src="../capture/스크린샷 2019-07-19 오후 7.08.36.png" width=500>

  > 연관 관계를 가지면 각 클래스의 객체는 해당 연관 관계에서 어떤 역할을 수행하게 된다. 이러한 역할은 클래스 바로 옆 선 가까이에 적을 수 있다.

  

  <img src="../capture/스크린샷 2019-07-19 오후 7.11.26.png" width=500>

  > '상담한다' 연관 관계는 **양방향(bidirectional) 연관 관계다.** 두 클래스의 객체들이 서로의 존재를 인식한다는 의미이다.

* **예제) 사람과 전화기의 관계**

  <img src="../capture/스크린샷 2019-07-22 오후 8.32.03.png" width=500>

  ```java
  public class Person {
    private Phone[] phones;
    
    public Person() {
      phones = new Phone[2];
    }
  }
  ```

* **예제) 사람과 전화기의 관계2**

  <img src="../capture/스크린샷 2019-07-22 오후 8.46.39.png" width=500>

  ```java
  public class Person {
    private Phone homePhone;
    private Phone officePhone;
    
    public void setHomePhone(Phone phone) {
      this.homePhone = phone;
    }
    
    public void setOfficePhone(Phone phone) {
      ths.officePhone = phone;
    }
  }
  ```

  

* **다중성(multiplicity)** 

  : 연관된 객체 수를 연관된 클래스와 연결한 선 부근에 명시하는 것. 선에 아무런 숫자가 없으면 일대일 관계임을 나타낸다.

| 다중성 표기 | 의미                   |
| ----------- | ---------------------- |
| 1           | 엄밀하게 1             |
| *           | 0 또는 그 이상         |
| 0..*        | 0 또는 그 이상         |
| 1..*        | 1 이상                 |
| 0..1        | 0 또는 1               |
| 2..5        | 2 또는 3 또는 4 또는 5 |
| 1, 2, 6     | 1 또는 2 또는 6        |
| 1, 3..5     | 1 또는 3 또는 4 또는 5 |

* **예제) 한 교수에 여러 학생이 연관되는 다중성의 예**

<img src="../capture/스크린샷 2019-07-22 오후 6.16.07.png" witdh=300>



* **단방향 연관 관계**

: 한 클래스로만 방향성이 있는 연관 관계

​	<img src="../capture/스크린샷 2019-07-22 오후 9.14.55" width=700>

**Student 클래스**

```java
import java.util.Vector;

public class Student {

  private String name;
  private Vector<Course> courses;

  public Student(String name) {
    this.name = name;
    courses = new Vector<>();
  }

  public void registerCourse(Course course) {
    courses.add(course);
  }

  public void dropCourse(Course course) {
    if (courses.contains(course)) {
      courses.remove(course);
    }
  }
  
  public Vector<Course> getCourses() {
    return courses;
  }

}
```

**Course 클래스**

```java
package object_oriented_modeling;

public class Course {

  private String name;

  public Course(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
```



* **다대다 연관 관계**

: 다대다 연관 관계(양방향 연관 관계)는 서로의 존재를 안다는 의미이다. 그에 반해 단방향 연관 관계는 한 쪽은 알지만 다른 쪽은 상대방의 존재를 모른다는 의미이다.

<img src="../capture/스크린샷 2019-07-22 오후 6.33.37.png" width=500>

**Student 클래스**

```java
import java.util.Vector;

public class Student {

  private String name;
  private Vector<Course> courses;

  public Student(String name) {
    this.name = name;
    courses = new Vector<>();
  }

  public void registerCourse(Course course) {
    courses.add(course);
    course.addStudent(this);
  }

  public void dropCourse(Course course) {
    if (courses.contains(course)) {
      courses.remove(course);
      course.removeStudent(this);
    }
  }

  public Vector<Course> getCourses() {
    return courses;
  }

}
```

**Course 클래스**

```java
import java.util.Vector;

public class Course {

  private String name;
  private Vector<Student> students;

  public Course(String name) {
    this.name = name;
    students = new Vector<>();
  }

  public void addStudent(Student transcipt) {
    students.add(transcipt);
  }

  public void removeStudent(Student transcipt) {
    students.remove(transcipt);
  }

  public Vector<Student> getStudents() {
    return students;
  }

  public String getName() {
    return name;
  }

}
```



* **연관 클래스** 

  : 연관 관계에 **추가할 속성이나 행위가 있을 때 사용한다.** 예를 들어, 학생 클래스와 과목 클래스가 있을 때 성적은 두 클래스가 존재해야만 의미 있는 정보가 되기 때문에, 성적 정보는 클래스의 속성이 아닌 '수강하다' 라는 연관 관계의 속성으로 다뤄야 한다. 이런 경우 연관 클래스를 사용하면 된다.

  <img src="../capture/스크린샷 2019-07-22 오후 8.18.00.png" width=500>

  * **연관 클래스를 일반 클래스로 변환한 예**

  <img src="../capture/스크린샷 2019-07-22 오후 8.20.27" width=500>

  **Student 클래스**

  ```java
  import java.util.Vector;
  
public class Student {
  
  private String name;
    private Vector<Transcript> transcripts;

    public Student(String name) {
    this.name = name;
      transcripts = new Vector<>();
  }
  
  public void addTranscript(Transcript transcript) {
      transcripts.add(transcript);
    }
  
    public Vector<Transcript> getTranscripts() {
      return transcripts;
    }
  
    public String getName() {
      return name;
    }
  
  }
  ```
  
  **Course 클래스**
  
  ```java
  import java.util.Vector;
  
  public class Course {
  
    private String name;
    private Vector<Transcript> transcripts;
  
    public Course(String name) {
      this.name = name;
      transcripts = new Vector<>();
    }
  
    public void addTranscript(Transcript transcript) {
      transcripts.add(transcript);
    }
  
    public Vector<Transcript> getTranscripts() {
      return transcripts;
    }
  
    public String getName() {
      return name;
    }
  
  }
  ```
  
  **Transcript 클래스**
  
  ```java
  public class Transcript {
  
    private Student student;
    private Course course;
    private String date;
    private String grade;
  
    public Transcript(Student student, Course course) {
      this.student = student;
      this.student.addTranscript(this);
      this.course = course;
      this.course.addTranscript(this);
    }
  
    public Student getStudent() {
      return student;
    }
  
    public Course getCourse() {
      return course;
    }
  
    public void setDate(String date) {
      this.date = date;
    }
  
    public String getDate() {
      return date;
    }
  
  
    public String getGrade() {
      return grade;
    }
  
    public void setGrade(String grade) {
      this.grade = grade;
    }
    
  }
  ```
  
  > Student 클래스와 Transcipt 클래스의 연관 관계도 일대다 다중성을 갖으며 Course 클래스와 Transcipt 클래스 또한 그렇다.
  
  * **이력** : 어떤 연관 관계의 내용
  
    * **예시) 학생의 도서관 대출 이력**
  
      <img src="../capture/스크린샷 2019-07-22 오후 8.25.33.png" width=700>



* **연관 관계는 때로는 재귀적(reflexive)일 수 있다.**

  * **재귀적 연관 관계** : 동일한 클래스에 속한 객체들 사이의 관계이다.

  * **예시) 직원과 관리자 역할**

    <img src="../capture/스크린샷 2019-07-22 오후 10.10.38.png" width=500>

    > 하지만 여기서 사원과 사원이 서로 관리를 하는 모순이 발생할 수 있다. 

    