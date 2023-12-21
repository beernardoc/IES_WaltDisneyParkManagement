package project.WaltDisneyManagement.Dto;

import java.time.LocalDate;

public record MaintenanceDto(String park, String attraction, String Descripton, String technician , LocalDate date) {
}
