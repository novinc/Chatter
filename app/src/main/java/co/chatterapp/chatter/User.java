package co.chatterapp.chatter;

/**
 * Created by Putthida Samrith on 10/18/15.
 */
public class User {
    private String name;
    private String message;

    public User() {

    }

    public User(String name, String message) {

        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
