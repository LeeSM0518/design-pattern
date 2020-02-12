# 쇼핑몰 프로젝트 2차 설계

## 도메인

* **회원**
  * 회원가입
  * 로그인
  * 로그아웃
  * 내 정보 조회
* **장바구니** : 물품을 장바구니에 담을시 장바구니를 출력해주기 위해 **옵서버 패턴으로 구현**
  * 상품 삭제
  * 전체 구매 하기
* **상품**
  * 장바구니에 추가
  * 조회
* **구매** : 결제 API 마다 방식이 달라지기 때문에 **커맨드 패턴으로 구현**
  * 상품 구매시 결제 API를 실행
  * 구매시 배송 상태를 **스테이트 패턴으로 구현**
  * 결제가 완료되면 ProductDao로 물품을 하나 삭제
* **DB**
  * MemberDao, ProductDao 를 **싱글톤 패턴과 스트래티지 패턴으로 구현**

<br>

## DB 설계

* **회원(MEMBER)**
  * 회원은 회원 번호(member_id), 이름(name), 아이디(username), 비밀번호(password), 전화번호(phone_number), 주소(address)를 갖는다.
  * 회원은 상품을 장바구니에 담을 수 있다.
  * 회원은 상품을 주문할 수 있다.
* **상품(PRODUCT)**
  * 상품은 상품 번호(product_id), 상품명(name), 재고(stock), 판매 상태(sale_status), 가격(price)을 갖는다.
* **장바구니(BASKET)**

<br>

### ER 모델

<img src="https://user-images.githubusercontent.com/43431081/74100102-64ed9900-4b6e-11ea-98be-dc7fe342edde.png" alt="image" style="zoom:50%;" />

<br>

### ER 다이어그램

![image](https://user-images.githubusercontent.com/43431081/74100104-68812000-4b6e-11ea-9d8c-93c7da9e2ec6.png)

<br>

### SQL

* **MEMBER**

  ```sql
  create table MEMBER (
    member_id serial primary key,
    name varchar(20),
    username varchar(20),
    password varchar(20),
    phone_number varchar(15),
    address varchar(50)
  );
  ```

  * **select**

    * 한 멤버에 대해서 조회

      ```sql
      select *
      from MEMBER
      where username=? and password=?
      ```

  * **insert**

    * 멤버 삽입

      ```sql
      insert into MEMBER values
      (DEFAULT, ?, ?, ?, ?, ?)
      ```

* **PRODUCT**

  ```sql
  create table PRODUCT (
    product_id serial primary key,
    name varchar(20),
    price integer,
    stock integer
  );
  ```

  * **select**

    * 모든 상품 조회

      ```sql
      select *
      from PRODUCT
      ```

  * **update**

    * 상품 구매시 상품 개수 감소

      ```sql
      update PRODUCT
      set stock = stock - 1
      where product_id = ?
      ```

* **BASKET**

  ```sql
  create table BASKET (
    member_id integer,
    product_id integer,
    foreign key (member_id) references MEMBER(member_id),
    foreign key (product_id) references PRODUCT(product_id)
  );
  ```

  * **insert**

    * 선택된 상품을 해당 회원의 장바구니에 담는다.

      ```sql
      insert into BASKET values
      (?, ?);
      ```

  * **select**

    * 회원의 장바구니로 부터 상품 리스트를 받는다.

      ```sql
      select p.product_id, p.name, p.price, p.stock
      from MEMBER m, PRODUCT p, BASKET b
      where m.member_id = b.member_id and
      p.product_id = b.product_id and
      m.member_id = ?
      ```

  * **delete**

    * 상품 취소시 회원의 장바구니로 부터 제거한다.

      ```sql
      delete from BASKET
      where member_id = ? and
      product_id = ?
      ```

    * 상품 구매시 모두 제거

      ```sql
      delete from BASKET
      where member_id = ?
      ```

* **ORDER_PRODUCT**

  ```sql
  create table ORDER_PRODUCT (
    order_id serial primary key,
    delivery_status varchar(20),
    member_id integer,
    product_id integer,
    foreign key (member_id) references MEMBER(member_id),
    foreign key (product_id) references PRODUCT(product_id)
  );
  ```

  * **insert**

    * 상품 구매시 주문 상품으로 넣는다.

      ```sql
      insert into ORDER_PRODUCT values
      (DEFAULT, ?, ?, ?)
      ```

  * **select**

    * 해당 회원 번호로 구매 리스트를 받는다.

      ```sql
      select o.order_id, o.delivery_status, p.product_id, p.name, p.price
      from ORDER_PRODUCT o, MEMBER m, PRODUCT p
      where m.member_id = o.member_id and
      p.product_id = o.product_id and
      m.member_id = ?
      ```

  * **update**

    * 해당 회원 번호의 해당 상품의 배송 상태를 바꾼다.

      ```sql
      update ORDER_PRODUCT
      set	delivery_status = ?
      where product_id = ? and member_id = ?
      ```

<br>

### Init data

* **Product**

  ```sql
  insert into PRODUCT values
  (DEFAULT, 'Mac Pro', 2000000, 5),
  (DEFAULT, 'Mac Mini', 3500000, 3),
  (DEFAULT, 'iMac', 1500000, 2),
  (DEFAULT, 'iPhone 11', 1000000, 8);
  ```

<br>

## UI 설계

* **첫 메뉴**

  ```
  =============================
  메뉴를 입력해주세요.
  1. 로그인
  2. 회원가입
  3. 종료
  =============================
  >>>> 
  ```

* **회원 가입**

  ```
  =============================
  이름 >> 이상민
  아이디 >> abc
  비밀번호 >> 123
  전화번호 >> 010-1234-1234
  주소 >> 대전
  =============================
  ```

* **로그인**

  ```
  =============================
  아이디 >> abc
  비밀번호 >> 123
  =============================
  ```

* **메인 메뉴**

  ```
  =============================
  메뉴를 입력해주세요.
  1. 내정보 보기
  2. 로그아웃
  3. 쇼핑
  4. 장바구니
  5. 주문 확인
  =============================
  >>>> 
  ```

* **내정보 보기**

  ```
  =============================
  이름 >> 이상민
  아이디 >> abc
  전화번호 >> 010-1234-1234
  주소 >> 대전
  =============================
  ```

* **쇼핑**

  ```
  =============================
  상품 리스트
  =============================
  상품 번호: 0
  상품 이름: Mac Pro
  상품 가격: 2000000
  상품 재고: 3
  -----------------------------
  ...
  =============================
  메뉴를 입력해주세요.
  1. 상품 담기
  2. 이전으로
  =============================
  >>>> 
  ```

* **상품 담기**

  ```
  =============================
  상품 번호를 입력해주세요.
  >>>> 0
  =============================
  장바구니 리스트
  =============================
  상품 번호: 0
  상품 이름: Mac Pro
  상품 가격: 2000000
  상품 재고: 3
  -----------------------------
  ...
  =============================
  ```

* **장바구니**

  ```
  =============================
  장바구니 리스트
  =============================
  상품 번호: 0
  상품 이름: Mac Pro
  상품 가격: 2000000
  상품 재고: 3
  -----------------------------
  ...
  =============================
  메뉴를 입력해주세요.
  1. 전체 구매하기
  2. 상품 취소하기
  3. 이전으로
  =============================
  ```

  * **전체 구매하기**

    ```
    =============================
    결제 API 연결 완료
    =============================
    결제하실 방법을 선택해주세요.
    1. Samsung Pay
    2. 휴대폰 결제
    3. 계좌 이체
    =============================
    >>>> 1
    =============================
    Samsung Pay 결제중...
    결제 완료.
    =============================
    ```

  * **상품 취소하기**

    ```
    =============================
    취소할 상품 번호를 입력해주세요.
    >>>> 
    ```

* **주문 확인**

  ```
  =============================
  구매 리스트
  =============================
  상품 번호: 0
  상품 이름: Mac Pro
  상품 가격: 2000000
  배송 상태: 배송중
  -----------------------------
  ...
  =============================
  ```

<br>

## 클래스 설계

* **DTO class**

  ![image](https://user-images.githubusercontent.com/43431081/74100114-7fc00d80-4b6e-11ea-8b58-e77bf59b910d.png)

* **DB Connector class**

  <img src="https://user-images.githubusercontent.com/43431081/74100115-8189d100-4b6e-11ea-9f3d-8ee30fdc02f5.png" alt="image" style="zoom:50%;" />

* **DataAccessObject & Service class**

  ![image](https://user-images.githubusercontent.com/43431081/74100116-83539480-4b6e-11ea-86d8-3e1dcd17b05b.png)

<br>

## 사용할 패턴

* **스트래티지 패턴** : 전략을 쉽게 바꿀 수 있도록 해주는 디자인 패턴

  <img src="https://github.com/LeeSM0518/design-pattern/raw/master/capture/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202019-08-14%20%EC%98%A4%ED%9B%84%2010.53.11.png">

* **싱글톤 패턴** : 인스턴스가 오직 하나만 생성되는 것을 보장하고 어디에서든 이 인스턴스에 접근할 수 있도록 하는 디자인 패턴

  <img src="https://github.com/LeeSM0518/design-pattern/raw/master/capture/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202019-09-02%20%EC%98%A4%EC%A0%84%2010.19.30.png">

* **스테이트 패턴** : 어떤 행위를 수행할 때 상태에 행위를 수행하도록 위임한다. 시스템의 각 상태를 나타내는 클래스로 하여금 실체화하게 한다.

  <img src="https://github.com/LeeSM0518/design-pattern/raw/master/capture/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202019-09-05%20%EC%98%A4%ED%9B%84%206.49.52.png">

* **커맨드 패턴** : 이벤트가 발생했을 때 실행될 기능이 다양하면서도 변경이 필요한 경우에 이벤트를 발생시키는 클래스를 변경하지 않고 재사용하고자 할 때 유용하다.

  <img src="https://github.com/LeeSM0518/design-pattern/raw/master/capture/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202019-09-18%20%EC%98%A4%ED%9B%84%203.14.12.png">

* **옵서버 패턴** : 데이터의 변경이 발생했을 경우 상대 클래스나 객체에 의존하지 않으면서 데이터 변경을 통보하고자 할 때 유용하다.

  <img src="https://github.com/LeeSM0518/design-pattern/raw/master/capture/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202019-09-23%20%EC%98%A4%ED%9B%84%206.03.22.png">