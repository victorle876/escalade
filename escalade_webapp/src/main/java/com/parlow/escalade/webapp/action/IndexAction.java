package com.parlow.escalade.webapp.action;

import com.opensymphony.xwork2.ActionSupport;
import com.parlow.escalade.business.manager.contract.ManagerFactory;
import com.parlow.escalade.model.bean.Commentaire;
import com.parlow.escalade.model.bean.Site;
import com.parlow.escalade.model.bean.Topo;
import com.parlow.escalade.model.bean.utilisateur.Utilisateur;
import com.parlow.escalade.model.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IndexAction extends ActionSupport implements ServletRequestAware, SessionAware {

    // ==================== Attributs ====================

    private static final Logger logger = LogManager.getLogger(IndexAction.class);
    @Inject
    private ManagerFactory managerFactory;
    private Map<String, Object> session;
    private HttpServletRequest servletRequest;
    private List<Site> listSites;
    protected Utilisateur utilisateur;
    private List<Commentaire> listCommentaires;
    private List<Topo> listTopos;

    // ==================== Getters/Setters ====================

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    public void setUtilisateur(Utilisateur vutilisateur) {
        this.utilisateur = vutilisateur;
    }
    public List<Site> getListSites() {
        if(this.listSites==null){
            this.listSites=selectSites();
        }
        return listSites;
    }
    public void setListSites(List<Site> listSites) {
        this.listSites = listSites;
    }
    public List<Commentaire> getListCommentaires() {
        if(this.listCommentaires==null){
            this.listCommentaires=selectCommentaires();
        }
        return listCommentaires;
    }
    public List<Topo> getListTopos() {
        if(this.listTopos==null){
            this.listTopos=selectTopos();
        }
        return listTopos;
    }
    
    // ==================== Méthodes ====================
    /**
     * Action permettant la connexion d'un utilisateur
     * @return input / success
     */
    public String doIndex() {
        if (rememberMeLoad() >0){
            try {
                this.utilisateur = managerFactory.getUtilisateurManager().findById(rememberMeLoad());
            } catch (NotFoundException pEx) {
                this.addActionError(getText("error.login.incorrect"));
            }
            this.session.put("escalade_user", this.utilisateur);
        }
        String vResult = ActionSupport.SUCCESS;
        return vResult;
    }

    public int rememberMeLoad() {
        int vUtilisateurId = 0;
        Cookie[] cookies = servletRequest.getCookies();
        for(int i=0;cookies!=null&&i<cookies.length;i++) {
            if (cookies[i].getName().equals("escalade_user")) {
                vUtilisateurId = Integer.parseInt(cookies[i].getValue());
            }
        }
        return vUtilisateurId;
    }

    private List<Commentaire> selectCommentaires(){
        return  managerFactory.getCommentaireManager().findAll();
    }

    private List<Site> selectSites(){
        return managerFactory.getSiteManager().findAllPublic();
    }

    private List<Topo> selectTopos(){
        return  managerFactory.getTopoManager().findAllPublic();
    }


    @Override
    public void setSession(Map<String, Object> pSession) {
        this.session = pSession;
    }

    @Override
    public void setServletRequest(HttpServletRequest pRequest) {
        this.servletRequest = pRequest;
    }
}
