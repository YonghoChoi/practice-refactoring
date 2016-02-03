package io.github.YonghoChoi.refactoring;

import java.util.HashMap;
import java.util.Map;

/**
 * 참조 객체가 작고 수정할 수 없고 관리하기 힘들 땐
 * 그 참조 객체를 값 객체로 만들자.
 */
public class ChangeReferenceToValue {

    public static void main(String[] args) {
        // 여기서 Currency는 참조 객체. (유일한 객체)
        // Currency에는 code 값 외에도 여러 인스턴스가 있기 때문에 code값이 같다고 해서 같은 객체가 되진 않는다.
        //System.out.println(new Currency("USD").equals(new Currency("USD")));

        System.out.println(new Currency("USD", 1000).equals(new Currency("USD", 1000)));
        // 참조 객체를 값 객체로 만들기 위해서는 변경 불가 상태로 만들어야 하고 equals 메서드와 hashCode 메서드를 재정의 해야 한다.
        // hashcode를 재정의 하지 않으면 HashSet, HashMap 같이 해시를 이용하는 컬렉션이 오동작할 가능성이 있다.
        Currency c1 = new Currency("USD", 1000);
        Currency c2 = new Currency("USD", 1000);
        Map<Currency, Integer> map = new HashMap<>();
        map.put(c1, 1);
        map.put(c2, 1);
        System.out.println(map.size());
    }
}

class Currency {
    private String code;
    private Integer price;

    public Currency(String code, Integer price) {
        this.code = code;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    // equals 메서드에 사용되는 모든 필드의 해시 코드를 가져다 XOR(^) 비트 연산을 수행해야 한다.
    @Override
    public int hashCode() {
        return this.code.hashCode() ^ this.price.hashCode();
    }

    @Override
    public boolean equals(Object arg) {
        if(!(arg instanceof Currency)) return false;
        Currency other = (Currency)arg;
        return (code.equals(other.code)) && (price.equals(other.price));
    }
}