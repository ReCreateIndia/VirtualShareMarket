package recreate.india.vsm.Constructor;

import android.net.Uri;

public class Comments {
    Uri imageuri;
    String username;
    String comment;

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
}
