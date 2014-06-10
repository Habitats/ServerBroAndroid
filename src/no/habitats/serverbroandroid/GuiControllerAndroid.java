package no.habitats.serverbroandroid;

import serverBro.broShared.BroModel;
import serverBro.broShared.view.BroGuiController;

public class GuiControllerAndroid extends BroGuiController{
  private MainActivity mainActity;

  public GuiControllerAndroid(MainActivity mainActity) {
    this.mainActity = mainActity;
  }

  @Override
  public void setModel(BroModel model) {
    super.model = model;
    model.addObserver(mainActity);
  }
}
