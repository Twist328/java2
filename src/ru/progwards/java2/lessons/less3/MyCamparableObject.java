package ru.progwards.java2.lessons.less3;

class MyCamparableObject<T extends Comparable>{//допустим, нужно сравнить разные объекты
    public T max(T... items){
        T result = items[0];
        for(int i = 1; i<items.length; i++){
            if (result.compareTo(items[i]) == -1){
                result = items[i];
            }
        }
        return result;
    }
}
