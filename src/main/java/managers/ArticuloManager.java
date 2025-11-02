package managers;

import org.example.Articulo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticuloManager {
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public ArticuloManager(boolean anularShowSQL) {
        Map<String, Object> properties = new HashMap<>();
        if (anularShowSQL) {
            properties.put("hibernate.show_sql", "false");
        } else {
            properties.put("hibernate.show_sql", "true");
        }
        // Usar "update" para no borrar la BD
        properties.put("hibernate.hbm2ddl.auto", "update");

        emf = Persistence.createEntityManagerFactory("example-unit", properties);
        em = emf.createEntityManager();
    }

    // =====================================================
    // MÉTODOS (EJERCICIOS 4, 12, 13)
    // =====================================================

    /**
     * Ejercicio 4: Listar los artículos más vendidos
     */
    public List<Object[]> getArticulosMasVendidos() {
        // CORRECCIÓN: El JOIN d.articulo a no es necesario en JPQL
        String jpql = "SELECT d.articulo, SUM(d.cantidad) " +
                "FROM FacturaDetalle d " +
                "GROUP BY d.articulo " +
                "ORDER BY SUM(d.cantidad) DESC";
        Query query = em.createQuery(jpql, Object[].class);
        return query.getResultList();
    }

    /**
     * Ejercicio 12: Listar Artículos por código parcial
     */
    public List<Articulo> getArticulosPorCodigoParcial(String codigoParcial) {
        String jpql = "SELECT a FROM Articulo a " +
                "WHERE LOWER(a.codigo) LIKE LOWER(CONCAT('%', :codigoParcial, '%')) " +
                "ORDER BY a.codigo";
        Query query = em.createQuery(jpql, Articulo.class);
        query.setParameter("codigoParcial", codigoParcial);
        return query.getResultList();
    }

    /**
     * Ejercicio 13: Listar Artículos con precio mayor al promedio
     */
    public List<Articulo> getArticulosMasCarosQuePromedio() {
        String jpql = "SELECT a FROM Articulo a " +
                "WHERE a.precioVenta > (SELECT AVG(a2.precioVenta) FROM Articulo a2) " +
                "ORDER BY a.precioVenta DESC";
        Query query = em.createQuery(jpql, Articulo.class);
        return query.getResultList();
    }

    public void cerrarEntityManager() {
        em.close();
        emf.close();
    }
}