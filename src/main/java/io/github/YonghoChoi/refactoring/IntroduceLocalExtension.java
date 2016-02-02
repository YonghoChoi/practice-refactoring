package io.github.YonghoChoi.refactoring;

import java.util.Date;

/**
 * 사용 중인 서버 클래스에 여러 개의 메서드를 추가해야 하는데 클래스를 수정할 수 없을 땐
 * 새 클래스를 작성하고 그 안에 필요한 여러개의 메서드를 작성하자.
 * 이 상속확장 클래스를 원본 클래스의 하위클래스나 래퍼 클래스로 만들자.
 */
public class IntroduceLocalExtension {
    public static void main(String[] args) {
        // 외래 메서드 사용
        Date previousEnd = new Date();
        System.out.println(nextDay(previousEnd));

        // 하위 클래스 사용
        MfDateSub dateSub = new MfDateSub(previousEnd);
        System.out.println(dateSub.nextDay());

        // 래퍼 클래스 사용
        MfDateWrap dateWrap = new MfDateWrap(previousEnd);
        System.out.println(dateWrap.nextDay());

        test();
    }

    // nextDay는 Date 클래스에 있을 외래 메서드
    private static Date nextDay(Date date) {
        return new Date(date.getYear(), date.getMonth(), date.getDate() + 1);
    }

    /**
     *  메서드 실행이 한 방향으로만 가능하다.
     *  DateWrap 클래스에서 원본 클래스로도 함수 호출이 가능하도록 구현한 것은
     *  래퍼 클래스 사용 사실을 원본 클래스 사용 부분이 모르게 하기 위해서이다.
     *  하지만 완벽하게 감추진 못한다. 아래는 이 와같은 경우의 예시이다.
     * */
    private static void test() {
        MfDateWrap aWrapper = new MfDateWrap(new Date());
        MfDateWrap anotherWrapper = new MfDateWrap(new Date());
        Date aDate = new Date();

        aWrapper.after(aDate);              // 가능
        aWrapper.after(anotherWrapper);     // 가능
        //aDate.after(aWrapper);              // 불가능

        // equals 메서드가 대칭적이라고 전제하는데 만약 이 전제에 어긋나는 코딩을 하면
        // 각종 이상한 버그가 생길 것이다.
        System.out.println(aWrapper.equals(aDate)); // true
        System.out.println(aDate.equals(aWrapper)); // false

        // 대등성 검사라는 목적을 나타내고자 할 때는 그에 맞게 메서드 명을 변경하는 것이 좋다.
        System.out.println(aWrapper.equalsDate(anotherWrapper));
        System.out.println(aWrapper.equalsDate(aDate));
    }
}

// 하위 클래스 사용
class MfDateSub extends Date {
    public MfDateSub(String dateString) {
        super(dateString);
    }

    // 원본 Date 클래스를 인자로 받는 변환 생성자
    //   * 변환 생성자 : 서로 다른 타입 간의 대입에 있어서 암시적 변환을 가능하게 하는 문법적 장치
    //              (여기서는 Date 타입을 인자로 MfDateSub 인스턴스를 생성한다.)
    public MfDateSub(Date arg) {
        super(arg.getTime());
    }

    // IntroduceLocalExtension클래스의 외래 메서드인 nextDay를 메서드 이동
    public Date nextDay() {
        return new Date(getYear(), getMonth(), getDate() + 1);
    }
}


// 래퍼 클래스 사용
class MfDateWrap {
    private Date _original;

    // 원본 생성자 메서드는 간단한 위임을 통해 구현
    public MfDateWrap(String dateString) {
        this._original = new Date(dateString);
    }

    // 변환 생성자
    public MfDateWrap(Date arg) {
        this._original = arg;
    }

    // 원본 Date 클래스의 모든 메서드를 위임하는 작업을 수행해야 한다. (MfDateWrap 클래스로 Date 클래스의 역할을 해야하므로...)
    // 여기선 모든 메서드는 구현하지 않고 필요한 메서드만 구현
    public int getYear() {
        return this._original.getYear();
    }

    public int getMonth() {
        return this._original.getMonth();
    }

    public int getDay() {
        return this._original.getDay();
    }

    public boolean equals(Object arg) {
        if(this == arg) return true;
        if(arg instanceof MfDateWrap) {
            MfDateWrap other = (MfDateWrap)arg;
            return _original.equals(other._original);
        } else if(arg instanceof Date) {
            return _original.equals(arg);
        } else {
            return false;
        }
    }

    public boolean equalsDate(MfDateWrap arg) {
        return _original.equals(arg._original);
    }

    public boolean equalsDate(Date arg) {
        return _original.equals(arg);
    }

    public boolean after(MfDateWrap arg) {
        return this._original.after(arg._original);
    }

    public boolean after(Date arg) {
        return this._original.after(arg);
    }

    Date nextDay() {
        return new Date(getYear(), getMonth(), getDay() + 1);
    }
}