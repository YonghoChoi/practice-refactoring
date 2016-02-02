package io.github.YonghoChoi.refactoring;

/**
 * 클래스에 자잘한 위임이 너무 많을 땐 대리 객체를 클라이언트가 직접 호출하게 하자.
 */
public class RemoveMiddleMan {
    public static void main(String[] args) {
        Person2 manager = new Person2("simon");
        Department2 department= new Department2(manager);
        department.setAddress("유레카 빌딩");
        department.setPhone("010");
        department.setChargeCode("프로그램실");

        Person2 yongho = new Person2("yongho");
        yongho.setDepartment(department);
        yongho.setManager(manager);

        // Department에 필드가 추가될때마다 위임 메서드가 늘어난다.
        System.out.println(yongho.getManager().getName());
        System.out.println(yongho.getPhone());
        System.out.println(yongho.getAddress());

        // 위임 메서드가 너무 많아진다 싶을 때는 위임 메서드를 제거하고 대리객체에서의 메서드 호출로 교체하자.
        System.out.println(yongho.getDepartment().getManager().getName());
        System.out.println(yongho.getDepartment().getPhone());
        System.out.println(yongho.getDepartment().getAddress());
    }
}

class Person2 {
    String _name;
    Department2 _department;

    public Person2(String _name) {
        this._name = _name;
    }

    public String getName() {
        return _name;
    }

    public Department2 getDepartment() {
        return _department;
    }

    public void setDepartment(Department2 _department) {
        this._department = _department;
    }

    // 클라이언트가 Department에 대해 몰라도 되도록 위임
    public Person2 getManager() {
        return _department.getManager();
    }

    public void setManager(Person2 _person) {
        _department.setManager(_person);
    }

    // 추가로 작성된 Department 필드를 위임
    public String getAddress() {
        return _department.getAddress();
    }

    public void setAddress(String _address) {
        this._department.setAddress(_address);
    }

    public String getPhone() {
        return _department.getPhone();
    }

    public void setPhone(String _phone) {
        this._department.setPhone(_phone);
    }
}

class Department2 {
    private String _chargeCode;
    private Person2 _manager;
    private String _phone;
    private String _address;

    public Department2(Person2 manager) {
        _manager = manager;
    }

    public Person2 getManager() {
        return _manager;
    }

    public void setManager(Person2 _manager) {
        this._manager = _manager;
    }

    public String getChargeCode() {
        return _chargeCode;
    }

    public void setChargeCode(String _chargeCode) {
        this._chargeCode = _chargeCode;
    }

    public String getAddress() {
        return _address;
    }

    public void setAddress(String _address) {
        this._address = _address;
    }

    public String getPhone() {
        return _phone;
    }

    public void setPhone(String _phone) {
        this._phone = _phone;
    }
}