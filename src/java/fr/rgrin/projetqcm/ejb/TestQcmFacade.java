/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rgrin.projetqcm.ejb;

import fr.rgrin.projetqcm.entite.TestQcm;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author richard
 */
@Stateless
public class TestQcmFacade extends AbstractFacade<TestQcm> {
  @PersistenceContext(unitName = "qcmPU")
  private EntityManager em;

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public TestQcmFacade() {
    super(TestQcm.class);
  }
  
}
