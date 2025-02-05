package com.example.jhscomputer.asrequest.dto;


import com.example.jhscomputer.asrequest.entity.ASRequest.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ASRequestDTO {

    @NotNull
    private DeviceType deviceType;

    @NotBlank
    private String issueDescription;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    private String purchaseSource;

    private MultipartFile quotationAttachment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate manufactureDate;

    private String modelName;
}