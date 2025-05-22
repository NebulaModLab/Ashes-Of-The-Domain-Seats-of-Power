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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FactionAllData implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel;
    ArrayList<FactionMarketData>data;
    CustomPanelAPI componentPanel;
    ButtonAPI localData;
    ButtonAPI highLight;
    String commodityID;
    public FactionAllData(float width, float height) {
        mainPanel = Global.getSettings().createCustom(width,height,this);
        data = new ArrayList<>();
        createUI();
    }
    public void createUI(){
        if(componentPanel!=null){
            mainPanel.removeComponent(componentPanel);
        }
        Color base,bg,bright;
        base = Misc.getBasePlayerColor();
        bg = Misc.getDarkPlayerColor();
        UIData.recompute(mainPanel.getPosition().getWidth()-10);
        bright = Misc.getBrightPlayerColor();
        componentPanel = Global.getSettings().createCustom(mainPanel.getPosition().getWidth(),mainPanel.getPosition().getHeight(),null);
        if(commodityID==null){
            TooltipMakerAPI tooltip = componentPanel.createUIElement(componentPanel.getPosition().getWidth(),20,false);
            TooltipMakerAPI mainTooltip = componentPanel.createUIElement(componentPanel.getPosition().getWidth(),componentPanel.getPosition().getHeight()-75,true);
            TooltipMakerAPI btTooltip = componentPanel.createUIElement(componentPanel.getPosition().getWidth(),30,false);
            localData = btTooltip.addButton("Go back",null, Misc.getBasePlayerColor(),Misc.getDarkPlayerColor(), Alignment.MID,CutStyle.TL_BR,250,30,0f);localData.getPosition().setLocation(0,0).inTL(0,0);
            ButtonAPI button = tooltip.addAreaCheckbox("Commodity",null,base,bg,bright,UIData.WIDTH_OF_COMMODITY-1,20,0f);
            button.getPosition().inTL(0,0);
            button.setClickable(false);
            localData.setEnabled(false);
            localData.setClickable(false);
            button = tooltip.addAreaCheckbox("Market share",null,base,bg,bright,UIData.WIDTH_OF_MARKET_SHARE-1,20,0f);
            button.getPosition().inTL(UIData.WIDTH_OF_COMMODITY,0);
            button.setClickable(false);

            button = tooltip.addAreaCheckbox("Income from export",null,base,bg,bright,UIData.WIDTH_ON_PROFIT-1,20,0f);
            button.getPosition().inTL(UIData.WIDTH_OF_COMMODITY+UIData.WIDTH_OF_MARKET_SHARE,0);
            button.setClickable(false);

            button = tooltip.addAreaCheckbox("Total Market Value",null,base,bg,bright,UIData.WIDTH_OF_TOTAL_MARKET_VALUE-1 ,20,0f);
            button.getPosition().inTL(UIData.WIDTH_OF_COMMODITY+UIData.WIDTH_OF_MARKET_SHARE+UIData.WIDTH_ON_PROFIT,0);
            button.setClickable(false);

            highLight = localData;
            if(data.isEmpty()){
                for (String s : FactionManager.getAllCommoditiesInCirculationSorted(Global.getSector().getPlayerFaction(),true)) {
                    FactionMarketData data = new FactionMarketData(componentPanel.getPosition().getWidth()-10,Global.getSector().getPlayerFaction(),s);
                    this.data.add(data);
                }
                data.forEach(FactionMarketData::createUI);
                data.forEach(x->mainTooltip.addCustom(x.getMainPanel(),5f));
            }
            else{
                data.forEach(FactionMarketData::createUI);
                data.forEach(x->mainTooltip.addCustom(x.getMainPanel(),5f));
            }
            componentPanel.addUIElement(tooltip).inTL(0,0);
            componentPanel.addUIElement(mainTooltip).inTL(-5,25);
            componentPanel.addUIElement(btTooltip).inTL(0,componentPanel.getPosition().getHeight()-30);
        }
        else{


            TooltipMakerAPI tooltip = componentPanel.createUIElement(componentPanel.getPosition().getWidth(),20,false);
            TooltipMakerAPI headerTooltip = componentPanel.createUIElement(componentPanel.getPosition().getWidth(),100,false);
            TooltipMakerAPI mainTooltip = componentPanel.createUIElement(componentPanel.getPosition().getWidth(),componentPanel.getPosition().getHeight()-175,true);
            TooltipMakerAPI btTooltip = componentPanel.createUIElement(componentPanel.getPosition().getWidth(),30,false);
            headerTooltip.setParaFont(Fonts.ORBITRON_24AA);
            headerTooltip.addPara(Global.getSettings().getCommoditySpec(commodityID).getName(),Misc.getTooltipTitleAndLightHighlightColor(),0f).setAlignment(Alignment.MID);
            headerTooltip.setParaFont(Fonts.ORBITRON_16);
            headerTooltip.addPara("Global Market Value : %s",5f,Color.ORANGE,Misc.getDGSCredits(FactionManager.getTotalMarketValue(commodityID))).setAlignment(Alignment.MID);
            ImageViewer viewer = new ImageViewer(60,60,Global.getSettings().getCommoditySpec(commodityID).getIconName());
            headerTooltip.addCustom(viewer.getComponentPanel(),5f).getPosition().inTL(componentPanel.getPosition().getWidth()/2-(viewer.getComponentPanel().getPosition().getWidth()/2),50);

            localData = btTooltip.addButton("Go back",null, Misc.getBasePlayerColor(),Misc.getDarkPlayerColor(), Alignment.MID,CutStyle.TL_BR,250,30,0f);localData.getPosition().setLocation(0,0).inTL(0,0);
            ButtonAPI button = tooltip.addAreaCheckbox("Faction",null,base,bg,bright,UIData.WIDTH_OF_COMMODITY-1,20,0f);
            button.getPosition().inTL(0,0);
            button.setClickable(false);

            button = tooltip.addAreaCheckbox("Market share",null,base,bg,bright,UIData.WIDTH_OF_MARKET_SHARE-1,20,0f);
            button.getPosition().inTL(UIData.WIDTH_OF_COMMODITY,0);
            button.setClickable(false);

            button = tooltip.addAreaCheckbox("Income from export",null,base,bg,bright,UIData.WIDTH_ON_PROFIT+UIData.WIDTH_OF_TOTAL_MARKET_VALUE-1,20,0f);
            button.getPosition().inTL(UIData.WIDTH_OF_COMMODITY+UIData.WIDTH_OF_MARKET_SHARE,0);
            button.setClickable(false);

            for (FactionAPI faction : FactionManager.getMarketSharePercentageForEachFactionSorted(commodityID).keySet()) {
                FactionTotalMarketData data = new FactionTotalMarketData(componentPanel.getPosition().getWidth()-10,faction,commodityID);
                data.button.setClickable(false);
                mainTooltip.addCustom(data.getMainPanel(),5f);
            }
            TooltipMakerAPI.PlanetInfoParams params = new TooltipMakerAPI.PlanetInfoParams();
            params.showConditions=true;
            params.showName=true;
            params.withClass = true;
            params.conditionsHeight=40;
            mainTooltip.showPlanetInfo(Misc.getPlayerMarkets(true).get(0).getPlanetEntity(),100,100,params,5f);

            componentPanel.addUIElement(headerTooltip).inTL(0,0);
            componentPanel.addUIElement(tooltip).inTL(0,120);
            componentPanel.addUIElement(mainTooltip).inTL(-5,140);
            componentPanel.addUIElement(btTooltip).inTL(0,componentPanel.getPosition().getHeight()-30);
        }
        mainPanel.addComponent(componentPanel);

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
        for (FactionMarketData datum : data) {
            if(datum.getButton().isChecked()){
                datum.getButton().setChecked(false);
                commodityID = datum.commodityId;
                createUI();
                break;
            }
        }
        if(localData!=null&&localData.isChecked()){
            localData.setChecked(false);
            commodityID = null;
            createUI();
        }
    }

    @Override
    public void processInput(List<InputEventAPI> events) {

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }
}
