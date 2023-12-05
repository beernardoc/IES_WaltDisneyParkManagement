package project.WaltDisneyManagement.entity.Messages;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.entity.Park;
import project.WaltDisneyManagement.entity.Rollercoaster;
import project.WaltDisneyManagement.repository.ParkRepo;

import java.lang.reflect.Field;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MagicKingdomMessage {

    @SerializedName("Walt Disney World RailRoad")
    private Rollercoaster WaltDisneyWorldRailRoad;

    @SerializedName("Pirates of the Caribbean")
    private Map<String, Object> PiratesOfTheCaribbean;

    @SerializedName("Haunted Mansion")
    private Map<String, Object> HauntedMansion;

    @SerializedName("Seven Dwarfs Mine Train")
    private Rollercoaster SevenDwarfsMineTrain;

    @SerializedName("Tomorrowland Speedway")
    private Rollercoaster TomorrowlandSpeedway;

    public String checkLimits(){
        return WaltDisneyWorldRailRoad.checkParameterLimits();// depois serao todas as atrações
    }


    public String toString() {
        return "MagicKingdomMessage {" +
                "WaltDisneyWorldRailRoad=" + WaltDisneyWorldRailRoad +
                ", PiratesOfTheCaribbean=" + PiratesOfTheCaribbean +
                ", HauntedMansion=" + HauntedMansion +
                ", SevenDwarfsMineTrain=" + SevenDwarfsMineTrain +
                ", TomorrowlandSpeedway=" + TomorrowlandSpeedway +
                '}';
    }
}