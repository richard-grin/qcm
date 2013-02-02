package fr.rgrin.projetqcm.entite;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Le résultat d'un questionnaire.
 * @author richard
 */
@Entity
public class Resultat1Questionnaire {
  @Id
  @GeneratedValue
  private long id;
  @Temporal(TemporalType.DATE)
  private Date date;
  private Questionnaire questionnaire;
  
}
