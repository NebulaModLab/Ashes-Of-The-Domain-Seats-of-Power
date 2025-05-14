package data.ui.timeline;

import ashlib.data.plugins.ui.models.resizable.ImageViewer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.*;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionTimelineEvent;
import data.ui.basecomps.ExtendUIPanelPlugin;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.util.List;

public class NoticeableEventComponent implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel;
    CustomPanelAPI contentPanel;
    BaseFactionTimelineEvent timelineEvent;
    public static float width = 300;
    public static float height = 150;
    public NoticeableEventComponent(BaseFactionTimelineEvent event){
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
        ImageViewer viewer = new ImageViewer(contentPanel.getPosition().getWidth(),contentPanel.getPosition().getHeight(), timelineEvent.getImagePath());
        viewer.setAlphaMult(1f);
        tooltip.addCustom(viewer.getComponentPanel(),0f).getPosition().inTL(0,0);
        ButtonAPI button = tooltip.addAreaCheckbox("",null, Misc.getBasePlayerColor(),Misc.getDarkPlayerColor(),Misc.getBrightPlayerColor(),contentPanel.getPosition().getWidth(),contentPanel.getPosition().getHeight(),0f);
        button.getPosition().inTL(0,0);;
        button.setClickable(false);
        tooltip.setParaFont(Fonts.ORBITRON_20AA);
        LabelAPI labelAPI = tooltip.addPara(timelineEvent.getTitleOfEvent(), Misc.getTooltipTitleAndLightHighlightColor(),0f);
        labelAPI.getPosition().inTL(0,2);
        labelAPI.setAlignment(Alignment.MID);
        labelAPI = tooltip.addPara(timelineEvent.getCycle()+" . "+timelineEvent.getMonth() + " . "+timelineEvent.getDay(), Color.ORANGE,0f);
        labelAPI.getPosition().inTL(0,contentPanel.getPosition().getHeight()-labelAPI.getPosition().getHeight()-20);
        labelAPI.setAlignment(Alignment.MID);
        TooltipMakerAPI tl = tooltip.beginSubTooltip(contentPanel.getPosition().getWidth()-10);
        timelineEvent.createSmallNoteForEvent(tl);
        tooltip.endSubTooltip();
        tooltip.addCustom(tl,0f).getPosition().inTL(0,(height-15)/2-(tl.getHeightSoFar()/2));
        ImageViewer viewer1 = new ImageViewer(contentPanel.getPosition().getWidth(),contentPanel.getPosition().getHeight(),Global.getSettings().getSpriteName("timeline_overlay","test"));
        tooltip.addCustom(viewer1.getComponentPanel(),0f).getPosition().inTL(0,0);
        contentPanel.addUIElement(tooltip).inTL(0,0);
        tooltip.addTooltipTo(new TooltipMakerAPI.TooltipCreator() {
            @Override
            public boolean isTooltipExpandable(Object tooltipParam) {
                return false;
            }

            @Override
            public float getTooltipWidth(Object tooltipParam) {
                return 400f;
            }

            @Override
            public void createTooltip(TooltipMakerAPI tooltip, boolean expanded, Object tooltipParam) {
                timelineEvent.createDetailedTooltipOnHover(tooltip);
            }
        },contentPanel, TooltipMakerAPI.TooltipLocation.BELOW,false);
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

    }

    @Override
    public void processInput(List<InputEventAPI> events) {

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }
}
