package managers;

import org.example.Articulo;
import org.example.Factura;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacturaManager {
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public FacturaManager(boolean anularShowSQL) {
        Map<String, Object> properties = new HashMap<>();
        if (anularShowSQL) {
            // Desactivar el show_sql (si está activado en el persistence.xml o configuración por defecto)
            properties.put("hibernate.show_sql", "false");
        } else {
            properties.put("hibernate.show_sql", "true");
        }

        properties.put("hibernate.hbm2ddl.auto", "update");

        emf = Persistence.createEntityManagerFactory("example-unit", properties);
        em = emf.createEntityManager();
    }

    // =====================================================
    // MÉTODOS ORIGINALES
    // =====================================================

    public List<Factura> getFacturas() {
        String jpql = "FROM Factura";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    public List<Factura> getFacturasActivas() {
        String jpql = "FROM Factura WHERE fechaBaja IS NULL ORDER BY fechaComprobante DESC";
        Query query = em.createQuery(jpql);
        return query.getResultList();
    }

    public List<Factura> getFacturasXNroComprobante(Long nroComprobante) {
        String jpql = "FROM Factura WHERE nroComprobante = :nroComprobante";
        Query query = em.createQuery(jpql);
        query.setParameter("nroComprobante", nroComprobante);
        return query.getResultList();
    }

    public List<Factura> buscarFacturasXRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        String jpql = "FROM Factura WHERE fechaComprobante >= :fechaInicio AND fechaComprobante <= :fechaFin";
        Query query = em.createQuery(jpql);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }

    public Factura getFacturaXPtoVentaXNroComprobante(Integer puntoVenta, Long nroComprobante) {
        String jpql = "FROM Factura WHERE puntoVenta = :puntoVenta AND nroComprobante = :nroComprobante";
        Query query = em.createQuery(jpql);
        query.setMaxResults(1);
        query.setParameter("puntoVenta", puntoVenta);
        query.setParameter("nroComprobante", nroComprobante);
        return (Factura) query.getSingleResult();
    }

    public List<Factura> getFacturasXCliente(Long idCliente) {
        String jpql = "FROM Factura WHERE cliente.id = :idCliente";
        Query query = em.createQuery(jpql);
        query.setParameter("idCliente", idCliente);
        return query.getResultList();
    }

    public List<Factura> getFacturasXCuitCliente(String cuitCliente) {
        String jpql = "FROM Factura WHERE cliente.cuit = :cuitCliente";
        Query query = em.createQuery(jpql);
        query.setParameter("cuitCliente", cuitCliente);
        return query.getResultList();
    }

    public List<Factura> getFacturasXArticulo(Long idArticulo) {
        StringBuilder jpql = new StringBuilder("SELECT fact FROM Factura AS fact LEFT OUTER JOIN fact.detallesFactura AS detalle");
        jpql.append(" WHERE detalle.id = :idArticulo");
        Query query = em.createQuery(jpql.toString());
        query.setParameter("idArticulo", idArticulo);
        return query.getResultList();
    }

    public Long getMaxNroComprobanteFactura() {
        StringBuilder jpql = new StringBuilder("SELECT MAX(nroComprobante) FROM Factura WHERE fechaBaja IS NULL");
        Query query = em.createQuery(jpql.toString());
        return (Long) query.getSingleResult();
    }

    // =====================================================
    // NUEVOS MÉTODOS (EJERCICIOS 5–8)
    // =====================================================

    /**
     * EJERCICIO 5:
     * Consultar las facturas emitidas en los 3 últimos meses de un cliente específico
     */
    public List<Factura> getFacturasUltimosTresMesesXCliente(Long idCliente) {
        LocalDate fechaFin = LocalDate.now();
        LocalDate fechaInicio = fechaFin.minusMonths(3);

        String jpql = "FROM Factura f WHERE f.cliente.id = :idCliente " +
                "AND f.fechaComprobante BETWEEN :fechaInicio AND :fechaFin " +
                "ORDER BY f.fechaComprobante DESC";

        Query query = em.createQuery(jpql);
        query.setParameter("idCliente", idCliente);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);

        return query.getResultList();
    }

    /**
     * EJERCICIO 6:
     * Calcular el monto total facturado por un cliente específico
     */
    public Double getMontoTotalFacturadoPorCliente(Long idCliente) {
        String jpql = "SELECT SUM(f.total) FROM Factura f WHERE f.cliente.id = :idCliente AND f.fechaBaja IS NULL";
        Query query = em.createQuery(jpql);
        query.setParameter("idCliente", idCliente);

        Double totalFacturado = (Double) query.getSingleResult();
        return totalFacturado != null ? totalFacturado : 0.0;
    }

    /**
     * EJERCICIO 7:
     * Listar los Artículos vendidos en una factura específica
     */
    public List<Articulo> getArticulosDeFactura(Long idFactura) {
        String jpql = "SELECT df.articulo FROM FacturaDetalle df WHERE df.factura.id = :idFactura";
        Query query = em.createQuery(jpql);
        query.setParameter("idFactura", idFactura);
        return query.getResultList();
    }

    /**
     * EJERCICIO 8:
     * Obtener el Artículo más caro vendido en una factura específica
     */
    public Articulo getArticuloMasCaroDeFactura(Long idFactura) {
        String jpql = "SELECT df.articulo FROM FacturaDetalle df " +
                "WHERE df.factura.id = :idFactura " +
                "ORDER BY df.articulo.precioVenta DESC";

        Query query = em.createQuery(jpql);
        query.setParameter("idFactura", idFactura);
        query.setMaxResults(1); // Solo devuelve el más caro

        return (Articulo) query.getSingleResult();
    }

    // =====================================================
    // CIERRE DEL ENTITY MANAGER
    // =====================================================

    public void cerrarEntityManager() {
        em.close();
        emf.close();
    }
}
