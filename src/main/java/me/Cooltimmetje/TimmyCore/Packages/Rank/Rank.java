package me.Cooltimmetje.TimmyCore.Packages.Rank;

import lombok.Getter;

@Getter
public enum Rank {

    NEUROCITIZEN ("3", "Neurocitizen"),
    MOD          ("2", "Mod"         ),
    OWNER        ("c", "Owner"       ),
    SPARKED      ("e", "Sparked"     ),
    SERVER_DEV   ("6", "Developer"   );

    private String colorCode;
    private String rankName;

    Rank(String colorCode, String rankName){
        this.colorCode = colorCode;
        this.rankName = rankName;
    }

}
