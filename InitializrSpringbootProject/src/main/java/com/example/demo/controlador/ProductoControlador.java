/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controlador;

import com.example.demo.modelo.Producto;
import com.example.demo.modelo.ProductosInterfaz;
import com.example.demo.vista.ActualizarV;
import com.example.demo.vista.Advertencia;
import com.example.demo.vista.InformeV;
import com.example.demo.vista.VistaInterfaz;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;


  public class ProductoControlador  {
    @Autowired
    ProductosInterfaz rp;
    VistaInterfaz m;
    ArrayList<Producto> listaProductos;

    public ProductoControlador(ProductosInterfaz rp, VistaInterfaz m) {
        this.rp = rp;
        this.m = m;
    }
    
    
    public void setListaProductos(ArrayList<Producto> listaProductos){
        this.listaProductos = listaProductos;
    }
    
    public Producto agregar(Producto p){
    return rp.save(p);
    }
    
        public Producto actualizar (Producto p){
    return rp.save(p);
    }
    
    public boolean eliminar(Integer id){
    try{
        rp.deleteById(id);
        return true;
    }catch(Exception ex){
        return false;
        }
    }
    
    public ArrayList<Producto> obtenerProductos(){
        return (ArrayList<Producto>) rp.findAll();
    }
    
    public Optional<Producto> obtenerProducto(Integer id){
        return rp.findById(id);
    
    }
        public double totalInventario(){

        double total = 0;
        for (Producto producto: listaProductos){
            total += producto.getPrecio() * producto.getInventario();
        }
    return total;
    }
         public String productoPrecioMayor() {
        String nombre = listaProductos.get(0).getNombre();
        double precio = listaProductos.get(0).getPrecio();
        for (Producto p : listaProductos) {
            if (p.getPrecio() > precio) {
                nombre = p.getNombre();
                precio = p.getPrecio();
            }
        }
        return nombre;
    }
    public String productoPrecioMenor() {
        String nombre = listaProductos.get(0).getNombre();
        double precio = listaProductos.get(0).getPrecio();
        for (Producto p : listaProductos) {
            if (p.getPrecio() < precio) {
                nombre = p.getNombre();
                precio = p.getPrecio();
            }
        }
        return nombre;
    }
     public double promedioPrecios(){
        double suma = 0;
        for(Producto p: listaProductos){
            suma += p.getPrecio();
        }
        return suma / (listaProductos.size());
    }
     public void eventoAgregar(){
       String nombre = m.getNombretxt();
       String precio = m.getPreciotxt();
       String inventario = m.getInventariotxt();
       if(! nombre.equals("") && ! precio.equals("") && !inventario.equals("")){
            Producto nuevo = new Producto(nombre, Double.parseDouble(precio), Integer.parseInt(inventario));
            listaProductos.add(nuevo);
            agregar(nuevo);
            DefaultTableModel modelo = (DefaultTableModel) m.getDataBase().getModel();
            modelo.insertRow(listaProductos.size()-1, new Object[]{nuevo.getCodigo(), nuevo.getNombre(), nuevo.getPrecio(), nuevo.getInventario()});
         }else {
           Advertencia  me = new Advertencia();
           me.setVisible(true);
       }
     }
        public void eventoEliminar(){
       int filaEliminar = m.getDataBase().getSelectedRow();
       listaProductos.remove(filaEliminar);
       eliminar(listaProductos.get(filaEliminar).getCodigo());
       DefaultTableModel modelo = (DefaultTableModel) m.getDataBase().getModel(); 
       modelo.removeRow(filaEliminar);
    }
        public void abrirVentanaAct(){
            ActualizarV a = new ActualizarV();
            a.setControlador(this);
            a.setVisible(true);
            
        }
        public void eventoActualizar(ActualizarV v){
                  String nombre = v.getNombretxtV();
                  String precio = v.getPreciotxtV();
                  String inventario = v.getInventariotxtV();
               if(! nombre.equals("") && ! precio.equals("") && !inventario.equals("")){
                 int filaActualizar = m.getDataBase().getSelectedRow();
                     DefaultTableModel modelo = (DefaultTableModel) m.getDataBase().getModel();
                     listaProductos.get(filaActualizar).setInventario(Integer.parseInt(inventario));
                     listaProductos.get(filaActualizar).setNombre(nombre);
                     listaProductos.get(filaActualizar).setPrecio(Double.parseDouble(precio));
                     actualizar(listaProductos.get(filaActualizar));
                     modelo.setValueAt(nombre,filaActualizar , 0);
                     modelo.setValueAt(Double.parseDouble(precio), filaActualizar, 1);
                     modelo.setValueAt(Integer.parseInt(inventario),filaActualizar, 2);
            }else {
             Advertencia  me = new Advertencia();
             me.setVisible(true);
        }
       }
                  public void eventoInforme(){
                  InformeV i = new InformeV();
                  i.setVisible(true);
                  i.setInventario(i.getInventario()+totalInventario());
                  i.setMayor(i.getMayor()+productoPrecioMayor());
                  i.setMenor(i.getMenor()+productoPrecioMenor());
                  i.setPromedio(i.getPromedio()+String.format("%.1f",promedioPrecios()));
                  
                  }                 
public void inicializaTabla(){
      DefaultTableModel modelo = (DefaultTableModel) m.getDataBase().getModel(); 
      int ind = 0;
      for(Producto p : listaProductos){
        modelo.insertRow(ind,new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio(), p.getInventario()});
        ind+=1;
      }
   }
}

 