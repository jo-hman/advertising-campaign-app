package com.jochman.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Campaign {
    @Id
    @GeneratedValue
    private Long campaignId;
    private String name;
    @OneToMany(cascade = CascadeType.MERGE)
    private List<Keyword> keywords;

    private Double bidAmount;
    private Double campaignFund;

    private Boolean isOn;

    private String town;
    private Double radius;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "productId"
    )
    @JsonBackReference
    private Product product;
}
