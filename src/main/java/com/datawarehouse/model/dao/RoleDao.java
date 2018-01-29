package com.datawarehouse.model.dao;


import com.datawarehouse.model.entity.Role;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class RoleDao {

    @Autowired
    private SessionFactory sessionFactory;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addURole(Role role) {
        Serializable save = getSessionFactory().getCurrentSession().save(role);

    }

    public void deleteRole(Role role) {
        getSessionFactory().getCurrentSession().delete(role);
    }


    public void updateRole(Role role) {
        getSessionFactory().getCurrentSession().update(role);
    }


    public List<Role> getRoleAll() {
        List list = getSessionFactory().getCurrentSession().createQuery("from  Role ").list();
        return list;
    }

    public Role getRoleById(long id) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Role.class);
        criteria.add(Restrictions.eq("id",id));
        return (Role) criteria.uniqueResult();
    }

}
