package io.github.YonghoChoi.refectoring;

public class InlineMethod {
    private static int _numberOfLateDeliveries;

    public static void main(String[] args) {
        System.out.println(getRating());
        System.out.println(getRatingInline());
    }

    //=============================================================================
    // Original Code
    //=============================================================================
    private static int getRating() {
        return (moreThanFiveLateDeliveries()) ? 2: 1;
    }

    private static boolean moreThanFiveLateDeliveries() {
        return _numberOfLateDeliveries > 5;
    }

    //=============================================================================
    // Step 1 : 메소드 몸체가 메소드의 이름만큼이나 명확할 때는
    //          호출하는 곳에 메소드의 몸체를 넣고 메소드를 삭제
    //          Refector->inline (Ctrl + Alt + N)
    //=============================================================================
    private static int getRatingInline() {
        return (_numberOfLateDeliveries > 5) ? 2: 1;
    }
}
