package com.pets.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetShoppingDTO implements Serializable {

    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String createdate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String updatedate;

    private Integer shoppingTypeId;

}

