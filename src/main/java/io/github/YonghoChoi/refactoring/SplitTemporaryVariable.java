package io.github.YonghoChoi.refactoring;

/**
 * 루프 변수나 값 누적용 임시변수가 아닌 임시변수에 여러 번 값이 대입 될 땐
 * 각 대입마다 다른 임시변수를 사용하자
 */
public class SplitTemporaryVariable {
    private double _primaryForce = 1.0;
    private double _mass = 1.0;
    private int _delay = 100;
    private double _secondaryForce = 1.0;

    //===============================================================
    // Original Code
    //===============================================================
    public double getDistanceTravelled(int time) {
        double result;
        double acc = _primaryForce / _mass;
        int primaryTime = Math.min(time, _delay);
        result = 0.5 * acc * primaryTime * primaryTime;
        int secondaryTime = time - _delay;
        if(secondaryTime > 0) {
            double primaryVel = acc * _delay;
            acc = (_primaryForce + _secondaryForce) / _mass;
            result += primaryVel * secondaryTime + 0.5 *
                    acc * secondaryTime * secondaryTime;
        }

        return result;
    }

    //===============================================================
    // Step 1 : 임시변수의 이름을 변경하고 final로 선언
    //===============================================================
    public double getDistanceTravelled_step1(int time) {
        double result;
        final double primaryAcc = _primaryForce / _mass;
        int primaryTime = Math.min(time, _delay);
        result = 0.5 * primaryAcc * primaryTime * primaryTime;
        int secondaryTime = time - _delay;
        if(secondaryTime > 0) {
            double primaryVel = primaryAcc * _delay;
            double acc = (_primaryForce + _secondaryForce) / _mass;
            result += primaryVel * secondaryTime + 0.5 *
                    acc * secondaryTime * secondaryTime;
        }

        return result;
    }

    //===============================================================
    // Step 2 : 두 번째 임시변수의 이름을 변경하고 final로 선언
    //===============================================================
    public double getDistanceTravelled_step2(int time) {
        double result;
        final double primaryAcc = _primaryForce / _mass;
        int primaryTime = Math.min(time, _delay);
        result = 0.5 * primaryAcc * primaryTime * primaryTime;
        int secondaryTime = time - _delay;
        if(secondaryTime > 0) {
            double primaryVel = primaryAcc * _delay;
            final double secondaryAcc = (_primaryForce + _secondaryForce) / _mass;
            result += primaryVel * secondaryTime + 0.5 *
                    secondaryAcc * secondaryTime * secondaryTime;
        }

        return result;
    }
}
