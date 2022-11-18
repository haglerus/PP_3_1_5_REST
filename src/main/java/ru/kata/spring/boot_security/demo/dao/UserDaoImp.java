package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;


@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = (User) entityManager.createQuery("select u from User u left join fetch u.roles where u.username = :username").setParameter("username", username).getSingleResult();
        if (user == null) {
            throw new UsernameNotFoundException("User with this username does not exist");
        } else {
            return user;
        }
    }

    @Override
    public Set<User> listUsers() {
        return new TreeSet<User>(entityManager.createQuery("select u from User u left join fetch u.roles").getResultList());
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(getUser(id));
    }
}
