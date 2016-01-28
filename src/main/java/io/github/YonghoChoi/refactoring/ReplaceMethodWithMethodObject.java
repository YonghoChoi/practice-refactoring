package io.github.YonghoChoi.refactoring;

/**
 * 지역변수 때문에 메서드 추출을 적용할 수 없는 긴 메서드가 있을 땐
 * 그 메서드 자체를 객체로 전환해서 모든 지역변수를 객체의 필드로 만들자.
 * 그런 다음 그 메서드를 객체 안의 여러 메서드로 쪼개면 된다
 */
public class ReplaceMethodWithMethodObject {
    //====================================================================
    // Original Code
    //====================================================================
    public int gamma(int inputVal, int quantity, int yearToDate) {
        int importantValue1 = (inputVal * quantity) + delta();
        int importantValue2 = (inputVal * yearToDate) + 100;
        if((yearToDate - importantValue1) > 100) {
            importantValue2 -= 20;
        }
        int importantValue3 = importantValue2 * 7;
        return importantValue3 - 2 * importantValue1;
    }

    private int delta() {
        return 0;
    }

    //====================================================================
    // Step 1 : 새로운 클래스 생성과 임시변수와 필요한 변수들 필드로 선언
    //====================================================================
    class Gamma {
        private ReplaceMethodWithMethodObject rmmo;
        private int inputVal;
        private int quantity;
        private int yearToDate;
        private int importantValue1;
        private int importantValue2;
        private int importantValue3;

        //===========================================================================
        // Step 2 : 생성자 메서드로 필요한 값 전달 받음
        //===========================================================================
        Gamma(ReplaceMethodWithMethodObject rmmo, int inputVal, int quantity, int yearToDate) {
            this.rmmo = rmmo;
            this.inputVal = inputVal;
            this.quantity = quantity;
            this.yearToDate = yearToDate;
        }

        //===========================================================================
        // Step 3 : 연산 부분 필드 변수를 사용해서 재정의
        //===========================================================================
        int compute() {
            importantValue1 = (inputVal * quantity) + rmmo.delta();
            importantValue2 = (inputVal * yearToDate) + 100;
            importantThing();
            importantValue3 = importantValue2 * 7;
            return importantValue3 - 2 * importantValue1;
        }

        // 쉽게 Extract Method 가능
        private void importantThing() {
            if((yearToDate - importantValue1) > 100) {
                importantValue2 -= 20;
            }
        }
    }

    //===========================================================================
    // Step 4 : 생성한 메서드 객체로 위임하도록 수정
    //===========================================================================
    public int gamma_delegate(int inputVal, int quantity, int yearToDate) {
        return new Gamma(this, inputVal, quantity, yearToDate).compute();
    }
}
