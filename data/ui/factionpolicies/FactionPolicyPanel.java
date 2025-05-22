package data.ui.factionpolicies;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.PositionAPI;
import data.scripts.managers.AoTDFactionManager;
import data.ui.SoundUIManager;
import data.ui.basecomps.*;

import java.util.List;

public class FactionPolicyPanel implements ExtendUIPanelPlugin , SoundUIManager {
    CustomPanelAPI mainPanel;
    public boolean sendSignalToRecreateBothComponents = false;
    public FactionCurrentPoliciesListPanel currentPoliciesListPanel;
    public FactionAvailablePoliciesListPanel availablePoliciesListPanel;
    public FactionBonusPanel bonusPanel;
    public FactionPolicyPanel(float width, float height){
        mainPanel = Global.getSettings().createCustom(width,height,this);
        createUI();
    }
    public void createUI(){
        float width = mainPanel.getPosition().getWidth();
        float height = mainPanel.getPosition().getHeight();

        float widthT = width*0.5f - 10f;
        float heightT = height-160;
        AoTDFactionManager.getInstance().updateListBeforeUI();
        currentPoliciesListPanel =new FactionCurrentPoliciesListPanel(widthT,heightT);
        availablePoliciesListPanel = new FactionAvailablePoliciesListPanel(widthT,heightT);
        bonusPanel= new FactionBonusPanel(450, 130);
        mainPanel.addComponent(currentPoliciesListPanel.getMainPanel()).inTL(0,0);
        mainPanel.addComponent(availablePoliciesListPanel.getMainPanel()).inTL(widthT+5f,0);
        mainPanel.addComponent(new FactionFlagButtonComponent(130, 130).getPanelOfButton()).inTL(((width - 10) / 2) - 65, height - 130);
        mainPanel.addComponent(new FactionXPPanel(450, 130).getMainPanel()).inTL(((width - 10) / 2) - 65 - 455, height - 130);
        mainPanel.addComponent(bonusPanel.getMainPanel()).inTL(((width - 10) / 2) + 70, height - 130);


    }
    @Override
    public CustomPanelAPI getMainPanel() {
        return mainPanel;
    }

    @Override
    public void positionChanged(PositionAPI position) {

    }

    @Override
    public void renderBelow(float alphaMult) {

    }

    @Override
    public void render(float alphaMult) {

    }

    @Override
    public void advance(float amount) {
        if(currentPoliciesListPanel.changeRequired || availablePoliciesListPanel.changeRequired){
            currentPoliciesListPanel.setChangeRequired(false);
            availablePoliciesListPanel.setChangeRequired(false);
            currentPoliciesListPanel.createUI();
            availablePoliciesListPanel.createUI();
            bonusPanel.createUI();
        }
    }

    @Override
    public void processInput(List<InputEventAPI> events) {

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }

    @Override
    public void playSound() {

    }

    @Override
    public void pauseSound() {

    }
}
