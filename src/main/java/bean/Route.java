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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getStartName() {
		return startName;
	}
	public void setStartName(String startName) {
		this.startName = startName;
	}
	public String getEndName() {
		return endName;
	}
	public void setEndName(String endName) {
		this.endName = endName;
	}
	public String getPolyline() {
		return polyline;
	}
	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}
	public boolean isFavorite() {
		return isFavorite;
	}
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	public Timestamp getSearchedAt() {
		return searchedAt;
	}
	public void setSearchedAt(Timestamp searchedAt) {
		this.searchedAt = searchedAt;
	}

}