package com.steambeat.domain.subject;

import com.steambeat.domain.Repository;
import com.steambeat.domain.subject.steam.Steam;
import com.steambeat.domain.subject.webpage.WebPage;

import java.util.List;

public interface SubjectRepository extends Repository<Subject> {

    public Steam getSteam();

    public List<WebPage> getAllWebPages();
}
