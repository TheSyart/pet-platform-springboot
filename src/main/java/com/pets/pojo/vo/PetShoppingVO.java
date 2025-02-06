package com.pets.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetShoppingVO implements Serializable {

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

    private String category;

    private String image;

}

