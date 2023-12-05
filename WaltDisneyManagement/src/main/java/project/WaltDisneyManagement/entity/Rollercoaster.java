package project.WaltDisneyManagement.entity;


import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rollercoaster extends Attraction {

    private Double maxSpeed = 20.0;
    private Double maxHeight = 150.0;
    private Double maxTemperature = 100.0;
    private Double maxVibration = 100.0;



    @SerializedName("velocity_kmh")
    private Double velocityKmh;

    @SerializedName("height_m")
    private Double height;

    @SerializedName("temperature")
    private Double temperature;

    @SerializedName("vibration")
    private Double vibration;

    @SerializedName("people_queue")
    private Integer peopleQueue;

    @SerializedName("duration")
    private Integer duration;


    public String checkParameterLimits() {
        StringBuilder exceededParameters = new StringBuilder();

        // Verificar a velocidade máxima
        if (velocityKmh != null && velocityKmh > maxSpeed) {
            exceededParameters.append("Velocidade máxima foi ultrapassada. ");
        }

        // Verificar a altura máxima
        if (height != null && height > maxHeight) {
            exceededParameters.append("Altura máxima foi ultrapassada. ");
        }

        // Verificar a temperatura máxima
        if (temperature != null && temperature > maxTemperature) {
            exceededParameters.append("Temperatura máxima foi ultrapassada. ");
        }

        // Verificar a vibração máxima
        if (vibration != null && vibration > maxVibration) {
            exceededParameters.append("Vibração máxima foi ultrapassada. ");
        }



        // Retorna a mensagem de parâmetros ultrapassados (ou uma mensagem indicando que nenhum parâmetro foi ultrapassado)
        return !exceededParameters.isEmpty() ? exceededParameters.toString() : "Nenhum parâmetro ultrapassado";
    }



    public String toString() {
        return "{" +
                "velocityKmh=" + velocityKmh +
                ", height=" + height +
                ", temperature=" + temperature +
                ", vibration=" + vibration +
                ", peopleQueue=" + peopleQueue +
                ", duration=" + duration +
                '}';
    }
}


