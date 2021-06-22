import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class StandartTransientSerialization {
    public static class User implements Serializable {
        public String login;
        public transient String password;
        public transient String name;
        public transient boolean is_mentor;
        public transient String image = "";

        public User(String login, String password, String name, boolean is_mentor) {
            this.login = login;
            this.password = password;
            this.name = name;
            this.is_mentor = is_mentor;
        }
    }

    public static void main(String[] args) {
        // сериализуем
//        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
//            User user = new User("login1", "12345", "Фамилия Имя", true);
//            oos.writeObject(user);
//        } catch(Exception ignored){}

        // десериализуем
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))) {
            User user = (User)ois.readObject();
            System.out.println("Пользователь " + user.login + ", " + user.name);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

