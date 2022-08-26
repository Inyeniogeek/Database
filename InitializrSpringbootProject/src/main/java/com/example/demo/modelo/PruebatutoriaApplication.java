package com.example.demo.modelo;

import com.example.demo.controlador.ProductoControlador;
import com.example.demo.vista.VistaInterfaz;
import static java.lang.reflect.Array.set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PruebatutoriaApplication {
    
    @Autowired
    ProductosInterfaz rep;
	public static void main(String[] args) {
		//SpringApplication.run(PruebatutoriaApplication.class, args);
            SpringApplicationBuilder builder = new SpringApplicationBuilder(PruebatutoriaApplication.class); 
            builder.headless(false);
            ConfigurableApplicationContext context = builder.run(args);
        }
        @Bean
        ApplicationRunner applicationRunner(){
            return args -> {
                final Log logger = LogFactory.getLog(getClass());
                VistaInterfaz m = new VistaInterfaz();
                m.setVisible(true);
                ProductoControlador controlador = new ProductoControlador(rep,m);
                m.setControlador(controlador);
                controlador.setListaProductos(controlador.obtenerProductos());
                controlador.inicializaTabla();
                //System.out.println(controlador.productoPrecioMayor());
        };
    }
}
