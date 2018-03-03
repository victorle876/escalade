package com.parlow.escalade.model.bean.listes;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Objet métier représentant une cotation
 *
 * @author Mickaël Parlow
 */
public class Cotation {

    // ==================== Attributs ====================
    private Integer id;
    private String description;
    private Integer niveau;
    private static final Logger logger = LogManager.getLogger(Cotation.class);



    // ==================== Getters/Setters ==============
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCotation() {
        return description;
    }

    public void setCotation(String cotation) {
        this.description = cotation;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    // ==================== Méthodes =====================
    public void maMethod() {
        logger.info("Hello, World!");
    }
}
