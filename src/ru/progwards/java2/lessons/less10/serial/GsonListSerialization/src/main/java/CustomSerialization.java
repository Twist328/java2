import java.io.*;
import java.util.Base64;

public class CustomSerialization {
    public static class User implements Serializable {
        public String login;
        public transient String password;
        public String name;
        public boolean is_mentor;
        public String image = "";

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
            String encodedString =
                    Base64.getEncoder().withoutPadding().encodeToString(password.getBytes());
            out.writeObject(encodedString);
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            String password = (String) in.readObject();
            this.password = new String(Base64.getDecoder().decode(password));
        }


        public User(String login, String password, String name, boolean is_mentor) {
            this.login = login;
            this.password = password;
            this.name = name;
            this.is_mentor = is_mentor;
        }
    }

    public static void main(String[] args) {
        // сериализуем
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            User user = new User("login1", "12345", "Фамилия Имя", true);
            oos.writeObject(user);
        } catch(Exception ignored){}

        // десериализуем
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))) {
            User user = (User)ois.readObject();
            System.out.println("Пользователь " + user.login + ", пароль: " + user.password);
        } catch(Exception ignored){}
    }
}


