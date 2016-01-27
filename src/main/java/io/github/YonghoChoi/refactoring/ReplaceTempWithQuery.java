package io.github.YonghoChoi.refactoring;

public class ReplaceTempWithQuery{
    private int _quantity = 2;
    private int _itemPrice = 3;

    //========================================================================
    // Original Code
    //========================================================================
    public double getPrice() {
        int basePrice = _quantity * _itemPrice;
        double discountFactor;
        if(basePrice > 1000) discountFactor = 0.95;
        else discountFactor = 0.98;
        return basePrice * discountFactor;
    }

    //========================================================================
    // Step 1 : 임시 변수를 메소드 호출로 전환
    //========================================================================
    public double getPrice_step1() {
        int basePrice = basePrice();
        double discountFactor;
        if(basePrice > 1000) discountFactor = 0.95;
        else discountFactor = 0.98;
        return basePrice * discountFactor;
    }

    private int basePrice() {
        return _quantity * _itemPrice;
    }

    //========================================================================
    // Step 2 : Inline Temp 수행 후 임시변수 제거
    //========================================================================
    public double getPrice_step2() {
        double discountFactor;
        if(basePrice() > 1000) discountFactor = 0.95;
        else discountFactor = 0.98;
        return basePrice() * discountFactor;
    }

    //========================================================================
    // Step 3 : discountFactor도 메서드로 추출
    //========================================================================
    public double getPrice_step3() {
        double discountFactor = discountFactor();
        return basePrice() * discountFactor;
    }

    private double discountFactor() {
        if(basePrice() > 1000) return 0.95;
        else return 0.98;
    }

    //========================================================================
    // Step 4 : InlineTemp 수행 후 임시 변수 제거
    //========================================================================
    public double getPrice_step4() {
        return basePrice() * discountFactor();
    }
}
