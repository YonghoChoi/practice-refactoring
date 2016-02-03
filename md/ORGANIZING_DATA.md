# Organizing Data

* 마틴 파울러의 리펙토링 "8장 데이터 체계화" 부분 

## self Encapsulate Field

==필드에 직접 접근하고 있는데 필드에 대한 결합이 이상해지면==, 그 필드에 대한 get/set 메소드를 만들고 항상 이 메소드를 사용하여 필드에 접근하라.
```java
  private int _low, _high;
  boolean includes (int arg) {
      return arg >= _low && arg <= _high;
  }
                             
  private int _low, _high;
  boolean includes (int arg) {
      return arg >= getLow() && arg <= getHigh();
  }
  int getLow() {return _low;}
  int getHigh() {return _high;}
```

## 동 기

* 간접 접근 방식( indirect variable access )의 장점은 서브클래스에서 정보를 얻는 메소드를 오버라이드하여, 데이터를 관리하는데 있어 lay initialization(사용할 필요가 있을 때 값을 초기화 한다.) 많은 융통성을 제공할 수 있다.
* 직접 접근 방식( direct variable access )의 장점은 코드가 읽기 쉬워진다는 것이다. -> get 메소드라고 쉽게 말을 한다.
* 슈퍼클래스에 있는 필드에 접근하지만 이 변수 접근을 서브클래스에서 계산되는 값으로 오버라이드하고 싶을 때 좋다.

## 절 차

* 필드에 대한 get/set 메소드를 만든다.
* 해당 필드가 참조되는 곳을 모두 찾아 get/set 메소드를 사용하도록 바꾼다.
	* 필드에 접근하는 코드는 get 메소드를 호출하도록 바꾸고, 필드에 값을 할당하는 경우에는 set메소드를 사용하도록 바꾼다.
	* 필드의 이름을 임시로 바꿔서 컴파일 하면 필드를 참조하는 곳을 쉽게 찾을 수 있다.
* 필드를 private한다.
* 모든 참조를 수정했는지 다시 확인한다.
* 컴파일과 테스를 한다.

## 예 제
```java
  class IntRange {

    private int _low, _high;

    boolean includes (int arg) {
        return arg >= _low && arg <= _high;
    }

    void grow(int factor) {
        _high = _high * factor;
    }
    IntRange (int low, int high) {
        _low = low;
        _high = high;
    }
```

* 만약 get/set 메소드가 없다면, 자체 캡슐화를 하기 위해 get/set 메소드를 정의한 다음, 이 메소드를 사용하도록 한다.

```java
  class IntRange {

    private int _low, _high;

    boolean includes (int arg) {
        return arg >= _low && arg <= _high;
    }

    void grow(int factor) {
        _high = _high * factor;
    }
    IntRange (int low, int high) {
        _low = low;
        _high = high;
    }

	// To self-encapsulate I define getting and setting methods (if they don't already exist) and use those:

                                        
  class IntRange {

    boolean includes (int arg) {
        return arg >= getLow() && arg <= getHigh();
    }

    void grow(int factor) {
        setHigh (getHigh() * factor);
    }

    private int _low, _high;

    int getLow() {
        return _low;
    }

    int getHigh() {
        return _high;
    }

    void setLow(int arg) {
        _low = arg;
    }

    void setHigh(int arg) {
        _high = arg;
    }
```

* 자체 캡슐화를 사용할 때, 생성자에서 set메소드가 사용되는 것에 주의해야 한다.
* set 메소드에는 초기화 할 때와는 다른 동작을 포함 해야 한다.
```java
    IntRange (int low, int high) {
        initialize (low, high);
    }

    private void initialize (int low, int high) {
        _low = low;
        _high = high;
    }
```

* 이렇게 해두면 다음과 같이 서브클래스를 만들 때 그 진가가 드러난다.
```java
  class CappedRange extends IntRange {

    CappedRange (int low, int high, int cap) {
        super (low, high);
        _cap = cap;
    }

    private int _cap;

    int getCap() {
        return _cap;
    }

    int getHigh() {
        return Math.min(super.getHigh(), getCap());
    }
   }
```