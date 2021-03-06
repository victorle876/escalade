package com.parlow.escalade.business.manager.contract;

import com.parlow.escalade.model.bean.Longueur;
import com.parlow.escalade.model.bean.Voie;
import com.parlow.escalade.model.exception.FunctionalException;
import com.parlow.escalade.model.exception.NotFoundException;
import com.parlow.escalade.model.exception.TechnicalException;

import java.util.List;

public interface LongueurManager {

    /**
     * Renvoie la le {@link Longueur} demandé
     * @param pId du {@link Longueur}
     * @return {@link Longueur}
     * @throws NotFoundException si non trouvé
     */
    Longueur findById(int pId )throws NotFoundException;

    /**
     * Renvoie la liste des {@link Longueur}
     *
     * @return List
     */
    List<Longueur> findAll();

    /**
     * Insert l'{@link Longueur} dans la bdd
     * @param pLongueur le {@link Longueur}
     * @return Le {@link Longueur}
     * @throws FunctionalException si le longueur est null
     */
    int insert( Longueur pLongueur )throws FunctionalException, TechnicalException;

    /**
     * Supprime un {@link Longueur}
     * @param pId du {@link Longueur}
     * @throws NotFoundException si non trouvé
     * @return boolean
     */
    void delete( int pId )throws NotFoundException;

    /**
     * Met à jour l'{@link Longueur} dans la bdd
     * @param pLongueur le {@link Longueur}
     * @return boolean
     * @throws FunctionalException si le longueur est null
     */
    void update ( Longueur pLongueur ) throws FunctionalException;

    /**
     * Renvoie la liste des {@link Longueur} liés à un site
     * @param voieId du {@link Voie}
     * @return List
     */
    List<Longueur> findAllByVoieId(int voieId) throws NotFoundException;

    /**
     * Renvoie la liste des résultats de la recherche
     * @param keyWord
     * @return List
     */
    List<Longueur> searchResult(String keyWord);

    /**
     * Renvoie le {@link Longueur} demandé
     * @param pNom du {@link Longueur}
     * @return {@link Longueur}
     * @throws NotFoundException si non trouvé
     */
    Longueur findByName( String pNom )throws NotFoundException;
}
