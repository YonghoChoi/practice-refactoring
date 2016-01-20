package io.github.YonghoChoi.refectoring;

public class InlineTemp {
    public static void main(String[] args) {
        System.out.println(moreThan1000Price());
        System.out.println(moreThan1000PriceInline());
    }

    //===========================================================================================
    // Original Code
    //===========================================================================================
    private static boolean moreThan1000Price() {
        double basePrice = getBasePrice();
        return basePrice > 1000;
    }

    private static double getBasePrice() {
        return 1000.0;
    }

    //===========================================================================================
    // Step 1 : 간단한 수식의 결과값을 가지는 임시 변수가 있고 그 임시 변수가 다른 리팩토링을 하는데 방해가
    //          된다면 이 임시변수를 참조하는 부분을 모두 원래의 수식으로 바꿔라.
    //===========================================================================================
    private static boolean moreThan1000PriceInline() {
        return getBasePrice() > 1000;
    }
}
