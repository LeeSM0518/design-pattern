# 쇼핑몰 프로젝트 1차 설계

## 도메인

* **회원**
  * 회원가입
  * 로그인
  * 로그아웃
  * 내 정보 조회
* **장바구니** : 물품을 장바구니에 담을시 장바구니를 출력해주기 위해 **옵서버 패턴으로 구현**
  * 물품 담기
* **상품**
  * 추가
  * 삭제
  * 수정
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

![image](https://user-images.githubusercontent.com/43431081/74100102-64ed9900-4b6e-11ea-98be-dc7fe342edde.png)

<br>

### ER 다이어그램

![image](https://user-images.githubusercontent.com/43431081/74100104-68812000-4b6e-11ea-9d8c-93c7da9e2ec6.png)

<br>

# 클래스 설계

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