package project.WaltDisneyManagement.Dto;

import java.time.LocalDate;

public record MaintenanceHistoryDto(String park, String attraction, String Descripton,String technician ,LocalDate date) {
}
