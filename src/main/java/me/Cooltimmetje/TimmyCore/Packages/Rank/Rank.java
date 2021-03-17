package me.Cooltimmetje.TimmyCore.Packages.Rank;

import lombok.Getter;
import me.Cooltimmetje.TimmyCore.Utilities.StringUtilities;

@Getter
public enum Rank {

    NEUROCITIZEN ("3", "Neurocitizen"),
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
        return StringUtilities.colorify("&8[&" + colorCode + rankName + "&8]&r");
    }

}
