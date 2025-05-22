package data.ui.overview.marketdata;

import ashlib.data.plugins.ui.models.resizable.ImageViewer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.*;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.ui.basecomps.ExtendUIPanelPlugin;

import java.awt.*;
import java.util.List;

public class FactionMarketData implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel;
    CustomPanelAPI contentPanel;
    public static float height = 45;
    public String commodityId;
    public FactionAPI faction;
    public ButtonAPI button;
    public FactionMarketData(float width, FactionAPI faction , String commodityId) {
        this.commodityId = commodityId;
        this.faction = faction;
        mainPanel = Global.getSettings().createCustom(width,height,this);
        createUI();
    }

    public ButtonAPI getButton() {
        return button;
    }

    public void createUI(){
        if(contentPanel!=null){
            mainPanel.removeComponent(contentPanel);
        }
        contentPanel = Global.getSettings().createCustom(mainPanel.getPosition().getWidth(),mainPanel.getPosition().getHeight(),null);
        float y = 2;
        float newHeight = height-8;
        TooltipMakerAPI tooltip = contentPanel.createUIElement(contentPanel.getPosition().getWidth(),contentPanel.getPosition().getHeight(),false);
        button = tooltip.addAreaCheckbox("",null, faction.getBaseUIColor(),faction.getDarkUIColor(),faction.getBrightUIColor(),contentPanel.getPosition().getWidth(),contentPanel.getPosition().getHeight(),0f);
        button.getPosition().inTL(0,0);

        ImageViewer commodity  = new ImageViewer(newHeight,newHeight,Global.getSettings().getCommoditySpec(commodityId).getIconName());
        tooltip.addCustom(commodity.getComponentPanel(),0f).getPosition().inTL(1,4);
        LabelAPI label = tooltip.addPara(Global.getSettings().getCommoditySpec(commodityId).getName(),faction.getBaseUIColor(),0f);
        label.getPosition().inTL(1,calculateHeight(label,height));
        label.autoSizeToWidth(UIData.WIDTH_OF_COMMODITY);
        label.setAlignment(Alignment.MID);
        float x = UIData.WIDTH_OF_COMMODITY;
        label = tooltip.addPara(FactionManager.getMarketSharePercentage(commodityId,faction) +"%", Color.ORANGE,0f);
        label.getPosition().inTL(x,calculateHeight(label,height));
        label.autoSizeToWidth(UIData.WIDTH_OF_MARKET_SHARE);
        label.setAlignment(Alignment.MID);
        x+=UIData.WIDTH_OF_MARKET_SHARE;
        label = tooltip.addPara(Misc.getDGSCredits(FactionManager.getProfitsFromCommodityAcrossColonies(commodityId,faction)), Color.ORANGE,0f);
        label.getPosition().inTL(x,calculateHeight(label,height));
        label.autoSizeToWidth(UIData.WIDTH_ON_PROFIT);
        label.setAlignment(Alignment.MID);

        x+=UIData.WIDTH_ON_PROFIT;

        label = tooltip.addPara(Misc.getDGSCredits(FactionManager.getTotalMarketValue(commodityId)), Color.ORANGE,0f);
        label.getPosition().inTL(x,calculateHeight(label,newHeight));
        label.autoSizeToWidth(UIData.WIDTH_OF_TOTAL_MARKET_VALUE);
        label.setAlignment(Alignment.MID);

        contentPanel.addUIElement(tooltip).inTL(0,0);
        mainPanel.addComponent(contentPanel).inTL(0,0);

    }
    public float calculateHeight(LabelAPI label, float height){
        return height/2-(label.computeTextHeight(label.getText())/2);
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

    }

    @Override
    public void processInput(List<InputEventAPI> events) {

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }
}
