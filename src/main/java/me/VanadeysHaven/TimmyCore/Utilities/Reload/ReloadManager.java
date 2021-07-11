package me.VanadeysHaven.TimmyCore.Utilities.Reload;

import java.util.ArrayList;
import java.util.List;

public final class ReloadManager {

    private static ReloadManager instance;

    public static ReloadManager getInstance(){
        if(instance == null)
            instance = new ReloadManager();

        return instance;
    }

    private List<Reloadable> reloadables;

    private ReloadManager(){
        reloadables = new ArrayList<>();
    }

    public void add(Reloadable... reloadables){
        for(Reloadable reloadable : reloadables)
            add(reloadable);
    }

    public void add(Reloadable reloadable){
        reloadables.add(reloadable);
    }

    public void reloadAll(){
        reloadables.forEach(Reloadable::reload);
    }

}
