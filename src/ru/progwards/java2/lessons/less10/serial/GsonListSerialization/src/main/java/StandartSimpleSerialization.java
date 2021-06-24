import java.io.*;

public class StandartSimpleSerialization {
    public static class User implements Serializable {
        public String login;
        public String password;
        public String name;
        public boolean is_mentor;
        public String image = "";

        public User(String login, String password, String name, boolean is_mentor) {
            this.login = login;
            this.password = password;
            this.name = name;
            this.is_mentor = is_mentor;
        }
    }

    public static void main(String[] args) {
        // сериализуем
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/ru/progwards/java2/lessons/less10/serial/GsonListSerialization/src/main/resources/users.dat"))) {
            User user = new User("login1", "12345", "Фамилия Имя", true);
            oos.writeObject(user);
        } catch(Exception ignored){}

        // десериализуем
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/ru/progwards/java2/lessons/less10/serial/GsonListSerialization/src/main/resources/users.dat"))) {
            User user = (User)ois.readObject();
            System.out.println("Пользователь " + user.login + ", " + user.name);
        } catch(Exception ignored){}
    }
}
