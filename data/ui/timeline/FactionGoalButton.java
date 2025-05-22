package data.ui.timeline;

import ashlib.data.plugins.ui.models.resizable.ImageViewer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignClockAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.*;
import com.fs.starfarer.api.util.Misc;
import data.misc.ReflectionUtilis;
import data.scripts.factiongoals.BaseFactionGoal;
import data.scripts.factiongoals.ResearchAndExplorationGoal;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;
import data.ui.basecomps.ExtendUIPanelPlugin;

import java.awt.*;
import java.util.List;

public class FactionGoalButton implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel;
    CustomPanelAPI contentPanel;
    TimelineEventType timelineEvent;
    ButtonAPI button;
    public static float width = 390;
    public static float height = 100;
    public FactionGoalButton(TimelineEventType event){
        mainPanel = Global.getSettings().createCustom(width,height,this);
        timelineEvent = event;
        createUI();
    }
    public void createUI(){
        if(contentPanel!=null){
            mainPanel.removeComponent(contentPanel);
        }
        contentPanel = Global.getSettings().createCustom(mainPanel.getPosition().getWidth(),mainPanel.getPosition().getHeight(),null);
        TooltipMakerAPI tooltip = contentPanel.createUIElement(contentPanel.getPosition().getWidth(),contentPanel.getPosition().getHeight(),false);
        button = tooltip.addAreaCheckbox("",null, Misc.getBasePlayerColor(),Misc.getDarkPlayerColor(),Misc.getBrightPlayerColor(),contentPanel.getPosition().getWidth(),contentPanel.getPosition().getHeight(),0f);
        button.getPosition().inTL(0,0);;
        TooltipMakerAPI tl = tooltip.beginSubTooltip(contentPanel.getPosition().getWidth()-10);
        tl.setParaFont(Fonts.ORBITRON_24AA);
        tl.addPara(FactionManager.getStringType(timelineEvent),Misc.getTooltipTitleAndLightHighlightColor(),0f).setAlignment(Alignment.MID);
        tl.setParaFont(Fonts.ORBITRON_16);
        Color[]colors = new Color[]{Color.ORANGE,Color.ORANGE};
        tl.addPara("%s / %s",5f,colors,FactionManager.getInstance().getGoalStat(timelineEvent).getModifiedInt()+"",""+FactionManager.maxPerCategory).setAlignment(Alignment.MID);
        tooltip.endSubTooltip();
        tooltip.addCustom(tl,0f).getPosition().inTL(5,(height-15)/2-(tl.getHeightSoFar()/2));
        ImageViewer viewer1 = new ImageViewer(contentPanel.getPosition().getWidth(),contentPanel.getPosition().getHeight(),Global.getSettings().getSpriteName("timeline_goal_overlay",timelineEvent.toString()));
        tooltip.addCustom(viewer1.getComponentPanel(),0f).getPosition().inTL(0,0);
        contentPanel.addUIElement(tooltip).inTL(0,0);
        BaseFactionGoal goal = FactionManager.getInstance().getScriptForGoal(timelineEvent);
        tooltip.addTooltipTo(new TooltipMakerAPI.TooltipCreator() {
            @Override
            public boolean isTooltipExpandable(Object tooltipParam) {
                return false;
            }

            @Override
            public float getTooltipWidth(Object tooltipParam) {
                return 500;
            }

            @Override
            public void createTooltip(TooltipMakerAPI tooltip, boolean expanded, Object tooltipParam) {
                    goal.createTooltip(tooltip);
            }
        },contentPanel, TooltipMakerAPI.TooltipLocation.RIGHT);
        mainPanel.addComponent(contentPanel).inTL(0,0);
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
        if(button.isChecked()){
            button.setChecked(false);

        }
    }

    @Override
    public void processInput(List<InputEventAPI> events) {

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }



}
