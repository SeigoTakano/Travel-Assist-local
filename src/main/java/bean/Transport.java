package bean;

import java.io.Serializable;

public class Transport implements Serializable {
    private static final long serialVersionUID = 1L;

    // JSPの各項目に対応するフィールド
    private String transportType; // 新幹線, 飛行機, バス (data-type)
    private String departure;     // 出発地 (id="departure")
    private String destination;   // 目的地 (id="destination")
    private String travelDate;    // 出発日 (id="travelDate")
    private String condition;     // 条件設定 (id="condition")

    public Transport() {}

    public String getTransportType() { return transportType; }
    public void setTransportType(String transportType) { this.transportType = transportType; }

    public String getDeparture() { return departure; }
    public void setDeparture(String departure) { this.departure = departure; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getTravelDate() { return travelDate; }
    public void setTravelDate(String travelDate) { this.travelDate = travelDate; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
}