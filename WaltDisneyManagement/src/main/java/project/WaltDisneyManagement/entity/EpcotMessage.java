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
public class EpcotMessage {

    @SerializedName("Spaceship Earth")
    private Map<String, Object> spaceship;

    @SerializedName("Guardians of the Galaxy: Cosmic Rewind")
    private Map<String, Object> comsicRewind;

    @SerializedName("Test Track")
    private Map<String, Object> testTrack;


    public String toString() {
        return "EpcotMessage{" +
                "spaceship=" + spaceship +
                ", cosmicRewind=" + comsicRewind +
                ", testTrack=" + testTrack +
                '}';
    }

}