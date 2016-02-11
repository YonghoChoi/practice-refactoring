package io.github.YonghoChoi.refactoring;

public class ReplaceTypeCodeWithStrategy {
    class Employee {
        private int type;
        static final int ENGINEER = 0;
        static final int SALESMAN = 1;
        static final int MANAGER = 2;
        private int monthlySalary;
        private int commission;
        private int bonus;

        public Employee(int type) {
            //this.type = type;
            setType(type);  // 분류 부호 자체캡슐화 후 set메서드 이용
        }

        int payAmount() {
            switch(getType()) {
                case ENGINEER:
                    return monthlySalary;
                case SALESMAN:
                    return monthlySalary + commission;
                case MANAGER:
                    return monthlySalary + bonus;
                default:
                    throw new RuntimeException("사원이 잘못됨");
            }
        }

        //======================================================================
        // Step 1 : 분류 부호 자체 캡슐화
        //======================================================================
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    //======================================================================
    // Step 4 : 하위클래스를 Employee클래스에 적용
    //======================================================================
    static class Employee2 {
        private EmployeeType type;
        private int monthlySalary;
        private int commission;
        private int bonus;

        public Employee2(int type) {
            setType(type);  // 분류 부호 자체캡슐화 후 set메서드 이용
        }

        int payAmount() {
            switch(getType()) {
                case EmployeeType.ENGINEER:
                    return monthlySalary;
                case EmployeeType.SALESMAN:
                    return monthlySalary + commission;
                case EmployeeType.MANAGER:
                    return monthlySalary + bonus;
                default:
                    throw new RuntimeException("사원이 잘못됨");
            }
        }

        public int getType() {
            return this.type.getTypeCode();
        }

        public void setType(int arg) {
            this.type = EmployeeType.newType(arg);
        }

        //======================================================================
        // Step 2 : 상태 클래스 선언
        //======================================================================
        static abstract class EmployeeType {
            static EmployeeType newType(int code) {
                switch(code) {
                    case ENGINEER:
                        return new Engineer();
                    case SALESMAN:
                        return new Salesman();
                    case MANAGER:
                        return new Manager();
                    default:
                        throw new IllegalArgumentException("사원 부호가 잘못됨");
                }
            }

            static final int ENGINEER = 0;
            static final int SALESMAN = 1;
            static final int MANAGER = 2;

            abstract int getTypeCode();
        }

        //======================================================================
        // Step 3 : 분류 부호별 하위 클래스 작성
        //======================================================================
        static class Engineer extends EmployeeType {
            int getTypeCode() {
                return Employee.ENGINEER;
            }
        }

        static class Salesman extends EmployeeType {
            int getTypeCode() {
                return Employee.SALESMAN;
            }
        }

        static class Manager extends EmployeeType {
            int getTypeCode() {
                return Employee.MANAGER;
            }
        }
    }
}
