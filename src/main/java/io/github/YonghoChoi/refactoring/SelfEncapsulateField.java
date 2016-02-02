package io.github.YonghoChoi.refactoring;

/**
 * 필드에 직접 접근하던 중 그 필드로의 결합에 문제가 생길 땐
 * 그 필드용 읽기/쓰기 메서드를 작성해서 두 메서드를 통해서만 필드에 접근하게 만들자.
 */
public class SelfEncapsulateField {
    public static void main(String[] args) {
        IntRange intRange = new IntRange(1, 10);
        System.out.println(intRange.self_encapsulate_includes(8));

        CappedRange cappedRange = new CappedRange(1, 10, 4);
        System.out.println(cappedRange.self_encapsulate_includes(8));
    }
}

class IntRange {
    private int _low, _high;

    // 변수 직접 접근 방식
    boolean includes (int arg) {
        return arg >= _low && arg <= _high;
    }

    // 변수 간접 접근 방식
    // (자체 캡슐화를 통해 생성된 읽기 메서드 사용)
    boolean self_encapsulate_includes (int arg) {
        return arg >= getLow() && arg <= getHigh();
    }

    void grow(int factor) {
        _high = _high * factor;
    }

    public int getLow() {
        return _low;
    }

    public void setLow(int _low) {
        this._low = _low;
    }

    public int getHigh() {
        return _high;
    }

    public void setHigh(int _high) {
        this._high = _high;
    }

    // set 메서드를 호출하는 시점에는 이미 해당 필드에 대해 값이 초기화가 되었다고 전제할 때가 많다.
    // 그러므로 set 메서드에 이런 전제를 포함한 기능이 추가되어 있을 수 있으므로 생성자 안에서
    // set 메서드를 호출하는 경우 주의해야 한다. (이럴 땐 별도의 초기화 메서드를 사용하거나 생성자 안에서 직접 접근 방식을 사용한다.)
    IntRange(int low, int high) {
        this._low = low;
        this._high = high;
        // or
        initialize(low, high);
    }

    private void initialize(int low, int high) {
        this._low = low;
        this._high = high;
    }
}

// 위와같이 자체 캡슐화를 해두면 하위 클래스가 생길 때 편리해진다.
class CappedRange extends IntRange {
    private int _cap;

    public CappedRange(int low, int high, int _cap) {
        super(low, high);
        this._cap = _cap;
    }

    int getCap() {
        return _cap;
    }

    // IntRange의 기능을 재정의하면 기능을 하나도 수정하지 않고 cap을 계산에 넣을 수 있다.
    @Override
    public int getHigh() {
        return Math.min(super.getHigh(), getCap());
    }
}