package bean;
import java.io.Serializable;
import java.sql.Timestamp;

public class Route implements Serializable {
    private int id;
    private int userId;
    private String startName;
    private String endName;
    private String polyline;
    private boolean isFavorite;
    private Timestamp searchedAt;

}