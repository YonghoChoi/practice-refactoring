package io.github.YonghoChoi.refactoring;

/**
 * 사용된 수식이 복잡할 땐
 * 수식의 결과나 수식의 일부분을 용도에 부합하는 직관적 이름의 임시변수에 대입하자.
 */
public class IntroduceExplaningVariable {
    private int _quantity = 2;
    private int _itemPrice = 3;

    //==================================================================
    // Original Code
    //==================================================================
    public double price() {
        // 결제액(price) = 총 구매액(base price) -
        // 대량 구매 할인(quantity discount) + 배송비(shipping)
        return _quantity * _itemPrice -
                Math.max(0, _quantity - 500) * _itemPrice * 0.05 +
                Math.min(_quantity * _itemPrice * 0.1, 100.0);
    }

    // Introduce Explaining Variable 사용

    //==================================================================
    // Step 1 : 계산의 일부분을 임시변수로
    //==================================================================
    public double price_use_variable_step1() {
        // 결제액(price) = 총 구매액(base price) -
        // 대량 구매 할인(quantity discount) + 배송비(shipping)
        // Extract Variable = Ctrl + Alt + v
        int basePrice = _quantity * _itemPrice;
        return basePrice -
                Math.max(0, _quantity - 500) * _itemPrice * 0.05 +
                Math.min(_quantity * _itemPrice * 0.1, 100.0);
    }

    //==================================================================
    // Step 2 : 임시변수로 추출된 변수로 대체 가능한 부분 변경
    //==================================================================
    public double price_use_variable_step2() {
        // 결제액(price) = 총 구매액(base price) -
        // 대량 구매 할인(quantity discount) + 배송비(shipping)
        // Extract Variable = Ctrl + Alt + v
        int basePrice = _quantity * _itemPrice;
        return basePrice -
                Math.max(0, _quantity - 500) * _itemPrice * 0.05 +
                Math.min(basePrice * 0.1, 100.0);
    }

    //==================================================================
    // Step 3 : 나머지 계산 부분도 임시변수로 추출
    //==================================================================
    public double price_use_variable_step3() {
        // 결제액(price) = 총 구매액(base price) -
        // 대량 구매 할인(quantity discount) + 배송비(shipping)
        // Extract Variable = Ctrl + Alt + v
        int basePrice = _quantity * _itemPrice;
        double quantityDiscount = Math.max(0, _quantity - 500) * _itemPrice * 0.05;
        double shipping = Math.min(basePrice * 0.1, 100.0);
        return basePrice - quantityDiscount + shipping;
    }

    //==================================================================
    // Step 4 : 코드 자체가 주석의 설명과 같아지므로 주석을 제거해도 됨
    //==================================================================
    public double price_use_variable_step4() {
        int basePrice = _quantity * _itemPrice;
        double quantityDiscount = Math.max(0, _quantity - 500) * _itemPrice * 0.05;
        double shipping = Math.min(basePrice * 0.1, 100.0);
        return basePrice - quantityDiscount + shipping;
    }



    // Extract Method 사용

    //==================================================================
    // Step 1 : 복잡한 계산을 메소드로 추출
    //==================================================================
    public double price_extract_method_use_step1() {
        // 결제액(price) = 총 구매액(base price) -
        // 대량 구매 할인(quantity discount) + 배송비(shipping)
        return basePrice() - quantityDiscount() + shipping();
    }

    private double basePrice() {
        return _quantity * _itemPrice;
    }

    private double quantityDiscount() {
        return Math.max(0, _quantity - 500) * _itemPrice * 0.05;
    }

    private double shipping() {
        return Math.min(basePrice() * 0.1, 100.0);
    }

    //==================================================================
    // Step 2 : 주석 제거
    //==================================================================
    public double price_extract_method_use_step2() {
        return basePrice() - quantityDiscount() + shipping();
    }
}
