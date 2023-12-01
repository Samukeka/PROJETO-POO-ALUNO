package br.senac.sp.epictask.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.File;
import java.time.LocalDate;

@Entity
@Data
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String name;

    @Size(min = 3, message = "Digite uma descrição com pelo menos 3 caracteres")
    String description;
    
    @Min(value = 1, message = "Preço não podem ser negativo")
    Integer price;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    LocalDate dateStart;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    LocalDate dateEnd;


    
}

