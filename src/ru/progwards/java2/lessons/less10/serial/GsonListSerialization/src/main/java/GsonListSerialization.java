import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class GsonListSerialization {


    @Override
    public String toString() {
        return "GsonListSerialization{}";
    }
    public static class User implements Serializable {
        public String login;
        public String password;
        public String name;
        public boolean is_mentor;
        public transient String image = "";

        public User(String login, String password, String name, boolean is_mentor) {
            this.login = login;
            this.password = password;
            this.name = name;
            this.is_mentor = is_mentor;
        }
    }

    public GsonListSerialization() {
        super();
    }

    public static void main(String[] args) throws IOException {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("login1", "password1", "name1", false));
        users.add(new User("login2", "password2", "name2", true));
        users.add(new User("login3", "password3", "name3", false));

        // сериализуем в JSON
        String json = new Gson().toJson(users);
        Files.writeString(Path.of("users.json"), json);

        // десериализуем из JSON
        json = Files.readString(Path.of("users.json"));
        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        ArrayList<User> list = new Gson().fromJson(json, type);
        System.out.println("\n******************************************************************************************");
        System.out.println(json.toString());
        System.out.println("******************************************************************************************");

    }

}

