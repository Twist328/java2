package ru.progwards.java2.lessons.http.bankomat.service.impl;

import ru.progwards.java2.lessons.http.bankomat.model.Account;
import ru.progwards.java2.lessons.http.bankomat.service.AccountService;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.*;

public class AtmClient implements AccountService {

    private static final int SOCKET_TIMEOUT = 1000;
    String host;
    int port;
    static final String httpPrefix = "HTTP/";
    static final String requestString = "GET /{method} HTTP/1.1\nhostname: localhost\n\n";
    static final String requestBalance = requestString.replace("{method}", "balance?id={id}");
    static final String requestDeposit = requestString.replace("{method}", "deposit?id={id}&amount={amount}");
    static final String requestWithdraw = requestString.replace("{method}", "withdraw?id={id}&amount={amount}");
    static final String requestTransfer = requestString.replace("{method}", "transfer?fromId={fromId}&toId={toId}&amount={amount}");

    public AtmClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public double balance(Account account) {
        if (account == null)
            throw new NullPointerException("account must be set");
        String id = account.getId();
        if (id.isEmpty())
            throw new NullPointerException("account.id must be set");

        String request = requestBalance
                .replace("{id}", id);

        String answer = doRequest(request);

        return Double.valueOf(answer);
    }

    private String doRequest(String request) {
        String code = "";
        boolean nextIsData = false;

        try (
                Socket client = new Socket(host, port);
                PrintWriter pw = new PrintWriter(client.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ) {
            client.setSoTimeout(SOCKET_TIMEOUT);

            pw.println(request);
            pw.flush();

            String line;
            while ((line = br.readLine()) != null) {
                if (nextIsData) {
                    if (!code.equals("200"))
                        throw new RuntimeException(line);
                    return line;
                } else if (code.isEmpty() && line.startsWith(httpPrefix)) {
                    String[] s = line.split(" ");
                    if (s.length > 1) {
                        code = s[1];
                        continue;
                    } else {
                        throw new RuntimeException("HTTP header is bad");
                    }
                } else if (line.isEmpty() && !code.isEmpty()) {
                    nextIsData = true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deposit(Account account, double amount) {
        if (account == null)
            throw new NullPointerException("account must be set");
        String id = account.getId();
        if (id.isEmpty())
            throw new NullPointerException("account.id must be set");

        String request = requestDeposit
                .replace("{id}", id)
                .replace("{amount}", String.valueOf(amount));

        doRequest(request);
    }

    @Override
    public void withdraw(Account account, double amount) {
        if (account == null)
            throw new NullPointerException("account must be set");
        String id = account.getId();
        if (id.isEmpty())
            throw new NullPointerException("account.id must be set");

        String request = requestWithdraw
                .replace("{id}", id)
                .replace("{amount}", String.valueOf(amount));

        doRequest(request);
    }

    @Override
    public void transfer(Account from, Account to, double amount) {
        if (from == null)
            throw new NullPointerException("'from' must be set");
        String fromId = from.getId();
        if (fromId.isEmpty())
            throw new NullPointerException("'from.id' must be set");
        if (to == null)
            throw new NullPointerException("'to' must be set");
        String toId = to.getId();
        if (toId.isEmpty())
            throw new NullPointerException("'to.id' must be set");

        String request = requestTransfer
                .replace("{fromId}", fromId)
                .replace("{toId}", toId)
                .replace("{amount}", String.valueOf(amount));

        doRequest(request);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String id = "cc4842cc-60e5-47ca-9915-dce3499c3445";
        String id2 = "d434ae74-445a-46b8-8ea5-5e37964d80e2";

        Account acc = new Account();
        acc.setId(id);
        Account acc2 = new Account();
        acc2.setId(id2);
        double amount = 10_000;

        Thread.sleep(1000);
        AccountService as = new AtmClient("localhost", 80);
        System.out.println("\n*************************************************************");

        System.out.println("\nbalance operations acc1 : "+as.balance(acc)+(LocalDateTime.now().format(DateTimeFormatter.ofPattern
                ("  dd-MM-YYYY ")) + (LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm \n"))))+requestTransfer+requestWithdraw+requestDeposit+requestBalance);
        as.deposit(acc, amount);
        as.withdraw(acc, amount -80000);
        as.transfer(acc, acc2, amount / 5);
        System.out.println("*************************************************************");
        System.out.println("\nbalance operations acc2 : "+as.balance(acc2)+(LocalDateTime.now().format(DateTimeFormatter.ofPattern
                ("  dd-MM-YYYY ")) + (LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm \n"))))+requestTransfer+requestWithdraw+requestDeposit+requestBalance);
       System.out.println("*************************************************************");
    }
}