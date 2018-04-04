package com.parlow.escalade.webapp.action;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.sql.Timestamp;

import com.opensymphony.xwork2.ActionSupport;
import com.parlow.escalade.business.manager.contract.ManagerFactory;
import com.parlow.escalade.model.bean.Secteur;
import com.parlow.escalade.model.bean.Voie;
import com.parlow.escalade.model.bean.utilisateur.Utilisateur;
import com.parlow.escalade.model.exception.FunctionalException;
import com.parlow.escalade.model.exception.NotFoundException;
import com.parlow.escalade.model.exception.TechnicalException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts2.interceptor.SessionAware;


import javax.inject.Inject;


/**
 * Action de gestion des {@link Secteur}
 */
public class GestionSecteurAction extends ActionSupport implements  SessionAware {




    // ==================== Attributs ====================
    @Inject
    private ManagerFactory managerFactory;
    private Map<String, Object> session;


    private static final Logger logger = LogManager.getLogger(GestionSecteurAction.class);


    // ----- Paramètres en entrée
    private Integer id;
    private Date createdDate;
    private Date lastUpdate;
    private File imageTemp;
    private String imageTempContentType;
    private String imageTempFileName;

    // ----- Eléments en sortie
    private List<Secteur> listSecteur;
    private Secteur secteur;
    private List<Voie> listVoie;
    private Voie voie;

    // ==================== Getters/Setters ====================
    public Integer getId() {
        return id;
    }
    public void setId(Integer pId) {
        id = pId;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public Date getLastUpdate() {
        return lastUpdate;
    }
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Secteur> getListSecteur() {
        return listSecteur;
    }

    public void setListSecteur(List<Secteur> listSecteur) {
        this.listSecteur = listSecteur;
    }
    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public File getImageTemp() {
        return imageTemp;
    }

    public void setImageTemp(File imageTemp) {
        this.imageTemp = imageTemp;
    }

    public String getImageTempContentType() {
        return imageTempContentType;
    }

    public void setImageTempContentType(String imageTempContentType) {
        this.imageTempContentType = imageTempContentType;
    }

    public String getImageTempFileName() {
        return imageTempFileName;
    }

    public void setImageTempFileName(String imageTempFileName) {
        this.imageTempFileName = imageTempFileName;
    }

    public List<Voie> getListVoie() {
        return listVoie;
    }

    public void setListVoie(List<Voie> listVoie) {
        this.listVoie = listVoie;
    }

    public Voie getVoie() {
        return voie;
    }

    public void setVoie(Voie voie) {
        this.voie = voie;
    }

    // ==================== Méthodes ====================
    /**
     * Action listant les {@link Secteur}
     * @return success
     */
    public String doList() {
        listSecteur = managerFactory.getSecteurManager().findAll();
        return ActionSupport.SUCCESS;
    }


    /**
     * Action affichant les détails d'un {@link Secteur}
     * @return success / error
     */
    public String doDetail() {
        if (id == null) {
            this.addActionError(getText("error.secteur.missing.id"));
        } else {
            try {
                logger.error("id du secteur" + id);

                secteur = managerFactory.getSecteurManager().findById(id);
            } catch (NotFoundException pE) {
                this.addActionError(getText("error.secteur.notfound", Collections.singletonList(id)));
            }
            this.createdDate = secteur.getDateCreation();
            this.lastUpdate = secteur.getLastUpdate();

            try {
                this.listVoie = managerFactory.getVoieManager().findAllBySecteurId(id);
            } catch (NotFoundException pE) {
                // this.addActionError(getText("error.secteur.notfound", Collections.singletonList(id)));
            }
            secteur.setVoies(this.listVoie);
            secteur.setNbVoies(this.listVoie.size());
        }

        return ActionSupport.SUCCESS;
    }
    /**
     * Action permettant de créer un nouveau {@link Secteur}
     * @return input / success / error
     */
    public String doCreate() {
        logger.error("I m here");

        String vResult = ActionSupport.INPUT;

        // ===== Validation de l'ajout de secteur (secteur != null)
        if (this.secteur != null) {
            Date date = new Date();
            this.secteur.setUtilisateur((Utilisateur)this.session.get("escalade_user"));
            this.secteur.setDateCreation(new Timestamp(date.getTime()));
            try {
                if(this.secteur.getImage()==null){
                    String image = "../../ressources/images/750x300.png";
                    this.secteur.setImage(image);
                }
                this.secteur.setId(managerFactory.getSecteurManager().insert(this.secteur));
                vResult = ActionSupport.SUCCESS;
                this.addActionMessage("Secteur ajouté avec succès");

            } catch (FunctionalException pEx) {
                this.addActionError(pEx.getMessage());
                vResult = ActionSupport.ERROR;

            } catch (TechnicalException pEx) {

                this.addActionError(pEx.getMessage());
                vResult = ActionSupport.ERROR;
            }
        }

        //Ajout des infos nécessaires pour le formulaire de saisie
        if (vResult.equals(ActionSupport.INPUT)) {

        }
        return vResult;
    }


    /**
     * Action permetttant la modification d'un {@link Secteur}
     * @return success / error
     */
    public String doModifier() throws IOException {

        String vResult = ActionSupport.INPUT;

        //vérification si affiche les données ou les update
        if (this.secteur != null) {
            Date date = new Date();
            this.secteur.setLastUpdate(new Timestamp(date.getTime()));
            //Gestion image
            logger.error("image fileName + contentType "+getImageTempFileName() + getImageTempContentType());
            //copy the uploaded file to the dedicated location
            try{
                String filePath = "D:\\IdeaWorkspace\\projectsRep\\escalade\\escalade_webapp\\src\\main\\webapp\\ressources\\images";
                File file2 = new File(filePath, getImageTempFileName());
                FileUtils.copyFile(imageTemp, file2);

            }catch (Exception e)
            {logger.error("problème lors du upload de l'image " +e);}


            if(imageTemp!=null){
                this.secteur.setImage("../../ressources/images/"+ getImageTempFileName());
            }
            logger.error("id du secteur" + secteur.getId());
            try {
                managerFactory.getSecteurManager().update(secteur);
                vResult = ActionSupport.SUCCESS;
            } catch (FunctionalException e) {
                this.addActionError(getText("Un problème est survenu avec la base de données, réessayez plus tard"));
                vResult = ActionSupport.ERROR;
            }
        }
        else {

            try {
                this.secteur = managerFactory.getSecteurManager().findById(id);
            } catch (NotFoundException pE) {
                this.addActionError(getText("error.user.notfound", Collections.singletonList(id)));
            }
        }


        return vResult;
    }

    @Override
    public void validate() {
        if (this.secteur != null) {
            if (secteur.getNom().length() < 3) {

                addFieldError("secteurNom", "Le nom du secteur doit faire au moins 3 lettres");
            }
        }
    }

    @Override
    public void setSession(Map<String, Object> pSession) {
        this.session = pSession;
    }


}
