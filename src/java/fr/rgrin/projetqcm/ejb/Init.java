package fr.rgrin.projetqcm.ejb;

import fr.rgrin.login.entite.Groupe;
import fr.rgrin.login.entite.Login;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author richard
 */
@Singleton
@Startup
public class Init {

  @PersistenceContext(unitName = "loginPU")
  private EntityManager em;

  /**
   * Ajoute des logins s'il n'y en a pas déjà.
   */
  @PostConstruct
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  public void init() {
    System.out.println("************************INIT !!!!!!!!!!!!!");
    // Création de la vue qui sert pour le domaine de sécurité
    // pour la table des groupes.

    String ordreCreaVue =
            "create view V_LOGIN_GROUPE(LOGIN, NOM_GROUPE) as "
            + " select LOGIN, GROUPE.NOM "
            + " from GROUPE join LOGIN_GROUPE "
            + " on GROUPE.ID = LOGIN_GROUPE.ID_GROUPE ";
    try {
      Query q = em.createNativeQuery(ordreCreaVue);
      q.executeUpdate();
      System.out.println("********* Vue créée");
    } catch (Throwable throwable) {
      System.out.println("Vue pas créée :" + throwable);
    }
    // Insertion des comptes utilisateur
    // Regarde si des logins existent déjà
    CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
    cq.select(cq.from(Login.class));
    List<Login> l = em.createQuery(cq).getResultList();
    if (l.isEmpty()) {
      Groupe etudiants = new Groupe("etudiant");
      Groupe enseignants = new Groupe("enseignant");
      // Ajoute des logins (toto pour tous les mots de passe, en SHA256 et Hex)
      Login grin = new Login("grin", "31f7a65e315586ac198bd798b6629ce4903d0899476d5741a9f32e2e521b6a66");
      Login pierre = new Login("pierre", "31f7a65e315586ac198bd798b6629ce4903d0899476d5741a9f32e2e521b6a66");
      Login jacques = new Login("jacques", "31f7a65e315586ac198bd798b6629ce4903d0899476d5741a9f32e2e521b6a66");
      grin.addGroupe(enseignants);
      pierre.addGroupe(etudiants);
      jacques.addGroupe(etudiants);
      em.persist(etudiants);
      em.persist(enseignants);
      em.persist(grin);
      em.persist(pierre);
      em.persist(jacques);
      System.out.println("*********Utilisateurs créés");
    }
  }
}
