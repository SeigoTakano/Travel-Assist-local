package bean;
 
import java.io.Serializable;
import java.sql.Timestamp;

public class Inquiry implements Serializable {

    private int inquiryNumber;
    private String email;      // JSP: ${inq.email}
    private String name;       // JSP: ${inq.name}
    private String message;    // JSP: ${inq.message} (inquiry_detailに対応)
    private String reply;      // inquiry_reply
    private Timestamp createdAt; // JSP: ${inq.createdAt} (create_dateに対応)
    private Timestamp updatedAt; // JSP: ${inq.updatedAt} (update_dateに対応)
    private String status;     // JSP: ${inq.status} (ロジック用)
    private String category;
 
    public Inquiry() {}
 
    public Inquiry(int inquiryNumber, String email, String name, String message, 

                   String reply, Timestamp createdAt, Timestamp updatedAt, String category) {

        this.inquiryNumber = inquiryNumber;
        this.email = email;
        this.name = name;
        this.message = message;
        this.reply = reply;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        // reply（返信内容）があるかどうかで初期ステータスを判定
        this.status = (reply == null || reply.isEmpty()) ? "未確認" : "対応済";
        
        this.category = category;

    }
 
    // Getter & Setter (JSPからアクセスするために必須)

    public int getInquiryNumber() { return inquiryNumber; }

    public String getEmail() { return email; }

    public String getName() { return name; }

    public String getMessage() { return message; }

    public String getReply() { return reply; }

    public Timestamp getCreatedAt() { return createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }

    public String getStatus() { return status; }
    
    public String getCategory() { return category; }
    
    // ===== Setter =====
    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setReply(String reply) {
        this.reply = reply;
        this.status = (reply == null || reply.isEmpty()) ? "未確認" : "対応済";
    }
    
    public void setCategory(String category) { 
    	this.category = category;
    }

}
 