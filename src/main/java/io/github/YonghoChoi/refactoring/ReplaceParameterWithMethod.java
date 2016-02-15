package io.github.YonghoChoi.refactoring;

public class ReplaceParameterWithMethod {
    private static int itemPrice;
    private static int quntity;

    static double getPrice() {
        return discountPrice();
    }

    private static int getBasePrice() {
        return quntity * itemPrice;
    }
    private static int getDiscountLevel() {
        if(quntity > 100) return 2;
        else return 1;
    }

    private static double discountPrice() {
        if(getDiscountLevel() == 2) return getBasePrice() * 0.1;
        else return getBasePrice() * 0.05;
    }
}
