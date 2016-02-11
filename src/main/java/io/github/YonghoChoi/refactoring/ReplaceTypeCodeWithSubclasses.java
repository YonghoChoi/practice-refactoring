package io.github.YonghoChoi.refactoring;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ReplaceTypeCodeWithSubclasses {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(Employee.create(Employee.ENGINEER));
        employees.add(Employee.create(Employee.MANAGER));
        employees.add(Employee.create(Employee.SALESMAN));

        for(Employee e : employees) {
            System.out.println(e);
        }
    }
}


abstract class Employee {
//    private int type;   // 분류 부호
    static final int ENGINEER = 0;
    static final int SALESMAN = 1;
    static final int MANAGER = 2;

//    Employee(int type) {
//        this.type = type;
//    }

    //=====================================================================
    // Step 1 : 분류 부호에 필드 자체 캡슐화(Self Encapsulate Field) 적용
    //=====================================================================
//    public int getType() {
//        return type;
//    }

    //=====================================================================
    // Step 2 : 생성자 메서드를 팩토리 메서드로 변경 (생성자는 private로 변경)
    //=====================================================================
//    Employee create(int type) {
//        return new Employee(type);
//    }

    //=====================================================================
    // Step 4 : 하위 클래스가 정의 되면 적절한 객체를 생성하기 위해 팩토리 메서드도 수정
    //=====================================================================
//    static Employee create(int type) {
//        if(type == ENGINEER) return new Engineer();
//        else return new Employee(type);
//    }

    //=====================================================================
    // Step 6 : 분류 부호 제거 후 abstract 타입의 getType 메서드 작성
    //=====================================================================
    abstract int getType();

    //=====================================================================
    // Step 7 : 팩토리 메서드 수정
    //=====================================================================
    static Employee create(int type) {
        switch(type) {
            case ENGINEER:
                return new Engineer();
            case MANAGER:
                return new Manager();
            case SALESMAN:
                return new Salesman();
            default:
                throw new IllegalArgumentException("분류 부호 값이 잘못됨");
        }
    }
}

//=====================================================================
// Step 3 : 분류 부호 변수를 Employee의 하위 클래스로 만들고
// 분류 부호에 해당하는 재정의 메서드를 작성
//=====================================================================
class Engineer extends Employee {
    @Override
    public int getType() {
        return Employee.ENGINEER;
    }

    @Override
    public String toString() {
        return "Engineer{}";
    }
}

//=====================================================================
// Step 5 : 나머지 분류 부호도 하위 클래스로 수정
//=====================================================================
class Manager extends Employee {
    @Override
    public int getType() {
        return Employee.MANAGER;
    }

    @Override
    public String toString() {
        return "Manager{}";
    }
}

class Salesman extends Employee {
    @Override
    public int getType() {
        return Employee.SALESMAN;
    }

    @Override
    public String toString() {
        return "Salesman{}";
    }
}