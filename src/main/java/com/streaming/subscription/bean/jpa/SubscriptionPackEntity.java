package com.streaming.subscription.bean.jpa;

import javax.persistence.*;

@Entity
@Table(name = "subscription_pack")
public class SubscriptionPackEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "sku")
    private String sku;

    @Column(name = "provider")
    private String provider;

    @Column(name = "country")
    private String country;

    @Column(name = "name")
    private String name;

    @Column(name = "local_language_name")
    private String localLanguageName;

    @Column(name = "price")
    private String price;

    @Column(name = "language")
    private String language;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "days")
    private Integer days;

    @Column(name = "currency")
    private String currency;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "catalogue_name")
    private String catalogueName;

    @Column(name = "operator_code")
    private String operatorCode;

    @Column(name = "status")
    private Boolean active;

    @Column(name = "isFallbackPack")
    private Boolean isFallbackPack;

    @Column(name = "charge_amount")
    private String chargeAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalLanguageName() {
        return localLanguageName;
    }

    public void setLocalLanguageName(String localLanguageName) {
        this.localLanguageName = localLanguageName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCatalogueName() {
        return catalogueName;
    }

    public void setCatalogueName(String catalogueName) {
        this.catalogueName = catalogueName;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Boolean getFallbackPack() {
        return isFallbackPack;
    }

    public void setFallbackPack(Boolean fallbackPack) {
        isFallbackPack = fallbackPack;
    }

    public String getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(String chargeAmount) {
        this.chargeAmount = chargeAmount;
    }
}
