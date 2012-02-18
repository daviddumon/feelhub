package com.steambeat.domain.subject.steam;

import com.steambeat.domain.subject.Subject;

public class Steam extends Subject {

    public Steam() {
        super("steam");
    }

    @Override
    public void update() {
        this.description = "steam";
        this.shortDescription = "steam";
        this.illustration = "";
    }
}
