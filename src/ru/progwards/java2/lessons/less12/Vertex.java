package ru.progwards.java2.lessons.less12;
/**
 * @program: java2
 * @description:
 * @autor: twist328
 * @create: 2021-07-21 10
 **/
 class Vertex {
    /**
     * Порядковый номер (с нуля)
     */
    int n;
    /**
     * Вес (сколько стоит добраться)
     */
    int weight;
    /**
     * Состояние
     *
     * @see State
     */
    State status;

    @Override
    public String toString() {
        return "Vertex{n=" + n + ", weight=" + weight + '}';
    }
}
