package com.steambeat.application;

import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.repositories.Repositories;

public class SubjectService {

    public Steam steam() {
        Steam steam = (Steam) Repositories.subjects().get("steam");
        if (steam == null) {
            steam = new Steam();
            Repositories.subjects().add(steam);
        }
        return steam;
    }
}
