package project.WaltDisneyManagement.entity.Messages;

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
public class MagicKingdomMessage {

    @SerializedName("Walt Disney World RailRoad")
    private Map<String, Object> railRoad;

    @SerializedName("Pirates of the Caribbean")
    private Map<String, Object> piratesOfTheCaribbean;

    @SerializedName("Haunted Mansion")
    private Map<String, Object> hauntedMansion;

    @SerializedName("Seven Dwarfs Mine Train")
    private Map<String, Object> sevenDwarfsMineTrain;

    @SerializedName("Tomorrowland Speedway")
    private Map<String, Object> tomorrowlandSpeedway;

    public String toString() {
        return "MagicKingdomMessage{" +
                "railRoad=" + railRoad +
                ", piratesOfTheCaribbean=" + piratesOfTheCaribbean +
                ", hauntedMansion=" + hauntedMansion +
                ", sevenDwarfsMineTrain=" + sevenDwarfsMineTrain +
                ", tomorrowlandSpeedway=" + tomorrowlandSpeedway +
                '}';
    }

}