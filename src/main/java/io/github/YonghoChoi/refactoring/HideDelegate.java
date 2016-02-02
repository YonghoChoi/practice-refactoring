package io.github.YonghoChoi.refactoring;

/**
 * 클라이언트 객체의 대리 클래스를 호출 할 떈,
 * 대리 클래스를 감추는 메서드를 서버에 작성하자.
 */
public class HideDelegate {
    public static void main(String[] args) {
        Person manager = new Person("simon");

        // john의 팀장을 알아내려면 먼저 부서를 알아내야 한다.
        Person yongho = new Person("yongho");
        yongho.setDepartment(new Department(yongho));
        yongho.getDepartment().setManager(manager);
        System.out.println("yongho의 팀장은? " + yongho.getDepartment().getManager().getName());

        // 이런 의존성을 줄이려면 Department클래스를 클라이언트가 알 수 없게 감춰야 한다.
        // 이 후 getDepartment()가 필요 없어 지는 경우 제거한다. (병행해서 사용해야 하는 경우도 있으므로 상황에 따라)
        Person jjong = new Person("jjong");
        jjong.setDepartment(new Department(jjong));
        jjong.setManager(manager);
        System.out.println("jjong의 팀장은? " + jjong.getManager().getName());
    }
}

class Person {
    String _name;
    Department _department;

    public Person(String _name) {
        this._name = _name;
    }

    public String getName() {
        return _name;
    }

    public Department getDepartment() {
        return _department;
    }

    public void setDepartment(Department _department) {
        this._department = _department;
    }

    // 클라이언트가 Department에 대해 몰라도 되도록 위임
    public Person getManager() {
        return _department.getManager();
    }

    public void setManager(Person _person) {
        _department.setManager(_person);
    }
}

class Department {
    private String _chargeCode;
    private Person _manager;

    public Department(Person manager) {
        _manager = manager;
    }

    public Person getManager() {
        return _manager;
    }

    public void setManager(Person _manager) {
        this._manager = _manager;
    }

    public String getChargeCode() {
        return _chargeCode;
    }

    public void setChargeCode(String _chargeCode) {
        this._chargeCode = _chargeCode;
    }
}