package io.github.YonghoChoi.refactoring;

import java.util.Observable;
import java.util.Observer;

public class DuplicateObservedData {
    public static void main(String[] args) {
        IntervalWindow intervalWindow = new IntervalWindow();
        intervalWindow.setEnd("test");
    }
}

class Interval extends Observable {
    private String startField;
    private String endField;
    private String lengthField;

    public String getStartField() {
        return startField;
    }

    public void setStartField(String startField) {
        this.startField = startField;
    }

    public String getEndField() {
        return endField;
    }

    public void setEndField(String endField) {
        this.endField = endField;
        setChanged();
        notifyObservers();
    }

    public String getLengthField() {
        return lengthField;
    }

    public void setLengthField(String lengthField) {
        this.lengthField = lengthField;
    }

    void calculateLength() {
        try {
            int start = Integer.parseInt(startField);
            int end = Integer.parseInt(endField);
            int length = end - start;
            setLengthField(String.valueOf(length));
        } catch (NumberFormatException e) {
            throw new RuntimeException("잘못된 숫자 형식 에러");
        }
    }

    void calculateEnd() {
        try {
            int start = Integer.parseInt(startField);
            int length = Integer.parseInt(lengthField);
            int end = start + length;
            setEndField(String.valueOf(end));
        } catch (NumberFormatException e) {
            throw new RuntimeException("잘못된 숫자 형식 에러");
        }
    }
}

class IntervalWindow implements Observer {
    private Interval subject;

    public IntervalWindow() {
        this.subject = new Interval();
        this.subject.addObserver(this);
        update(this.subject, null);
    }

    String getEnd() {
        return this.subject.getEndField();
    }

    void setEnd(String arg) {
        this.subject.setEndField(arg);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(this.subject.getEndField());
    }
}