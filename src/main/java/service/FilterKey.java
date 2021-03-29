package service;

import model.Item;

import java.util.List;

public enum FilterKey {
    ALL {
        public List<Item> find() {
            return StoreHibernate.openSession().createQuery("FROM Item ORDER BY createTime", Item.class).getResultList();
        }
    },
    CLOSED {
        public List<Item> find() {
            return StoreHibernate.openSession().createQuery("FROM Item WHERE isDone = true ORDER BY createTime", Item.class).getResultList();
        }
    },
    OPENED {
        public List<Item> find() {
            return StoreHibernate.openSession().createQuery("FROM Item WHERE isDone = false ORDER BY createTime", Item.class).getResultList();
        }
    };

    public abstract List<Item> find();
}
