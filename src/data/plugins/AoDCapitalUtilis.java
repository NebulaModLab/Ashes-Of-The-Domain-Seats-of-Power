package data.plugins;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.util.Misc;
import data.Ids.AoDConditions;
import data.Ids.AodMemFlags;


public class AoDCapitalUtilis {
    public static int MIN_SIZE = 20;

    public static void setCapital(MarketAPI setMarket){
        if(setMarket.getPlanetEntity()==null){
            return;
        }
        if(!setMarket.getPlanetEntity().getMemory().contains(AodMemFlags.CAPITAL_SETTER_FLAG)&&!setMarket.hasCondition(AoDConditions.REGIONAL_POWER_CAPITAL)){
            setMarket.getFaction().getMemory().set(AodMemFlags.CAPITAL_ENABLED_FOR_FACTION_FLAG,true);
            setMarket.getPlanetEntity().getMemory().set(AodMemFlags.CAPITAL_NAME_FLAG+setMarket.getFactionId(),true);
            setMarket.getPlanetEntity().getMemory().set(AodMemFlags.CAPITAL_SETTER_FLAG,true);
            setMarket.addCondition(AoDConditions.REGIONAL_POWER_CAPITAL);
        }
    }
    public static  boolean canBuildCapital(MarketAPI market) {
        if(market.getPlanetEntity()==null){
            return false;
        }
        int size=0;
        if(market.getFaction().getMemory().contains(AodMemFlags.CAPITAL_SETTER_FLAG)){
            if(!market.getPlanetEntity().getMemory().is(AodMemFlags.CAPITAL_NAME_FLAG+market.getFactionId(),true)){
               return false;
            }
        }

        if(market.getSize()<=5){
            return false;
        }
        for (MarketAPI factionMarket : Misc.getFactionMarkets(market.getFactionId())) {
            size+= factionMarket.getSize();
        }
        if(size<MIN_SIZE){
            return false;

        }
        return true;
    }
    public static  String getUnavailableReasonForCapital(MarketAPI market) {
        boolean toosmolfaction=false;
        boolean toosmollplanet=true;
        boolean ALREADY_HAVE_CAPITAL=false;
        if(market.getPlanetEntity()==null){
            return "Can't be build here due to technical problems, if you encounter them contact on dsc Kaysaar#1181";
        }

        if(market.getFaction().getMemory().contains(AodMemFlags.CAPITAL_ENABLED_FOR_FACTION_FLAG)){
            if(!market.getPlanetEntity().getMemory().is(AodMemFlags.CAPITAL_NAME_FLAG+market.getFactionId(),true)){
                ALREADY_HAVE_CAPITAL= true;

            }
        }
        String reason = "";
        if(market.getSize()>=6){
            toosmollplanet = false;
        }
        if(ALREADY_HAVE_CAPITAL){
            reason+=" You already got a capital\n";
            return reason;
        }
        int size =0;
        for (MarketAPI factionMarket : Misc.getFactionMarkets(market.getFactionId())) {
            size+= factionMarket.getSize();
        }
        if(size<MIN_SIZE){
            toosmolfaction=true;

        }
        if(toosmollplanet){
            reason +="That plannet is too small for being capital\n";
        }
        if(toosmolfaction) {
            reason += "Your faction have total size of their markets lower than " + MIN_SIZE + "\n";
        }
        return reason;
    }

}
