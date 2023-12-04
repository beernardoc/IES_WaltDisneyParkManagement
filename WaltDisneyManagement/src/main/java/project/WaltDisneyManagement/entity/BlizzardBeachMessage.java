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
public class BlizzardBeachMessage {

    @SerializedName("Summit Plummet")
    private Map<String, Object> summit;

    @SerializedName("Slush Gusher")
    private Map<String, Object> slush;


    public String toString() {
        return "BlizzardBeachMessage{" +
                "summitPlummet=" + summit +
                ", slushGusher=" + slush +
                '}';
    }

}