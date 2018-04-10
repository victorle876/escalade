package com.parlow.escalade.webapp.action;

import com.opensymphony.xwork2.ActionSupport;
import com.parlow.escalade.business.manager.contract.ManagerFactory;
import com.parlow.escalade.model.bean.Commentaire;
import com.parlow.escalade.model.bean.utilisateur.Utilisateur;
import com.parlow.escalade.model.exception.FunctionalException;
import com.parlow.escalade.model.exception.TechnicalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class GestionCommentaireAction extends ActionSupport implements SessionAware {

    @Inject
    private ManagerFactory managerFactory;
    private Map<String, Object> session;

    private static final Logger logger = LogManager.getLogger(GestionCommentaireAction.class);

    // ----- Paramètres en entrée

    private Commentaire commentaire;
    private Integer sectionId;
    private Integer commentaireId;

    // ==================== Getters/Setters ====================


    public Commentaire getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getCommentaireId() {
        return commentaireId;
    }

    public void setCommentaireId(Integer commentaireId) {
        this.commentaireId = commentaireId;
    }

    // ==================== Méthodes ====================

    /**
     * Action permettant de créer un nouveau {@link Commentaire}
     * @return success
     */
    public String doCreate() {

        String vResult = ActionSupport.SUCCESS;

        // ===== Validation de l'ajout de site (site != null)
        if (this.commentaire != null) {
            Date date = new Date();
            this.commentaire.setUtilisateur((Utilisateur) this.session.get("escalade_user"));
            this.commentaire.setDateCreation(new Timestamp(date.getTime()));
            this.commentaire.setCommentaire(premiereLettreMaj(this.commentaire.getCommentaire()));

            try {
                managerFactory.getCommentaireManager().insert(this.commentaire);
                vResult = ActionSupport.SUCCESS;

            } catch (FunctionalException pEx) {
                this.addActionError(pEx.getMessage());
                vResult = ActionSupport.ERROR;

            }
        }

        return vResult;
    }

    /**
     * Action permettant de modifier un {@link Commentaire}
     * @return success
     */
    public String doModifier() {
    // todo modifier commentaire
        String vResult = ActionSupport.SUCCESS;

        // ===== Validation de l'ajout de site (site != null)
        if (this.commentaire != null) {
            Date date = new Date();
            this.commentaire.setUtilisateur((Utilisateur) this.session.get("escalade_user"));
            //this.commentaire.setDateCreation(new Timestamp(date.getTime()));
            this.commentaire.setCommentaire(premiereLettreMaj(this.commentaire.getCommentaire()));

            try {
                managerFactory.getCommentaireManager().insert(this.commentaire);
                vResult = ActionSupport.SUCCESS;

            } catch (FunctionalException pEx) {
                this.addActionError(pEx.getMessage());
                vResult = ActionSupport.ERROR;

            }
        }

        return vResult;
    }

    /**
     * Action permettant de supprimer {@link Commentaire}
     * @return input / success / error
     */
    public String doDelete() {
        //todo delete
        String vResult = ActionSupport.SUCCESS;
        return vResult;
    }

    //transforme la premiere lettre d'un string en majuscule
    private String premiereLettreMaj(String str){

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    @Override
    public void setSession(Map<String, Object> pSession) {
        this.session = pSession;
    }
}
