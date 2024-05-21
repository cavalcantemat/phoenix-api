package com.ecom.phoenix.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products", schema = "public")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String team;
    private String description;

    public Product() {}

    public Product(long id, String team, String description) {
        this.id = id;
        this.team = team;
        this.description = description;
    }

}
