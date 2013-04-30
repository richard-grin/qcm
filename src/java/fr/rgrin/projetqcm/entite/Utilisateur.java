package fr.rgrin.projetqcm.entite;

import fr.rgrin.login.entite.Login;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Un utilisateur de l'application.
 * @author richard
 */
@Entity
public class Utilisateur implements Serializable {
  @Id
  @GeneratedValue
  private Long id;
  @Column(length=60)
  private String nom;
  @Column(length=80)
  private String prenoms;
  @OneToMany
  private Set<Login> logins = new HashSet<>();

  public Utilisateur() {
  }

  public Utilisateur(String nom, String prenoms) {
    this.nom = nom;
    this.prenoms = prenoms;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenoms() {
    return prenoms;
  }

  public void setPrenoms(String prenoms) {
    this.prenoms = prenoms;
  }
  /**
   * Ajoute un login pour cet utilisateur.
   * @param login
   * @return 
   */
  public boolean addLogin(Login login) {
    return logins.add(login);
  }
  
  public boolean removeLogin(Login login) {
    return logins.remove(login);
  }
  
    @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Utilisateur)) {
      return false;
    }
    Utilisateur other = (Utilisateur) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "fr.rgrin.projetqcm.entite.Utilisateur[ id=" + id + " ]";
  }
}
