package hibernate;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import product.Barcode;
import product.Category;
import product.Product;
import sun.rmi.runtime.Log;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Created by User on 06.07.2017.
 */
public class ManageProduct  extends Thread {

    private SessionFactory sessionFactory;

    public ManageProduct() {
        super.start();
        sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public synchronized Integer addProduct(Category category, Product product, Barcode barcode){

        Transaction transaction = null;
        Integer productId = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            product.setCategory(category);
            product.setBarcode(barcode);

            productId = (Integer) session.save(product);

            Criteria criteria = session.createCriteria(Barcode.class);
            criteria.add(Restrictions.eq("barcode", barcode.getBarcode()));
            criteria.setProjection(Projections.rowCount());

            long count = (Long) criteria.uniqueResult();

            session.getTransaction();

            if(count != 0) {
                transaction.commit();
            }

            else {
                Log.getLog("Продукт уже существует", barcode.getBarcode(), productId);
            }

        } catch (HibernateError error) {
            if (transaction != null) transaction.rollback();
            error.printStackTrace();
        }

        System.out.println("Продукт успешно добавлен в базу данных.");
        return productId;
    }

    public static boolean uniqueExists(Criteria uniqueCriteria) {
        uniqueCriteria.setProjection(Projections.id());
        return uniqueCriteria.uniqueResult() != null;
    }
}
