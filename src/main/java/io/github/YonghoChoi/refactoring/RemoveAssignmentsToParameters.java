package io.github.YonghoChoi.refactoring;

import java.util.Date;

/**
 * 매개변수로 값을 대입하는 코드가 있을 땐
 * 매개변수 대신 임시변수를 사용하게 수정하자.
 */
public class RemoveAssignmentsToParameters {

    //====================================================================
    // Original Code
    // 컴파일 오류나 결과에는 이상이 없으나 파라미터로 전달 받은 변수에 직접적인 수정을
    // 하는 것은 이 메소드를 호출한 곳에서 의도하지 않은 것 일 수 있고 개발자 입장에서
    // call by reference와 call by value를 혼동할 수 있다.
    // 실질적으로 문제는 되지 않으나 혼란을 줄이기 위해 파라미터를 수정하는 것은 지양하는 것이 관례
    //====================================================================
    public int discount(int inputVal, int quantity, int yearToDate) {
        if(inputVal > 50) inputVal -= 2;
        if(quantity > 100) inputVal -= 1;
        if(yearToDate > 10000) inputVal -= 4;
        return inputVal;
    }

    //====================================================================
    // Step 1 : 매개변수를 임시 변수로 변경
    //====================================================================
    public int discount_step1(int inputVal, int quantity, int yearToDate) {
        int result = inputVal;
        if(inputVal > 50) result -= 2;
        if(quantity > 100) result -= 1;
        if(yearToDate > 10000) result -= 4;
        return result;
    }

    //====================================================================
    // Step 2 : 매개변수에 값 대입 방지를 강제하도록 final 선언
    //====================================================================
    public int discount_step2(final int inputVal, final int quantity, final int yearToDate) {
        int result = inputVal;
        if(inputVal > 50) result -= 2;
        if(quantity > 100) result -= 1;
        if(yearToDate > 10000) result -= 4;
        return result;
    }

    //====================================================================
    // Call by Value, Call by Reference
    //====================================================================
    public void param() {
        Param param = new Param();
        param.callByValue();
        param.callByReference();
    }
}

class Param
{
    public void callByValue() {
        int x = 5;
        triple(x);
        System.out.println("triple 메서드 실행 후 x 값 : " + x);
    }

    private void triple(int arg) {
        arg = arg * 3;
        System.out.println("triple 메서드 안의 arg 값 : " + arg);
    }

    public void callByReference() {
        Date d1 = new Date("1 Apr 98");
        nextDateUpdate(d1);
        System.out.println("nextDay 메서드 실행 후 d1 값 : " + d1);

        Date d2 = new Date("1 Apr 98");
        nextDateReplace(d2);
        System.out.println("nextDay 메서드 실행 후 d2 값 : " + d2);
    }

    private void nextDateUpdate(Date arg) {
        arg.setDate(arg.getDate() + 1);
        System.out.println("nextDay 메서드 안의 arg 값 : " + arg);
    }

    private void nextDateReplace(Date arg) {
        arg = new Date(arg.getYear(), arg.getMonth(), arg.getDate() + 1);
        System.out.println("nextDay 메서드 안의 arg 값 : " + arg);
    }
}
