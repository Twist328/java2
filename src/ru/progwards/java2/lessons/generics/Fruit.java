package ru.progwards.java2.lessons.generics;

public class Fruit {
    public double weight = 1.0;

    void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public double getWeight() {
        return weight;
    }
}
