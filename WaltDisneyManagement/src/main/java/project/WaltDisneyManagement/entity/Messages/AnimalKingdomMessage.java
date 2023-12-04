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
public class AnimalKingdomMessage {

    @SerializedName("Tricera Top Spin")
    private Map<String, Object> triceratop;

    @SerializedName("Kali River Rapids")
    private Map<String, Object> kaliriver;

    @SerializedName("Dinosaur")
    private Map<String, Object> dinosaur;



    public String toString() {
        return "AnimalKingdomMessage{" +
                "triceraTop=" + triceratop +
                ", kaliRiver=" + kaliriver +
                ", dinosaur=" + dinosaur +
                '}';
    }

}