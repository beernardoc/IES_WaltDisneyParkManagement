package project.WaltDisneyManagement.Dto;


import lombok.Getter;
import lombok.Setter;
import project.WaltDisneyManagement.entity.Attraction;

import java.util.List;


public record ParkDto(String name, List<Attraction> attractions) {



}
