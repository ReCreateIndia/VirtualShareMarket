package recreate.india.vsm.Constructor;

import java.util.HashMap;

public class PostModal {
    String name;
    String type;
    int no_comments;
    int no_likes;
    HashMap<String,String> likes;
    HashMap<String,String> comments;
    public PostModal(){}

    public PostModal(String name, String type,HashMap<String,String> likes, HashMap<String,String> comments, int no_comments, int no_likes) {
        this.name = name;
        this.type = type;
        this.comments = comments;
        this.likes = likes;
        this.no_comments = no_comments;
        this.no_likes = no_likes;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNo_comments() {
        return no_comments;
    }

    public void setNo_comments(int no_comments) {
        this.no_comments = no_comments;
    }

    public int getNo_likes() {
        return no_likes;
    }

    public void setNo_likes(int no_likes) {
        this.no_likes = no_likes;
    }

    public HashMap<String, String> getLikes() {
        return likes;
    }

    public void setLikes(HashMap<String, String> likes) {
        this.likes = likes;
    }

    public HashMap<String, String> getComments() {
        return comments;
    }

    public void setComments(HashMap<String, String> comments) {
        this.comments = comments;
    }
}
