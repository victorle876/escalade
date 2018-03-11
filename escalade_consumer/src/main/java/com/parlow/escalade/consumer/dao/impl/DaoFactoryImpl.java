package com.parlow.escalade.consumer.dao.impl;

import com.parlow.escalade.consumer.dao.contract.DaoFactory;
import com.parlow.escalade.consumer.dao.contract.SecteurDao;
import com.parlow.escalade.consumer.dao.contract.SiteDao;
import com.parlow.escalade.consumer.dao.contract.UtilisateurDao;

import javax.inject.Named;
import javax.inject.Inject;


@Named
public class DaoFactoryImpl implements DaoFactory {

    @Inject
    private SecteurDao secteurDao;
    @Inject
    private SiteDao siteDao;
    @Inject
    private UtilisateurDao utilisateurDao;

    @Inject
    public  DaoFactoryImpl() {
    }

    @Override
    public SecteurDao getSecteurDao() {
        return null;
    }

    @Override
    public void setSecteurDao(SecteurDao pSecteurDao) {

    }

    @Override
    public SiteDao getSiteDao() {
        return null;
    }

    @Override
    public void setSiteDao(SiteDao pSiteDao) {

    }

    @Override
    public UtilisateurDao getUtilisateurDao() {
        return utilisateurDao;
    }

    @Override
    public void setUtilisateurDao(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }
}
