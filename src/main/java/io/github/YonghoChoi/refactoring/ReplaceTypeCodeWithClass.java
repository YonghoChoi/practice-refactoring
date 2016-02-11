package io.github.YonghoChoi.refactoring;

public class ReplaceTypeCodeWithClass {
    public static void main(String[] args) {
        Person person = new Person(Person.A);
        System.out.println(person.getBloodGroup());
        person.setBloodGroup(Person.AB);
        System.out.println(person.getBloodGroup());

        NewPerson newPerson = new NewPerson(BloodGroup.A);
        System.out.println(newPerson.getBloodGroup().getCode());
        newPerson.setBloodGroup(BloodGroup.AB);
        System.out.println(newPerson.getBloodGroup().getCode());

        EnumPerson enumPerson = new EnumPerson(EnumPerson.BloodGroupEnum.A);
        System.out.println(enumPerson.getBloodGroupValue());
        enumPerson.setBloodGroup(EnumPerson.BloodGroupEnum.AB);
        System.out.println(enumPerson.getBloodGroupValue());
    }

    static class Person {
        public static final int O = 0;
        public static final int A = 1;
        public static final int B = 2;
        public static final int AB = 3;

        private int bloodGroup;

        public Person(int bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public int getBloodGroup() {
            return bloodGroup;
        }

        public void setBloodGroup(int bloodGroup) {
            this.bloodGroup = bloodGroup;
        }
    }

    //====================================================================================
    // Step 1 : 혈액형 그룹을 판단할 BloodGroup 클래스를 작성
    //====================================================================================
    static class BloodGroup {
        public static final BloodGroup O = new BloodGroup(0);
        public static final BloodGroup A = new BloodGroup(1);
        public static final BloodGroup B = new BloodGroup(2);
        public static final BloodGroup AB = new BloodGroup(3);
        private static final BloodGroup[] values = {O, A, B, AB};

        private final int code;

        public BloodGroup(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static BloodGroup code(int arg) {
            return values[arg];
        }
    }

    //====================================================================================
    // Step 2 : 새 클래스(BloodGroup)를 사용
    //====================================================================================
    static class NewPerson {
        public static final int O = BloodGroup.O.getCode();
        public static final int A = BloodGroup.A.getCode();
        public static final int B = BloodGroup.B.getCode();
        public static final int AB = BloodGroup.AB.getCode();

        private BloodGroup bloodGroup;

        public NewPerson(int bloodGroup) {
            this.bloodGroup = BloodGroup.code(bloodGroup);
        }

        public NewPerson(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public int getBloodGroupCode() {
            return bloodGroup.getCode();
        }

        public BloodGroup getBloodGroup() {
            return bloodGroup;
        }

        public void setBloodGroup(BloodGroup bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public void setBloodGroup(int bloodGroup) {
            this.bloodGroup = BloodGroup.code(bloodGroup);
        }
    }

    static class EnumPerson {
        // nested enum type은 무조건 static이다.
        // inner class는 상수 변수를 제외한 static 선언을 가질 수 없으므로 static nested class가 되어야 한다.
        enum BloodGroupEnum {
            O(0),
            A(1),
            B(2),
            AB(3);

            BloodGroupEnum(int value) {
                this.value = value;
            }

            private final int value;

            public int getValue() {
                return value;
            }
        }
        public BloodGroupEnum bloodGroup = BloodGroupEnum.A;
        public EnumPerson(BloodGroupEnum bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public BloodGroupEnum getBloodGroup() {
            return bloodGroup;
        }

        public int getBloodGroupValue() {
            return bloodGroup.getValue();
        }

        public void setBloodGroup(BloodGroupEnum bloodGroup) {
            this.bloodGroup = bloodGroup;
        }
    }
}