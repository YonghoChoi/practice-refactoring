package io.github.YonghoChoi.refactoring;

public class InlineTemp {
    private static double basePrice;

    public static void setBasePrice(double basePrice) {
        InlineTemp.basePrice = basePrice;
    }

    public static void main(String[] args) {
        System.out.println(moreThan1000Price());
        System.out.println(moreThan1000PriceInline());
    }

    //===========================================================================================
    // Original Code
    //===========================================================================================
    public static boolean moreThan1000Price() {
        double basePrice = getBasePrice();
        return basePrice > 1000;
    }

    public static double getBasePrice() {
        return basePrice;
    }

    //===========================================================================================
    // Step 1 : 간단한 수식의 결과값을 가지는 임시 변수가 있고 그 임시 변수가 다른 리팩토링을 하는데 방해가
    //          된다면 이 임시변수를 참조하는 부분을 모두 원래의 수식으로 바꿔라.
    //===========================================================================================
    public static boolean moreThan1000PriceInline() {
        return getBasePrice() > 1000;
    }
}
