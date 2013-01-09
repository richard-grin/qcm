package fr.rgrin.projetqcm.jsf;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author richard
 */
public class LifeCycleListener implements PhaseListener {

  @Override
  public void afterPhase(PhaseEvent event) {
    System.out.println("FIN PHASE " + event.getPhaseId() + "; source = " + event.getSource());
    System.out.println("Type source =" + event.getSource().getClass().getName());

  }

  @Override
  public void beforePhase(PhaseEvent event) {
    System.out.println("DEBUT PHASE " + event.getPhaseId() + "; affiché par thread " + Thread.currentThread());
    System.out.println("Source de la phase :" + event.getSource());
  }

  @Override
  public PhaseId getPhaseId() {
    return PhaseId.ANY_PHASE;
  }
  
}
