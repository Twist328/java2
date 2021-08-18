package ru.progwards.java2.lessons.synchro.gc;

class OutOfMemoryException extends Exception {
    public OutOfMemoryException() {}
    public OutOfMemoryException(String message) {
        super(message);
    }
}