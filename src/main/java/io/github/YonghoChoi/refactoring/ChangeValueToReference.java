package io.github.YonghoChoi.refactoring;

import java.util.*;

/**
 * 클래스에 같은 인스턴스가 많이 들어 있어서 이것들을 하나의 객체로 바꿔야 할 땐
 * 그 객체를 참조 객체로 전환하자.
 */
public class ChangeValueToReference {
    public static void main(String[] args) {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("Yongho"));
        orders.add(new Order("Yongho"));
        orders.add(new Order("Yongho"));
        orders.add(new Order("Yesol"));

        // 여기서 Order를 한 Customer는 값 객체다.
        // 값 객체라는 말에 혼동을 하기 쉬운데 여기서의 값 객체는 개념을 의미하는 것으로
        // 리펙토링 책에 보면 값 객체는 날짜나 돈 같이 전적으로 데이터 값을 통해서만 정의된 것이라고 명시되어 있다.
        // 그래서 Yongho라는 동일한 값을 지닌 Customer가 여러개 포함되므로 값 객체로 보는 것이다.
        System.out.println(numberOfOrdersFor(orders, "Yongho"));


        RefactoringCustomer.loadCustomers();
        List<RefactoringOrder> refactoringOrders = new ArrayList<>();
        refactoringOrders.add(new RefactoringOrder("Yongho"));
        refactoringOrders.add(new RefactoringOrder("Yongho"));
        refactoringOrders.add(new RefactoringOrder("Yongho"));
        refactoringOrders.add(new RefactoringOrder("Yesol"));
        System.out.println(numberOfRefactoringOrdersFor(refactoringOrders, "Yongho"));
    }

    public static int numberOfOrdersFor(Collection orders, String customer) {
        int result = 0;
        Iterator iter = orders.iterator();
        while(iter.hasNext()) {
            Order each = (Order)iter.next();
            if(each.getCustomerName().equals(customer)) result++;
        }
        return result;
    }

    public static int numberOfRefactoringOrdersFor(Collection orders, String customer) {
        int result = 0;
        Iterator iter = orders.iterator();
        while(iter.hasNext()) {
            RefactoringOrder each = (RefactoringOrder)iter.next();
            if(each.getCustomerName().equals(customer)) result++;
        }
        return result;
    }
}

class Customer {
    String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Order {
    Customer customer;
    public Order(String customerName) {
        this.customer = new Customer(customerName);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCustomerName() {
        return customer.getName();
    }
}

class RefactoringCustomer {
    String name;
    private static Dictionary instances = new Hashtable<>();

    private RefactoringCustomer(String name) {
        this.name = name;
    }

    static void loadCustomers() {
        new RefactoringCustomer("우리 렌터카").store();
        new RefactoringCustomer("커피 자판기 운영업 협동조합").store();
        new RefactoringCustomer("삼천리 가스 공장").store();
        new RefactoringCustomer("Yongho").store();
        new RefactoringCustomer("Yesol").store();
    }

    private void store() {
        instances.put(this.getName(), this);
    }

    public static RefactoringCustomer getNamed(String name) {
        return (RefactoringCustomer)instances.get(name);
    }

    public String getName() {
        return name;
    }
}

class RefactoringOrder {
    RefactoringCustomer customer;
    public RefactoringOrder(String customerName) {
        this.customer = RefactoringCustomer.getNamed(customerName);
    }

    public void setCustomer(RefactoringCustomer customer) {
        this.customer = customer;
    }

    public String getCustomerName() {
        return customer.getName();
    }
}