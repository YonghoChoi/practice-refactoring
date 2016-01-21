package io.github.YonghoChoi.refactoring;

import java.util.Enumeration;
import java.util.Vector;

public class ExtractMethod {
    static String _name = "Roi";
    static Vector<Order> _orders = new Vector<>();

    public static void main(String[] args) {
        _orders.add(new Order(1));
        _orders.add(new Order(2));
        _orders.add(new Order(3));

        printOwing();
    }

    //=================================================================================
    // Original
    //=================================================================================
    public static void printOwing() {
        Enumeration e = _orders.elements();
        double outstanding = 0.0;

        // print banner
        System.out.println ("**************************");
        System.out.println ("***** Customer Owes ******");
        System.out.println ("**************************");

        // calculate outstanding
        while (e.hasMoreElements()) {
            Order each = (Order) e.nextElement();
            outstanding += each.getAmount();
        }

        //print details
        System.out.println ("name:" + _name);
        System.out.println("amount: " + outstanding);
    }


    //=================================================================================
    // Step 1 : 배너 추출
    //=================================================================================
    public static void printOwing_Step1() {
        Enumeration e = _orders.elements();
        double outstanding = 0.0;

        printBanner();

        // calculate outstanding
        while (e.hasMoreElements()) {
            Order each = (Order) e.nextElement();
            outstanding += each.getAmount();
        }

        //print details
        System.out.println ("name:" + _name);
        System.out.println("amount: " + outstanding);
    }

    private static void printBanner() {
        // print banner
        System.out.println ("**************************");
        System.out.println ("***** Customer Owes ******");
        System.out.println ("**************************");
    }

    //=================================================================================
    // Step2 : 지역 변수가 포함되어 있는 경우 파라미터로 전달 (변수의 값이 변하지 않는 경우에는 그냥 전달만 하면 됨)
    //=================================================================================
    public static void printOwing_Step2() {
        Enumeration e = _orders.elements();
        double outstanding = 0.0;

        printBanner();

        // calculate outstanding
        while (e.hasMoreElements()) {
            Order each = (Order) e.nextElement();
            outstanding += each.getAmount();
        }

        printDetails(outstanding);
    }

    private static void printDetails(double outstanding) {
        System.out.println ("name:" + _name);
        System.out.println ("amount: " + outstanding);
    }

    //=================================================================================
    // Step3 : 지역 변수에 다른 값을 여러번 대입하는 경우 (계산 부분의 코드만 뽑는다.)
    //=================================================================================
    public static void printOwing_Step3() {
        printBanner();
        double outstanding = getOutstanding();
        printDetails(outstanding);
    }

    private static double getOutstanding() {
        Enumeration e = _orders.elements();
        double outstanding = 0.0;
        while (e.hasMoreElements()) {
            Order each = (Order) e.nextElement();
            outstanding += each.getAmount();
        }
        return outstanding;
    }

    //=================================================================================
    // Step4 : 만약 변수와 관련된 다른 코드가 있다면 이전의 값을 파라미터로 넘겨야 한다.
    //=================================================================================
    public static void printOwing_Step4(double previousAmount) {
        printBanner();
        double outstanding = previousAmount * 1.2;
        outstanding = getOutstanding(outstanding);
        printDetails(outstanding);
    }

    private static double getOutstanding(double initialValue) {
        Enumeration e = _orders.elements();
        double outstanding = initialValue;
        while (e.hasMoreElements()) {
            Order each = (Order) e.nextElement();
            outstanding += each.getAmount();
        }
        return outstanding;
    }

    //=================================================================================
    // Step5 : 컴파일과 테스트를 한 후에 outstanding 변수를 초기화하는 방법을 바꾼다.
    //=================================================================================
    public static void printOwing_Step5(double previousAmount) {
        printBanner();
        double outstanding = getOutstanding(previousAmount * 1.2);
        printDetails(outstanding);
    }

    public static class Order {
        private int amount;

        public Order(int amount) {
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }
    }
}
