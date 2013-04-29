package fr.rgrin.projetqcm.entite;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;

/**
 * Un étudiant.
 * @author richard
 */
@Entity
public class Etudiant extends Utilisateur implements Serializable {
  private static final long serialVersionUID = 1L;
  
  /**
   * Les tests passés par l'étudiant
   */
  private Set<TestQcm> tests = new HashSet<>();
  
  public boolean addTest(TestQcm testQcm) {
    return tests.add(testQcm);
  }
  
}
