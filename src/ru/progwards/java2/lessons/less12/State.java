package ru.progwards.java2.lessons.less12;

/**
 * Состояние вершины
 */
enum State {
    // Расчет завершен
    DONE,
    // Расчёт в процессе (в очереди)
    CALC,
    // Не рассчитана
    UNDEF};
