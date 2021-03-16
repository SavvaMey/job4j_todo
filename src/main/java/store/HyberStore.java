package store;

import model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HyberStore implements Store, AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(HyberStore.class.getName());
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final Store INST = new HyberStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Item create(Item item) {
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
        }  catch (Exception e) {
        LOG.error(e.getMessage());
    }
        return item;
    }

    @Override
    public boolean update(int id, Item item) {
        boolean result = true;
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            item.setId(id);
            if (session.get(Item.class, id) != null) {
                Query query = session.createQuery(
                        "update model.Item set finished = :finished where id = :id");
                query.setParameter("finished", true);
                query.setParameter("id", id);
                query.executeUpdate();
            } else {
                result = false;
            }
            transaction.commit();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            Item item = new Item(id);
            if (session.get(Item.class, id) != null) {
                session.delete(item);
                result = true;
            }
            transaction.commit();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List<Item> result;
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            result = session.createQuery("from model.Item").list();
            transaction.commit();
        }
        return result;
    }

    @Override
    public Item findById(int id) {
        Item item = null;
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();
            item = session.get(Item.class, id);
            transaction.commit();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return item;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
