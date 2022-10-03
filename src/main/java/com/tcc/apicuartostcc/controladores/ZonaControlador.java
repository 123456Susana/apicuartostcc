package com.tcc.apicuartostcc.controladores;

import com.tcc.apicuartostcc.entidades.Zona;
import com.tcc.apicuartostcc.servicios.Implementaciones.ZonaServicioImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tcc/zonas")
public class ZonaControlador {

    @Autowired
    ZonaServicioImp zonaServicio;

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Zona zona){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(zonaServicio.registrar(zona));
        }catch (Exception error){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje:Revise su peticion}");
        }
    }

    @GetMapping
    public ResponseEntity<?> buscarTodos(){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(zonaServicio.buscarTodos());
        }catch (Exception error){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{mensaje:Datos no encontrados}");

        }
    }



}
