package startup.carvaan.Constructor;

import android.net.Uri;

public class Likes {
    Uri imageuri;
    String username;
    public Likes(Uri imageuri, String username)
    {
        this.imageuri = imageuri;
        this.username = username;
    }

    public Uri getImageuri() {
        return imageuri;
    }

    public String getUsername() {
        return username;
    }
}
