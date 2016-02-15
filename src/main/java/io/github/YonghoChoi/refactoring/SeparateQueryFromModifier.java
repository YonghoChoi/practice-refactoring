package io.github.YonghoChoi.refactoring;

/**
 * 값 반환 기능과 객체 상태 변경 기능이 한 메서드에 들어 있을 땐
 * 질의 메서드와 변경 메서드로 분리하자 (get, set 메서드로 분리)
 */
public class SeparateQueryFromModifier {
    public static void main(String[] args) {
        
    }
    
    static void sendAlert(String[] people) {
        if(!foundPerson(people).equals(""))
            sendAlert();
    }

    private static void sendAlert() {
        System.out.println("i found person!");
    }

    private static void checkSecurity(String[] people) {
        String found = foundPerson(people);
        someLaterCode(found);
    }

    private static void someLaterCode(String found) {

    }

    private static String foundPerson(String[] people) {
        for(int i = 0; i < people.length; i++) {
            if(people[i].equals("Don")) {
                return "Don";
            }
            if(people[i].equals("John")) {
                return "John";
            }
        }
        return "";
    }
}
