package me.VanadeysHaven.TimmyCore.Packages.Rank;

import lombok.Getter;
import me.VanadeysHaven.TimmyCore.Main;
import me.VanadeysHaven.TimmyCore.Utilities.StringUtilities;

@Getter
public enum Rank {

    PLAYER       ("3", "Player"      ),
    MOD          ("2", "Mod"         ),
    ADMIN        ("c", "Admin"       ),
    SPARKED      ("e", "Sparked"     ),
    SERVER_DEV   ("6", "Developer"   );

    private String colorCode;
    private String rankName;

    Rank(String colorCode, String rankName){
        this.colorCode = colorCode;
        this.rankName = rankName;
    }

    public String formatTag(){
            return StringUtilities.colorify("&8[&" + colorCode + getRankName() + "&8]&r");
    }

    public String getRankName(){
        if(this == PLAYER)
            return Main.getPlugin().getConfig().getString("server.defaultRank");
        else
            return rankName;
    }

}
