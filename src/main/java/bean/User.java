package bean;
<<<<<<< HEAD
 
import java.io.Serializable;
 
=======

import java.io.Serializable;

>>>>>>> branch 'master' of https://github.com/SeigoTakano/Travel-Assist-local.git
public class User implements Serializable {
    private int id;
    private String email;
    private String password;
    private String username;
    private boolean managerFlag; // 1なら管理者
    private boolean adminFlag;   // managerFlagと両方1ならスーパー管理者
<<<<<<< HEAD
 
=======

>>>>>>> branch 'master' of https://github.com/SeigoTakano/Travel-Assist-local.git
    public User() {}
<<<<<<< HEAD
 
=======

>>>>>>> branch 'master' of https://github.com/SeigoTakano/Travel-Assist-local.git
    public User(int id, String email, String password, String username, boolean managerFlag, boolean adminFlag) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.managerFlag = managerFlag;
        this.adminFlag = adminFlag;
    }
<<<<<<< HEAD
 
=======

>>>>>>> branch 'master' of https://github.com/SeigoTakano/Travel-Assist-local.git
    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public boolean isManagerFlag() { return managerFlag; }
    public void setManagerFlag(boolean managerFlag) { this.managerFlag = managerFlag; }
    public boolean isAdminFlag() { return adminFlag; }
    public void setAdminFlag(boolean adminFlag) { this.adminFlag = adminFlag; }
}