package io.github.YonghoChoi.refactoring;

/**
 * 여러 하위클래스가 상수 데이터를 반환하는 메서드만 다를 땐
 * 각 하위클래스의 메서드를 상위클래스 필드로 전환하고 하위클래스는 전부 삭제하자
 */
public class ReplaceSubclassWithFields {
    public static void main(String[] args) {
        // 하위 클래스 사용
        Male male = new Male();
        System.out.println(male);
        Female female = new Female();
        System.out.println(female);

        // 하위 클래스 제거
        NewPerson newMale = NewPerson.createMale();
        System.out.println(newMale);
        NewPerson newFemale = NewPerson.createFeale();
        System.out.println(newFemale);
    }
    static abstract class Person {
        abstract boolean isMale();
        abstract char getCode();
    }

    static class Male extends Person {
        @Override
        boolean isMale() {
            return true;
        }

        @Override
        char getCode() {
            return 'M';
        }

        @Override
        public String toString() {
            return "Male";
        }
    }

    static class Female extends Person {
        @Override
        boolean isMale() {
            return false;
        }

        @Override
        char getCode() {
            return 'F';
        }

        @Override
        public String toString() {
            return "Female";
        }
    }

    // Male 하위클래스와 Female 하위클래스는 하드코딩된 상수 메서드 반환만 다르다.
    // 이렇게 기능에 충실하지 못한 하위클래스는 삭제하자
    static class NewPerson {
        private final boolean isMale;
        private final char code;

        public NewPerson(boolean isMale, char code) {
            this.isMale = isMale;
            this.code = code;
        }

        public boolean isMale() {
            return isMale;
        }

        public char getCode() {
            return code;
        }

        static NewPerson createMale() {
            return new NewPerson(true, 'M');
        }

        static NewPerson createFeale() {
            return new NewPerson(false, 'F');
        }

        @Override
        public String toString() {
            return isMale == true ? "Male" : "Female";
        }
    }
}
