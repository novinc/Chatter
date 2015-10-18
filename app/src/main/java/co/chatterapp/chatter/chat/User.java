package co.chatterapp.chatter.chat;

import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by PutthidaSR on 10/18/15.
 */
public class User {

    private ImageView authorImg;
    private String name;
    private String message;

    public User() {

    }

    public User(ImageView authorImg, String name, String message) {
        this.authorImg = authorImg;
        this.name = name;
        this.message = message;
    }

    public ImageView getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(ImageView authorImg) {
        this.authorImg = authorImg;
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

    @Override
    public String toString() {
        return "User{" +
                "authorImg=" + authorImg +
                ", author='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}