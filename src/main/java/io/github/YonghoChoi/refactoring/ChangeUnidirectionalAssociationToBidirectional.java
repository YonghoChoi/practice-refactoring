package io.github.YonghoChoi.refactoring;

import java.util.HashSet;
import java.util.Set;

/**
 * 두 클래스가 서로의 기능을 사용해야 하는데 한 방향으로만 연결되어 있을 땐
 * 역 포인터를 추가하고 두 클래스를 모두 업데이트할 수 있게 접근 한정자를 수정하자
 */
public class ChangeUnidirectionalAssociationToBidirectional {
    class Order {
        Customer customer;

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            if(customer != null) customer.friendOrders().remove(this);
            this.customer = customer;
            if(customer != null) customer.friendOrders().add(this);
        }
    }

    class Customer {
        private Set orders = new HashSet();

        Set friendOrders() {
            // 연결을 변경할 때 Order에 의해서만 사용되어야 함
            return orders;
        }

        void addOrder(Order arg) {
            arg.setCustomer(this);
        }
    }
}
