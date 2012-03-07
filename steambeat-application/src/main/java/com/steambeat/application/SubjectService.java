package com.steambeat.application;

import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.repositories.Repositories;

public class SubjectService {

    public Steam steam() {
        Steam steam = Repositories.steam().get("steam");
        if (steam == null) {
            steam = new Steam();
            Repositories.steam().add(steam);
        }
        return steam;
    }
}
