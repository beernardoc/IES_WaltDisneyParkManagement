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
public class TyphoonLagoonMessage {

    @SerializedName("Gankplank Falls")
    private Map<String, Object> gankplank;

    @SerializedName("Humunga Kowabunga")
    private Map<String, Object> humunga;


    public String toString() {
        return "TyphoonLagoonMessage{" +
                "gankplankFalls=" + gankplank +
                ", humungaKowabunga=" + humunga +
                '}';
    }

}