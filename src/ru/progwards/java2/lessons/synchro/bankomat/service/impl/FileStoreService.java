package ru.progwards.java2.lessons.synchro.bankomat.service.impl;

import com.google.gson.Gson;
import ru.progwards.java2.lessons.synchro.bankomat.Store;
import ru.progwards.java2.lessons.synchro.bankomat.model.Account;
import ru.progwards.java2.lessons.synchro.bankomat.service.StoreService;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileStoreService implements StoreService {

    final String fileName = "src/ru/progwards/java2/lessons/synchro/bankomat/service/impl/FileStoreService.json";
    final String fileNameTmp = fileName + ".txt";
    final Gson gson = new Gson();
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public Account get(String id) {
        File inputFile = new File(fileName);

        lock.readLock().lock();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                Account a = strToAccount(currentLine);
                if (a.getId().equals(id)) return a;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
        throw new RuntimeException("Account not found by id: " + id);
    }

    private Account strToAccount(String currentLine) {
        return gson.fromJson(currentLine, Account.class);
    }

    private String strFromAccount(Account account) {
        return gson.toJson(account);
    }

    @Override
    public Collection<Account> get() {
        List<Account> result = new ArrayList<>();
        File inputFile = new File(fileName);

        lock.readLock().lock();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null)
                result.add(strToAccount(currentLine));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
        if (result.size() == 0)
            throw new RuntimeException("Store is empty");

        return result;
    }

    @Override
    public void delete(String id) {

        boolean found = false;
        File inputFile = new File(fileName);
        File tempFile = new File(fileNameTmp);
        String separator = System.getProperty("line.separator");

        lock.writeLock().lock();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (!found && strToAccount(currentLine).getId().equals(id)) {
                    found = true;
                } else {
                    writer.write(currentLine + separator);
                }
            }
            reader.close();
            writer.close();
            tempFile.renameTo(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
        if (!found)
            throw new RuntimeException("Account not found by id: " + id);
    }

    @Override
    public void insert(Account account) {
        if (account == null)
            throw new RuntimeException("Account is null, cannot insert.");
        File inputFile = new File(fileName);
        String separator = System.getProperty("line.separator");
        String newLine = strFromAccount(account);

        lock.writeLock().lock();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile, true))) {
            writer.write(newLine + separator);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void update(Account account) {

        boolean found = false;
        File inputFile = new File(fileName);
        File tempFile = new File(fileNameTmp);
        String separator = System.getProperty("line.separator");
        String newLine = strFromAccount(account);
        String id = account.getId();

        lock.writeLock().lock();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (!found && strToAccount(currentLine).getId().equals(id)) {
                    found = true;
                    writer.write(newLine + separator);
                } else {
                    writer.write(currentLine + separator);
                }
            }
            reader.close();
            writer.close();
            tempFile.renameTo(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
        if (!found)
            throw new RuntimeException("Account not found by id: " + id);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService es = Executors.newFixedThreadPool(10);
        StoreService ss = new FileStoreService();
        SplittableRandom random = new SplittableRandom();

        Callable<?> fillStorageByRandom = () -> {
            Store store = new Store();
            Map<String, Account> list = store.getStore();
            for (Account a : list.values()) {
                ss.insert(a);
            }
            return true;
        };
        es.submit(fillStorageByRandom).get(); // ?????????????????? ?????????????????? ???????????????????? ??????????????

        StoreService fs = new FileStoreService();
        final List<Account> accs = (List<Account>) fs.get(); // ?????????????? ?????? ??????????????????
        final int count = accs.size();

        Callable<?> randomGet = () -> {
            System.out.println("randomGet " + Thread.currentThread().getName()+" started");
            for(int i=1; i<count; i++) {
                int idx = random.nextInt(count);
                Account a = accs.get(idx);
                String id;
                synchronized (a) {
                    id = a.getId();
                    if (id == null) continue;
                    a.setId(null); //?????????? ?????????? ???? ?????????? ???????????????? ???????? ????????????
                }
                if (id!=null)
                    a = ss.get(id);
                a.setId(id);
            }
            System.out.println("randomGet " + Thread.currentThread().getName()+" finished");
            return true;
        };
        Callable<?> randomDeleteInsert = () -> {
            System.out.println("randomDeleteInsert " + Thread.currentThread().getName()+" started");
            for(int i=1; i<count/10; i++) {
                int idx = random.nextInt(count);
                Account a = accs.get(idx);
                String id;
                synchronized (a) {
                    id = a.getId();
                    if (id == null) continue;
                    a.setId(null); //?????????? ?????????? ???? ?????????? ???????????? ???????? ????????????
                }
                ss.delete(id);
                Account acc = new Account();
                acc.setId(id);
                acc.setPin(a.getPin());
                acc.setHolder(a.getHolder());
                acc.setDate(a.getDate());
                acc.setAmount(a.getAmount());
                ss.insert(acc);
                a.setId(id);
            }
            System.out.println("randomDeleteInsert " + Thread.currentThread().getName()+" finished");
            return true;
        };
        Callable<?> randomUpdate = () -> {
            System.out.println("randomUpdate " + Thread.currentThread().getName()+" started");
            for(int i=1; i<count/10; i++) {
                int idx = random.nextInt(count);
                Account a = accs.get(idx);
                synchronized (a) {
                    String id = a.getId();
                    if (id == null) continue;
                }
                a.setAmount(a.getAmount()+123);
                ss.update(a);
            }
            System.out.println("randomUpdate " + Thread.currentThread().getName()+" finished");
            return true;
        };
        System.out.println("\n******************************************");
        System.out.println("Preparation done. Storage Count = "+count);
        long tm = System.currentTimeMillis();
        ArrayList<Future<?>> futures = new ArrayList<>();
        futures.add(es.submit(randomGet));
        futures.add(es.submit(randomDeleteInsert));
        futures.add(es.submit(randomGet));
        futures.add(es.submit(randomUpdate));
        futures.add(es.submit(randomGet));
        futures.add(es.submit(randomGet));
        futures.add(es.submit(randomGet));
        for (Future f: futures)
            f.get();
        System.out.println("All tasks done in "+(System.currentTimeMillis()-tm)+" ms");

        es.shutdown();
        System.out.println("******************************************");
        /* ?????????????????? ?? ??????????????:

Preparation done. Storage Count = 43
randomDeleteInsert pool-1-thread-3 started
randomGet pool-1-thread-4 started
randomGet pool-1-thread-2 started
randomGet pool-1-thread-6 started
randomGet pool-1-thread-8 started
randomGet pool-1-thread-7 started
randomUpdate pool-1-thread-5 started
randomUpdate pool-1-thread-5 finished
randomDeleteInsert pool-1-thread-3 finished
randomGet pool-1-thread-8 finished
randomGet pool-1-thread-2 finished
randomGet pool-1-thread-7 finished
randomGet pool-1-thread-4 finished
randomGet pool-1-thread-6 finished
All tasks done in 298 ms

Process finished with exit code 0s*/

    }
}
