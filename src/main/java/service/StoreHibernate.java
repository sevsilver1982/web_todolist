package service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public final class StoreHibernate {
    private static final StandardServiceRegistry SERVICE_REGISTRY;
    private static final SessionFactory SESSION_FACTORY;

    private StoreHibernate() {
    }

    static {
        try {
            SERVICE_REGISTRY = new StandardServiceRegistryBuilder().configure().build();
            SESSION_FACTORY = new MetadataSources(SERVICE_REGISTRY).buildMetadata().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static Session openSession() {
        return SESSION_FACTORY.openSession();
    }

    public static void shutdown() {
        if (SERVICE_REGISTRY != null) {
            SERVICE_REGISTRY.close();
            StandardServiceRegistryBuilder.destroy(SERVICE_REGISTRY);
        }
    }

}
