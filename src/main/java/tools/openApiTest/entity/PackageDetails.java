package tools.openApiTest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by xuchun on 15/9/10.
 */
public class PackageDetails {
    @JsonProperty("packageDetail")
    PackageDetail packageDetail;

    public PackageDetail getPackageDetail() {
        return packageDetail;
    }

    public void setPackageDetail(PackageDetail packageDetail) {
        this.packageDetail = packageDetail;
    }
}
