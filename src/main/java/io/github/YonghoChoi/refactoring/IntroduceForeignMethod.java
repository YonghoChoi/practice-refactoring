package io.github.YonghoChoi.refactoring;

import java.util.Date;

/**
 * 사용 중인 서버 클래스에 메서드를 추가해야 하는데 그 클래스를 수정할 수 없을 땐
 * 클라이언트 클래스 안에 서버 클래스의 인스턴스를 첫 번째 인자로 받는 메서드를 작성하자.
 */
public class IntroduceForeignMethod {
    public static void main(String[] args) {
        Date previousEnd = new Date();

        // Date 클래스에 다음 날짜를 구하는 메소드가 필요한 경우
        Date nextDay = new Date(previousEnd.getYear(), previousEnd.getMonth(), previousEnd.getDate() + 1);
        System.out.println(nextDay);

        System.out.println(nextDay(previousEnd));

    }

    // Date 클래스를 수정할수가 없으므로 필요한 메서드를 클라이언트 클래스(IntroduceForeignMethod) 안에 작성한다.
    // 이 때 서버 클래스(Date)의 인스턴스를 첫 번째 매개변수로 만들자.

    // nextDay는 Date 클래스에 있을 외래 메서드
    private static Date nextDay(Date date) {
        return new Date(date.getYear(), date.getMonth(), date.getDate() + 1);
    }
}
