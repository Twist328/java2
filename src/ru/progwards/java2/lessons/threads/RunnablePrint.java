package ru.progwards.java2.lessons.threads;

class RunnablePrint implements Runnable {
    String docName;
    int pages;

    public RunnablePrint(String docName, int pages) {
        this.docName = docName;
        this.pages = pages;
    }

    @Override
    public void run() {
        PrintScan.print(docName, pages);
        PrintScan.scan(docName, pages);
    }
}
