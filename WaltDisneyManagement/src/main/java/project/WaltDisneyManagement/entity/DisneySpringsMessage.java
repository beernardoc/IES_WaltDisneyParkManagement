package project.WaltDisneyManagement.entity;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisneySpringsMessage {

    @SerializedName("Marketplace Train Express")
    private Map<String, Object> marketplace;

    @SerializedName("Classic Carousel")
    private Map<String, Object> classic;


    public String toString() {
        return "DisneySpringsMessage{" +
                "marketplaceTrain=" + marketplace +
                ", classicCarousel=" + classic +
                '}';
    }

}