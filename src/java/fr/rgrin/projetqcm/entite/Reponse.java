package fr.rgrin.projetqcm.entite;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Une réponse possible pour une question.
 * Inclut aussi la réponse d'un utilisateur pour faciliter la gestion
 * des réponses des utilisateurs.
 */
@Entity
public class Reponse implements Serializable {
  @Id
  @GeneratedValue
  private Long id;
  
  /**
   * Intitulé de cette réponse.
   */
  private String intitule;
  /**
   * Vrai si et seulement si la réponse doit être "cochée" pour donner
   * une bonne réponse à la question.
   * Un bug de Java DB (ou du driver pour Java DB) 
   * fait que ça ne marche pas avec boolean.
   * Ca marche maintenant dans la version actuelle.
   */
//  private boolean ok;
  private char ok;
  /**
   * Réponse d'un utilisateur.
   * Vrai si la réponse a été cochée par l'utilisateur.
   * Pas enregistré dans la base de données sous cette forme
   * (voir 
   */
  //@Column(name="REPONSE_UTILISATEUR")
  @Transient
  private char reponseUtilisateur;
  
  public Reponse() {
  }

  public Reponse(String intitule, boolean ok) {
    super();
//    this.question = question;
    this.intitule = intitule;
    setOk(ok);
  }
  
  public Long getId() {
    return id;
  }
  
  public String getIntitule() {
    return intitule;
  }

  public void setIntitule(String intitule) {
    this.intitule = intitule;
  }

  public void setOk(boolean ok) {
    this.ok = (ok == true) ? 'o' : 'n';
  }
  
  public boolean isOk() {
    return ok == 'o';
  }

  public boolean getReponseUtilisateur() {
    return reponseUtilisateur == 'o';
  }

  public void setReponseUtilisateur(boolean reponseUtilisateur) {
    System.out.println("+++Reponse id=" + id + " - " + super.toString() + " - setReponseUtilisateur(" + reponseUtilisateur + ")");
    this.reponseUtilisateur = (reponseUtilisateur == true) ? 'o' : 'n';
    System.out.println("+++reponseUtilisateur=" + reponseUtilisateur);
  }
  
  @Override
  public String toString() {
    return "Reponse " + " - " + super.toString() + " - [id=" + id + ", intitule=||" + intitule + "||, ok=" + ok + ", reponseUtilisateur=" + reponseUtilisateur + "]";
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 47 * hash + Objects.hashCode(this.id);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Reponse other = (Reponse) obj;
    if (this.id != other.id) {
      return false;
    }
    // this.id == other.id
    if (this.id == null) {
      // Les 2 id sont null ; ils sont les mêmes s'ils ont le même intitulé
      return this.intitule.equals(other.intitule);
    }
    // Les 2 ids sont les mêmes et ne sont pas null
    return true;
  }
}
