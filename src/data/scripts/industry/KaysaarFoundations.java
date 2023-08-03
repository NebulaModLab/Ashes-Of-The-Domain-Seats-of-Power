package data.scripts.industry;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.util.Misc;

public class KaysaarFoundations extends BaseIndustry {
    public static int MIN_SIZE = 20;
    public boolean toosmolfaction=false;
    public boolean toosmollplanet=false;
    public boolean ALREADY_HAVE_CAPITAL=false;
    @Override
    public void apply() {

    }
    @Override
    public boolean isAvailableToBuild() {
        int size=0;
        if(market.getFaction().getMemory().contains("$ashesOfDomainCapitalPlugin")){
            if(market.getPlanetEntity()==null){
                return false;
            }
            if(!market.getPlanetEntity().getMemory().is("$ashencapital:"+market.getFactionId(),true)){
                ALREADY_HAVE_CAPITAL= true;

            }
        }
        if(market.getSize()<=5){
            toosmollplanet=true;
        }
        for (MarketAPI factionMarket : Misc.getFactionMarkets(market.getFactionId())) {
            if(factionMarket!=null){
                size+= factionMarket.getSize();
            }

        }
        if(size<MIN_SIZE){
            toosmolfaction=true;

        }
        if(ALREADY_HAVE_CAPITAL||toosmolfaction||toosmollplanet){
            return false;
        }
        return true;


    }

        @Override
    public String getUnavailableReason() {
        String reason = "";
        if(market.getPlanetEntity()==null){
            return reason+="Capitals can't be built on Stations";
        }
        if(ALREADY_HAVE_CAPITAL){
            reason+=" You already got a capital\n";
            return reason;
        }

        if(toosmollplanet){
            reason +="That plannet is too small for being capital\n";
        }
        if(toosmolfaction) {
            reason += "Your faction have total size of their markets lower than 20\n";
        }
        return reason;


    }
}
