package bean;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Post implements Serializable {
    private int postNumber;
    private String title;
    private String impression;
    private String username;
    private String imagepass;
    private Date postDate;
    private Timestamp createDate;
    private String createUser;  // 追加
    private Timestamp updateDate; // 追加
    private String updateUser;  // 追加

    public Post() {}

    // --- 既存のゲッターとセッター ---
    public int getPostNumber() { return postNumber; }
    public void setPostNumber(int postNumber) { this.postNumber = postNumber; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImpression() { return impression; }
    public void setImpression(String impression) { this.impression = impression; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getImagepass() { return imagepass; }
    public void setImagepass(String imagepass) { this.imagepass = imagepass; }

    public Date getPostDate() { return postDate; }
    public void setPostDate(Date postDate) { this.postDate = postDate; }

    public Timestamp getCreateDate() { return createDate; }
    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }

    // --- 追加分のゲッターとセッター ---
    public String getCreateUser() { return createUser; }
    public void setCreateUser(String createUser) { this.createUser = createUser; }

    public Timestamp getUpdateDate() { return updateDate; }
    public void setUpdateDate(Timestamp updateDate) { this.updateDate = updateDate; }

    public String getUpdateUser() { return updateUser; }
    public void setUpdateUser(String updateUser) { this.updateUser = updateUser; }
}