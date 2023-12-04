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
public class HollywoodStudios {

    @SerializedName("Runaway Railway")
    private Map<String, Object> runaway;

    @SerializedName("Rock n Roller Coaster")
    private Map<String, Object> rocknroller;



    public String toString() {
        return "HollywoodStudiosMessage{" +
                "runawayRail=" + runaway +
                ", rocknroller=" + rocknroller +
                '}';
    }

}