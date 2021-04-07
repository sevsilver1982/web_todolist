package dao;

import model.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.Filter;
import service.FilterEnum;
import service.StoreHibernate;

import java.util.List;
import java.util.UUID;

public final class ItemDaoImpl implements ItemDao {

    @Override
    public Item add(final Item item) {
        try (Session session = StoreHibernate.openSession()) {
            Transaction transaction = session.beginTransaction();
            Item object = (Item) session.merge(item);
            transaction.commit();
            return object;
        }
    }

    @Override
    public List<Item> findAll() {
        return StoreHibernate.openSession().createQuery("FROM Item ORDER BY createTime", Item.class).getResultList();
    }

    public List<Item> findAll(final FilterEnum filterEnum) {
        return new Filter(filterEnum).setFilter();
    }

    @Override
    public Item findById(final UUID id) {
        return StoreHibernate.openSession().find(Item.class, id);
    }

    @Override
    public Boolean delete(final Item item) {
        try (Session session = StoreHibernate.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(item);
            transaction.commit();
            return findById(item.getId()) == null;
        }
    }

}
