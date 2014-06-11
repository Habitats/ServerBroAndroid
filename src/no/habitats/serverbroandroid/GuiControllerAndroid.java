package no.habitats.serverBroAndroid;

import java.util.Observer;

import serverBro.broShared.BroModel;
import serverBro.broShared.view.BroGuiController;

public class GuiControllerAndroid extends BroGuiController {

  @Override
  public void setModel(BroModel model) {
    super.model = model;
  }

  public void addObserver(Observer observer) {
    model.addObserver(observer);
  }
}
