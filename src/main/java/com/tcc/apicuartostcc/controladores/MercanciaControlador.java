package com.tcc.apicuartostcc.controladores;


import com.tcc.apicuartostcc.entidades.Mercancia;
import com.tcc.apicuartostcc.entidades.Zona;
import com.tcc.apicuartostcc.servicios.Implementaciones.MercanciaServicioImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tcc/mercancias")
public class MercanciaControlador {

    @Autowired
    MercanciaServicioImp mercanciaServicio;

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Mercancia mercancia){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaServicio.registrar(mercancia));
        }catch (Exception error){
            String mensaje="{\"error\":\"Error revise: "+error+"\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(mensaje);
        }
    }

    @GetMapping
    public ResponseEntity<?> buscarTodos(){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaServicio.buscarTodos());
        }catch (Exception error){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje:Datos no encontrados}");

        }
    }
    @GetMapping("/{iup}")
    public ResponseEntity<?> buscarPorIUP(@PathVariable Integer iup){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaServicio.buscarPorId(iup));
        }catch (Exception error){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje:Datos no encontrados}");

        }
    }
    @PutMapping("/{iup}")
    public ResponseEntity<?> actualizar(@PathVariable Integer iup,@RequestBody Mercancia mercancia){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaServicio.actualizar(iup,mercancia));
        }catch (Exception error){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje:No se pudo actualizar, no hay datos}");

        }
    }
    @DeleteMapping("/{iup}")
    public ResponseEntity<?> borrar(@PathVariable Integer iup){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mercanciaServicio.borrar(iup));
        }catch (Exception error){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje:No se pudo eliminar, no hay datos}");

        }
    }
}
