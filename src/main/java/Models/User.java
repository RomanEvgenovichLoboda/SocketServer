package Models;

import jakarta.persistence.*;
import org.json.JSONObject;

@Entity
@Table (name = "Users")
public class User {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int Id;
    protected String Login;
    protected String Password;
    public User(){}
    public User(String log,String pas){
        this.Id=0;
        this.Login=log;
        this.Password=pas;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
    public String getInfo(){
         return "\n\tId = "+Id+"\n\tLogin = "+Login+"\n\tPassword = "+Password;
    }
    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("Id=").append(Id);
        sb.append(", Login='").append(Login).append('\'');
        sb.append(", Password='").append(Password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
