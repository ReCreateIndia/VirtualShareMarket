package recreate.india.vsm.Constructor;

import java.util.HashMap;

public class PostModal {
    String name;
    String type,description;
    String no_comments;
    String no_likes;

    public PostModal(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PostModal(String name, String type,String no_comments, String no_likes, String description) {
        this.name = name;
        this.type = type;

        this.no_comments = no_comments;
        this.no_likes = no_likes;
        this.description=description;

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

    public String getNo_comments() {
        return no_comments;
    }

    public void setNo_comments(String no_comments) {
        this.no_comments = no_comments;
    }

    public String getNo_likes() {
        return no_likes;
    }

    public void setNo_likes(String no_likes) {
        this.no_likes = no_likes;
    }
}
