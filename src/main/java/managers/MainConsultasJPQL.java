package managers;

import funciones.FuncionApp;
import org.example.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainConsultasJPQL {
    public static void main(String[] args) {
    //REPOSITORIO-> https://github.com/gerardomagni/jpqlquerys.git

        // Esta línea le dice al sistema de logs de Java: "Para cualquier mensaje que venga de org.hibernate,
        // solo quiero ver los de nivel SEVERE (errores graves)"
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        // === MÉTODOS DEL PROFESOR ===

        //buscarFacturas();
        //buscarFacturasActivas();
        //buscarFacturasXNroComprobante();
        //buscarFacturasXRangoFechas();
        //buscarFacturaXPtoVentaXNroComprobante();
        //buscarFacturasXCliente();
        //buscarFacturasXCuitCliente();
        //buscarFacturasXArticulo();
        //mostrarMaximoNroFactura();
        //buscarClientesXIds();
        //buscarClientesXRazonSocialParcial();

        System.out.println("--- INICIO DE PRUEBAS ---");

        // === EJERCICIOS RESUELTOS ===

        System.out.println("\n--- Ejercicio 1: Listar Clientes ---");
        listarClientes();

        System.out.println("\n--- Ejercicio 2: Facturas del último mes ---");
        listarFacturasUltimoMes();

        System.out.println("\n--- Ejercicio 3: Cliente con más facturas ---");
        mostrarClienteConMasFacturas();

        System.out.println("\n--- Ejercicio 4: Artículos más vendidos ---");
        listarArticulosMasVendidos();

        System.out.println("\n--- Ejercicio 5: Facturas últimos 3 meses (Cliente 1L) ---");
        buscarFacturasUltimosTresMesesXCliente(1L);

        System.out.println("\n--- Ejercicio 6: Monto total (Cliente 1L) ---");
        mostrarMontoTotalFacturadoPorCliente(1L);

        System.out.println("\n--- Ejercicio 7: Artículos de Factura (Factura 1L) ---");
        listarArticulosDeFactura(1L);

        System.out.println("\n--- Ejercicio 8: Artículo más caro (Factura 1L) ---");
        mostrarArticuloMasCaroDeFactura(1L);

        System.out.println("\n--- Ejercicio 9: Cantidad total de facturas ---");
        mostrarCantidadTotalFacturas();

        System.out.println("\n--- Ejercicio 10: Facturas con total > 100.0 ---");
        listarFacturasSuperioresA(100.0);

        System.out.println("\n--- Ejercicio 11: Facturas que contienen 'Manzana' ---");
        listarFacturasPorDenominacionArticulo("Manzana");

        System.out.println("\n--- Ejercicio 12: Artículos por código parcial 'uuid' ---");
        listarArticulosPorCodigoParcial("-");

        System.out.println("\n--- Ejercicio 13: Artículos más caros que el promedio ---");
        listarArticulosMasCarosQuePromedio();

        System.out.println("\n--- Ejercicio 14: Facturas con Artículo 1L (Manzana) ---");
        listarFacturasPorArticuloConExists(1L);

        System.out.println("\n--- FIN DE PRUEBAS ---");
    }


    public static void buscarFacturas(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturas();
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasActivas(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasActivas();
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXNroComprobante(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasXNroComprobante(796910l);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXRangoFechas(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaInicio = FuncionApp.restarSeisMeses(fechaActual);
            List<Factura> facturas = mFactura.buscarFacturasXRangoFechas(fechaInicio, fechaActual);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturaXPtoVentaXNroComprobante(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Factura factura = mFactura.getFacturaXPtoVentaXNroComprobante(2024, 796910l);
            mostrarFactura(factura);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXCliente(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasXCliente(7l);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXCuitCliente(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasXCuitCliente("27236068981");
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXArticulo(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasXArticulo(6l);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void mostrarMaximoNroFactura(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Long nroCompMax = mFactura.getMaxNroComprobanteFactura();
            System.out.println("N° " + nroCompMax);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarClientesXIds(){
        ClienteManager mCliente = new ClienteManager(true);
        try {
            List<Long> idsClientes = new ArrayList<>();
            idsClientes.add(1l);
            idsClientes.add(2l);
            List<Cliente> clientes = mCliente.getClientesXIds(idsClientes);
            for(Cliente cli : clientes){
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }

    public static void buscarClientesXRazonSocialParcial(){
        ClienteManager mCliente = new ClienteManager(true);
        try {
            List<Long> idsClientes = new ArrayList<>();
            idsClientes.add(1l);
            idsClientes.add(2l);
            List<Cliente> clientes = mCliente.getClientesXRazonSocialParcialmente("Lui");
            for(Cliente cli : clientes){
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }



    public static void mostrarFactura(Factura factura){
        List<Factura> facturas = new ArrayList<>();
        facturas.add(factura);
        mostrarFacturas(facturas);
    }

    public static void mostrarFacturas(List<Factura> facturas){
        for(Factura fact : facturas){
            System.out.println("\nN° Comp: " + fact.getStrProVentaNroComprobante());
            System.out.println("Fecha: " + FuncionApp.formatLocalDateToString(fact.getFechaComprobante()));
            System.out.println("CUIT Cliente: " + FuncionApp.formatCuitConGuiones(fact.getCliente().getCuit()));
            System.out.println("Cliente: " + fact.getCliente().getRazonSocial() + " ("+fact.getCliente().getId() + ")");
            System.out.println("------Articulos------");
            for(FacturaDetalle detalle : fact.getDetallesFactura()){
                System.out.println(detalle.getArticulo().getDenominacion() + ", " + detalle.getCantidad() + " unidades, $" + FuncionApp.getFormatMilDecimal(detalle.getSubTotal(), 2));
            }
            System.out.println("Total: $" + FuncionApp.getFormatMilDecimal(fact.getTotal(),2));
            System.out.println("*************************");
        }
    }
    // === EJERCICIO 5 ===
    public static void buscarFacturasUltimosTresMesesXCliente(Long id) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasUltimosTresMesesXCliente(id);
            mostrarFacturas(facturas);
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    // === EJERCICIO 6 ===
    public static void mostrarMontoTotalFacturadoPorCliente(Long id) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Double total = mFactura.getMontoTotalFacturadoPorCliente(id);
            System.out.println("Total facturado por el cliente: $" + FuncionApp.getFormatMilDecimal(total, 2));
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    // === EJERCICIO 7 ===
    public static void listarArticulosDeFactura(Long id) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Articulo> articulos = mFactura.getArticulosDeFactura(id);
            for (Articulo art : articulos) {
                System.out.println("ID: " + art.getId() + " - " + art.getDenominacion());
            }
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    // === EJERCICIO 8 ===
    public static void mostrarArticuloMasCaroDeFactura(Long id) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Articulo articulo = mFactura.getArticuloMasCaroDeFactura(id);
            System.out.println("Artículo más caro: " + articulo.getDenominacion() +
                    " - $" + FuncionApp.getFormatMilDecimal(articulo.getPrecioVenta(), 2));
        } finally {
            mFactura.cerrarEntityManager();
        }


    }

    // === EJERCICIO 1 ===
    public static void listarClientes() {
        ClienteManager mCliente = new ClienteManager(true);
        try {
            List<Cliente> clientes = mCliente.getClientes();
            for (Cliente c : clientes) {
                System.out.println("ID: " + c.getId() + " - " + c.getRazonSocial());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mCliente.cerrarEntityManager();
        }
    }

    // === EJERCICIO 3 ===
    public static void mostrarClienteConMasFacturas() {
        ClienteManager mCliente = new ClienteManager(true);
        try {
            Object[] fila = mCliente.getClienteConMasFacturas();
            if (fila != null) {
                Cliente cliente = (Cliente) fila[0];
                Long cantidad = (Long) fila[1];
                System.out.println("Cliente: " + cliente.getRazonSocial() + " - Cantidad: " + cantidad);
            } else {
                System.out.println("No hay facturas registradas.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mCliente.cerrarEntityManager();
        }
    }

    // === EJERCICIO 4 ===
    public static void listarArticulosMasVendidos() {
        ArticuloManager mArticulo = new ArticuloManager(true);
        try {
            List<Object[]> resultado = mArticulo.getArticulosMasVendidos();
            System.out.println("Artículos más vendidos:");
            for (Object[] fila : resultado) {
                Articulo articulo = (Articulo) fila[0];
                Double cantidadTotal = (Double) fila[1]; // JPQL SUM devuelve Double pero por alguna razón es Object
                System.out.println(articulo.getDenominacion() + " - Cantidad vendida: " + cantidadTotal);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mArticulo.cerrarEntityManager();
        }
    }

    // === EJERCICIO 12 ===
    public static void listarArticulosPorCodigoParcial(String codigo) {
        ArticuloManager mArticulo = new ArticuloManager(true);
        try {
            List<Articulo> articulos = mArticulo.getArticulosPorCodigoParcial(codigo);
            System.out.println("Artículos encontrados por código parcial '" + codigo + "':");
            for (Articulo a : articulos) {
                System.out.println(a.getDenominacion() + " - " + a.getCodigo());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mArticulo.cerrarEntityManager();
        }
    }

    // === EJERCICIO 13 ===
    public static void listarArticulosMasCarosQuePromedio() {
        ArticuloManager mArticulo = new ArticuloManager(true);
        try {
            List<Articulo> articulos = mArticulo.getArticulosMasCarosQuePromedio();
            System.out.println("Artículos con precio superior al promedio:");
            for (Articulo a : articulos) {
                System.out.println(a.getDenominacion() + " - $" + a.getPrecioVenta());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mArticulo.cerrarEntityManager();
        }
    }

    // === EJERCICIO 2 ===
    public static void listarFacturasUltimoMes() {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasUltimoMes();
            System.out.println("Facturas emitidas en el último mes:");
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    // === EJERCICIO 9 ===
    public static void mostrarCantidadTotalFacturas() {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Long totalFacturas = mFactura.getCantidadTotalFacturas();
            System.out.println("Cantidad total de facturas generadas: " + totalFacturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    // === EJERCICIO 10 ===
    public static void listarFacturasSuperioresA(double valorMinimo) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasSuperioresA(valorMinimo);
            System.out.println("Facturas con total mayor a $" + valorMinimo + ":");
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    // === EJERCICIO 11 ===
    public static void listarFacturasPorDenominacionArticulo(String denominacion) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasPorDenominacionArticulo(denominacion);
            System.out.println("Facturas que contienen el artículo '" + denominacion + "':");
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

    // === EJERCICIO 14 ===
    public static void listarFacturasPorArticuloConExists(Long idArticulo) {
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasPorArticuloConExists(idArticulo);
            System.out.println("Facturas que contienen el artículo ID " + idArticulo + " (con EXISTS):");
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }

}
