package startup.carvaan.Constructor;

import android.net.Uri;

public class Comments {
    Uri imageuri;
    String username;
    String comment;
    public Comments(){}
    public Comments(Uri imageuri, String username, String comment) {
        this.imageuri = imageuri;
        this.username = username;
        this.comment = comment;
    }

    public Uri getImageuri() {
        return imageuri;
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public void setImageuri(Uri imageuri) {
        this.imageuri = imageuri;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
