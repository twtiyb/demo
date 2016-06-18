package tools.openApiTest.entity;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Date;

/**
 * Created by xuchun on 15/9/10.
 */
@JsonRootName("packageDetail")
public class PackageDetail {
    Date shipDate;
    String weight;

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
