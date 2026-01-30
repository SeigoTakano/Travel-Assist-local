package bean;

public class Postmanage {
    private int post_number;
    private String title;
    private String user_name;
    private String imagePath;
    private String post_date;
    
    public Postmanage(int post_number, String user_name, String title) {
        this.post_number = post_number;
        this.user_name = user_name;
        this.title = title;
    }



    // getter / setter
    public int getPost_number() { return post_number; }
    public void setPost_number(int post_number) { this.post_number = post_number; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    

    public String getUser_name() { return user_name; }
    public void setUser_name(String user_name) { this.user_name = user_name; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getCreatedAt() { return post_date; }
    public void setCreatedAt(String post_date) { this.post_date = post_date; }
}
