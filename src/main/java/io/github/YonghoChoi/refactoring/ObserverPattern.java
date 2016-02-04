package io.github.YonghoChoi.refactoring;

import java.util.Observable;
import java.util.Observer;

public class ObserverPattern {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionDisplay display = new CurrentConditionDisplay(weatherData);

        weatherData.setWeatherData(1.0f, 2.0f, 3.0f);
    }
}

class WeatherData extends Observable {
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
    }

    public void setWeatherData(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    private void measurementsChanged() {
        setChanged();   // 갱신할 새로운 데이터 여부의 플래그 값을 변경
        notifyObservers();  // 옵저버들에게 새로운 데이터를 전달
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }
}

interface DisplayElement {
    public void display();
}

class CurrentConditionDisplay implements Observer, DisplayElement {
    Observable observable;

    private float temperature;
    private float humidity;

    public CurrentConditionDisplay(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof WeatherData) {
            WeatherData weatherData = (WeatherData)o;
            this.temperature = weatherData.getTemperature();
            this.humidity = weatherData.getHumidity();
            display();
        }
    }

    @Override
    public void display() {
        System.out.println("현재 온도 : " + this.temperature + "도, 현재 습도 : " + this.humidity + "%");
    }
}