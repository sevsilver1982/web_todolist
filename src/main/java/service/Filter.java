package service;

import model.Item;

import java.util.List;

public final class Filter {
    private final FilterEnum filterEnum;

    public Filter(final FilterEnum filterEnum) {
        this.filterEnum = filterEnum;
    }

    public List<Item> setFilter() {
        switch (filterEnum) {
            case CLOSED:
                return StoreHibernate.openSession().createQuery("FROM Item WHERE isDone = true ORDER BY createTime", Item.class).getResultList();
            case OPENED:
                return StoreHibernate.openSession().createQuery("FROM Item WHERE isDone = false ORDER BY createTime", Item.class).getResultList();
            default:
                return StoreHibernate.openSession().createQuery("FROM Item ORDER BY createTime", Item.class).getResultList();
        }
    }

}
