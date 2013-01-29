package com.feelhub.repositories;

import com.feelhub.domain.admin.AdminStatistic;
import com.feelhub.domain.admin.AdminStatisticsRepository;
import com.feelhub.domain.admin.Api;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.criteria.Restrictions;

import java.util.List;

public class AdminStatisticsMongoRepository extends BaseMongoRepository<AdminStatistic> implements AdminStatisticsRepository {

    public AdminStatisticsMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public AdminStatistic byMonthAndApi(String month, Api api) {
        final Criteria criteria = getSession().createCriteria(AdminStatistic.class);
        criteria.add(Restrictions.equals("month", month));
        criteria.add(Restrictions.equals("api", api));
        return extractOne(criteria);
    }

    @Override
    public List<AdminStatistic> getAll(Api api) {
        final Criteria criteria = getSession().createCriteria(AdminStatistic.class);
        criteria.add(Restrictions.equals("api", api));
        return criteria.list();
    }
}
