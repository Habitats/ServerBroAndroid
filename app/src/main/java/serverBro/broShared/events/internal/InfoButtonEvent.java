package serverBro.broShared.events.internal;

import serverBro.broShared.Controller;
import serverBro.broShared.events.external.diagnostic.ComputerInfoRequestEvent;


public class InfoButtonEvent extends ViewEvent {


  @Override
  public void execute(Controller controller) {
    controller.sendEvent(new ComputerInfoRequestEvent());
  }
}
