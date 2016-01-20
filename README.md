# Refactoring

## 참고 도서

* 마틴 파울러의 Refactoring
  
  ![Refactoring](http://image.hanbit.co.kr/cover/_m_1971m.gif)

## Extract Method

**그룹으로 함께 묶을 수 있는 코드 조각이 있으면** 코드의 목적이 잘 드러나도록 메소드의 이름을 지어 별도의 메소드로 뽑아낸다.

```java
void printOwing(double amount) {
	printBanner();
    
    //print details
    System.out.println("name:" + _name);
    System.out.println("amount: " + amount);
}
```
```java
void printOwing(double amout) {
	printBanner();
    printDetails(amount);
}

void printDetails(double amount) {
	System.out.println("name: " + _name);
    System.out.println("amount: " + amount);
}
```

### 동기

* 지나치게 긴 메소드
* 목적을 이해하기 위해서 주석이 필요한 코드
* 짧고 이해하기 쉬운 메소드
	* 메소드가 잘게 쪼개져 있을 떄 다른 메소드에서 사용될 확률이 높아진다.
	* 상위 레벨(high-level)의 메소드를 볼 때 일련의 주석을 읽는 것 같은 느낌이 든다.
	* 오버라이드 하는 것도 쉽다.
* 이름을 지을 때 주의하라.
* 한 메소드의 길이는 중요하지 않다.
	* 메소드의 이름과 메소드 몸체의 의미적 차이
	* 뽑아내는 것이 코드를 더욱 명확하게 하면, 새로 만든 메소드의 이름이 원래 코드의 길이보다 길어져도 뽑아낸다.

### 절차

* 메소드를 새로 만들고, 의도를 잘 나타낼 수 있도록 이름을 정한다.
	* 어떻게 하는지를 나타내는 방식으로 이름을 정하지 말고, 무엇을 하는지를 나타내게 이름을 정한다.
	* **더 이해하기 쉬운 이름을 지을 수 없다면 뽑아내지 않는 것이 낫다.**
* 원래 메소드에서 뽑아내고자 하는 부분의 코드를 복사하여 새로운 메소드로 옮긴다.
* 원래 메소드에서 사용되고 있는 지역 변수가 뽑아낸 코드에 있는지 확인한다.
	* 이런 지역 변수는 새로운 메소드의 지역변수나 파라미터가 된다.
* 뽑아낸 코드 내에서만 사용되는 임시 변수가 있는지 본다.
	* 있다면 새로 만든 메소드의 임시 변수로 선언한다.
* 뽑아낸 코드 내에서 지역 변수의 값이 수정되는지 본다.
	* 하나의 지역변수만 수정된다면, 뽑아낸 코드를 질의로 보고, 수정된 결과를 관련된 변수에 대입할 수 있는지 본다.
	* 이렇게 하는 것이 이상하거나, 값이 수정되는 지역변수가 두 개 이상 있다면 쉽게 메소드로 추출할 수 없는 경우이다.
		* Split Temporary Variable을 사용한 뒤에 재시도.
		* 임시 변수는 Replace Temp with Query로 제거
* 뽑아낸 코드에서 읽기만 하는 변수는 새 메소드의 파라미터로 넘긴다.
* 지역 변수와 관련된 사항을 다룬 후에는 컴파일 한다.
* 원래 메소드에서 뽑아낸 코드 부분은 새로 만든 메소드를 호출하도록 바꾼다.
	* 새로 만든 메소드로 옮긴 임시 변수가 있는 경우, 그 임시 변수가 원래 메소드의 밖에서 선언 되었는지를 확인한다.
	* 새로 만든 메소드에서는 선언을 해줄 필요가 없다.
* 컴파일과 테스트를 한다.

#### 지역 변수가 없는 경우

```java
void printOwing() {
	Enumeration e = _orders.elements();
    double outstanding = 0.0;
    
    // print banner
    System.out.println("************************");
    System.out.println("**** Customer  Owes ****");
    System.out.println("************************");
    
    // calculate outstanding
    while(e.hasMoreElements()) {
    	Order each = (Order) e.nextElement();
        outstanding += each.getAmount();
    }
    
    // print details
    System.out.println("name: " + _name);
    System.out.println("amount: " + outstanding);
}
```
**배너를 인쇄하는 부분을 뽑아내는 것은 쉽다.** ==> Cut and Pasted 후 원래 코드는 새 메소드를 호출한다.

```java
public static void printOwing_Step1() {
    Enumeration e = _orders.elements();
    double outstanding = 0.0;

    printBanner();

    // calculate outstanding
    while (e.hasMoreElements()) {
        Order each = (Order) e.nextElement();
        outstanding += each.getAmount();
    }

    //print details
    System.out.println ("name:" + _name);
    System.out.println("amount: " + outstanding);
}

private static void printBanner() {
    // print banner
    System.out.println ("**************************");
    System.out.println ("***** Customer Owes ******");
    System.out.println ("**************************");
}
```

**지역 변수가 포함되어 있는 경우**
* 가장 쉬운 것은 변수가 읽히기만 하고 값이 변하지 않는 경우 ==> 그냥 파라미터만 넘긴다.

```java
public static void printOwing_Step2() {
    Enumeration e = _orders.elements();
    double outstanding = 0.0;

    printBanner();

    // calculate outstanding
    while (e.hasMoreElements()) {
        Order each = (Order) e.nextElement();
        outstanding += each.getAmount();
    }

    printDetails(outstanding);
}

private static void printDetails(double outstanding) {
    System.out.println ("name:" + _name);
    System.out.println ("amount: " + outstanding);
}
```
* 실제로 지역 변수에 다른 값을 대입하는 경우에만 부가적인 작업이 필요하다.

**지역 변수에 다른 값을 여러번 대입하는 경우**
* 지역 변수에 다른 값을 대입하는 코드가 있는 경우에는 문제가 복잡
* 우선 임시변수에 대해서만 생각
	* 파라미터에 다른 값을 대입하는 코드가 있다면 Remove Assignments to Parameters를 적용한다.
* 임시 코드에 값을 대입하는 경우
	* 임시 변수가 뽑아낸 코드 안에서만 사용 될 때
		* 뽑아낸 코드로 임시 변수를 옮긴다.
    * 임시 변수가 뽑아낸 코드 외부에도 사용되는 경우
    	* 뽑아낸 코드 이후의 부분에서 사용되지 않는다면, **앞의 경우처럼 바꿀 수 있다.**
    	* 이 후에서도 사용된다면, 뽑아낸 코드에서 **임시변수의 바뀐 값을 리턴하도록 수정**

**계산 부분의 코드만 뽑는다.**
**변수 e는 뽑아낸 코드안에서만 사용되기 때문에 새 메소드로 옮길 수 있다. 변수 outstanding은 양쪽에서 모두 사용되므로 뽑아낸 메소드에서 그 값을 리턴하게 한다. 테스트 후에 변수 이름을 변경한다.**

```java
public static void printOwing_Step3() {
    printBanner();
    double outstanding = getOutstanding();
    printDetails(outstanding);
}

private static double getOutstanding() {
    Enumeration e = _orders.elements();
    double result = 0.0;
    while (e.hasMoreElements()) {
        Order each = (Order) e.nextElement();
        result += each.getAmount();
    }
    return result;
}
```

**만약 변수와 관련된 다른 코드가 있다면 이전의 값을 파라미터로 넘겨야 한다.**

```java
public static void printOwing_Step4(double previousAmount) {
    printBanner();
    double outstanding = previousAmount * 1.2;
    outstanding = getOutstanding(outstanding);
    printDetails(outstanding);
}

private static double getOutstanding(double initialValue) {
    Enumeration e = _orders.elements();
    double result = initialValue;
    while (e.hasMoreElements()) {
        Order each = (Order) e.nextElement();
        result += each.getAmount();
    }
    return result;
}
```

**컴파일과 테스트를 한 후에 outstanding 변수를 초기화하는 방법을 바꾼다.**

```java
public static void printOwing_Step5(double previousAmount) {
        printBanner();
        double outstanding = getOutstanding(previousAmount * 1.2);
        printDetails(outstanding);
    }
```

* 만약 두개 이상의 값이 리턴되어야 한다면
	* 가장 좋은 선택은 뽑아낼 코드를 다르게 선택하라.
		* 각각의 다른 값에 대한 메소드를 따로 만든다.
    * 사용중인 언어가 출력 파라미터를 지원한다면 이것을 이용한다.
* 임시 변수가 너무 많아 코드를 뽑기 힘든 경우
	* Replace Temp with Query (임시 변수를 줄인다.)
	* Replace Method with Method Object


## Inline Method

**메소드 몸체가 메소드의 이름만큼이나 명확할 때**는 호출하는 곳에 메소드의 몸체를 넣고, 메소드를 삭제하라

```java
int getRating() {
	return (moreThanFiveLateDeliveries()) ? 2 : 1;
}

boolean moreThanFiveLateDeliveries() {
	return _numberOfLateDeliveries > 5;
}
```

```java
int getRating() {
	return (_numberOfLateDeliveries > 5) ? 2: 1;
}
```

### 동기

* 이럴 때는 메소드를 제거한다.
	* 때로는 메소드의 몸체가 메소드의 이름만큼이나 명확할 때가 있다.
	* 메소드의 몸체를 메소드의 이름만큼이나 명확하게 리팩토링할 수도 있다.
	* 필요없는 인디렉션은 짜증나게 한다.
* 메소드가 잘못 나뉘어졌을 때
	* 메소드를 다시 합쳐 하나의 큰 메소드로 만든 다음 다시 메소드 추출
		* Replace Method with Method Object를 사용하기 전에 이 방법을 쓰면 좋다(Kent Beck)
    * 메소드 객체가 포함하고 있어야 할 동작을 가진 메소드에 의해 호출되는 여러 메소드를 인라인화 한다.
    * 메소드와 그 메소드가 호출하는 다른 여러 메소드를 옮기는 것보다는 메소드 하나만 옮기는 것이 더 쉽다.
* 너무 많은 인디렉션이 사용되어 모든 메소드가 단순히 다른 메소드에 위임을 하고 있어 그 인디렉션 속에서 길을 잃을 염려가 있을 때

### 절차

* 메소드가 다형성을 가지고 있지 않은지 확인한다.
	* 서브 클래스에서 오버라이드 하고 있는 메소드에는 적용하지 않는다.
	* 수퍼 클래스에 없는 메소드를 서브 클래스에서 오버라이드 할 수는 없다.
* 메소드를 호출하고 있는 부분을 모두 찾는다.
* 각각의 메소드 호출을 메소드 몸체로 바꾼다.
* 컴파일과 테스트를 한다.
* 메소드 정의를 제거한다.

* inline Method가 간단한가?
	* 일반적으로 그리 간단하지 않다.
	* 재귀가 사용되는 경우, 리턴 포인트가 여러곳인 경우, 접근자가 없는 경우 다른 객체로 인라인화 하기 힘들다.
		* 이러면 하지 마라!

## Inline Temp

**간단한 수식의 결과값을 가지는 임시 변수가 있고, 그 임시 변수가 다른 리팩토링을 하는데 방해가 된다면 이 임시변수를 참조하는 부분을 모두 원래의 수식으로 바꿔라.**
```java
double basePrice = anOrder.basePrice();
return basePrice > 1000;
```

```java
return (anOrder.basePrice() > 1000)
```

### 동기

* 대부분의 경우 inline Temp는 Replace Temp with Query의 한 부분으로 사용된다.
* 진짜 동기는 그 쪽에 있다.
* Inline Temp가 자신의 목적으로 사용되는 유일한 경우
	* 메소드 호출의 결과값이 임시 변수에 대입되는 경우 인라인화 하라.
	* 임시 변수가 Extract Method와 같은 리팩토링에 방해가 된다면 인라인화 하라.

### 절차

* 임시 변수를 final로 선언한 다음 컴파일 하라.
	* 이것은 임시 변수에 값이 단 한번만 대입이 되고 있는지를 확인하기 위한 것이다.
* 임시 변수를 참조하고 있는 곳을 모두 찾아 대입문의 우변에 있는 수식으로 바꾼다.
* 각각의 변경에 대해 컴파일과 테스트를 한다.
* 임시 변수의 선언문과 대입문을 제거한다.
* 컴파일과 테스트를 한다.
