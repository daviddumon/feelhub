package com.steambeat.domain.subject;

import com.steambeat.domain.Repository;
import com.steambeat.domain.subject.steam.Steam;

public interface SubjectRepository extends Repository<Subject> {

    public Steam getSteam();
}
