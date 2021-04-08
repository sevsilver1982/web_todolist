package dao;

import model.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import service.Filter;
import service.FilterEnum;
import service.StoreHibernate;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public final class ItemDaoImpl implements ItemDao {

    private <T> T tx(final Function<Session, T> command) {
        try (Session session = StoreHibernate.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                T object = command.apply(session);
                transaction.commit();
                return object;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
    }

    @Override
    public Item add(final Item item) {
        return tx(session -> (Item) session.merge(item));
    }

    @Override
    public Boolean delete(final Item item) {
        tx(session -> {
            session.delete(item);
            return true;
        });
        return findById(item.getId()) == null;
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

}
