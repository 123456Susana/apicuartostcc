package com.tcc.apicuartostcc.servicios.Implementaciones;

import com.tcc.apicuartostcc.entidades.Mercancia;
import com.tcc.apicuartostcc.entidades.Zona;
import com.tcc.apicuartostcc.repositorios.Mercanciarepositorio;
import com.tcc.apicuartostcc.repositorios.Zonarepositorio;
import com.tcc.apicuartostcc.servicios.ServicioGenerico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MercanciaServicioImp implements ServicioGenerico<Mercancia> {

    @Autowired
    Mercanciarepositorio mercanciasrepositorio;

    @Autowired
    Zonarepositorio zonarepositorio;

    @Override
    public List<Mercancia> buscarTodos() throws Exception {
        try{
            List<Mercancia> mercancias= mercanciasrepositorio.findAll();
            return mercancias;
        }catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }

    @Override
    public Mercancia buscarPorId(Integer id) throws Exception {
        try{
            Optional<Mercancia> mercancia=mercanciasrepositorio.findById(id);
            return mercancia.get();
        }catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }

    @Override
    public Mercancia registrar(Mercancia tabla) throws Exception {
        try{
            Optional<Zona> zonaAsociadaMercancia=zonarepositorio.findById(tabla.getZona().getId());
            Double capacidadZona=zonaAsociadaMercancia.get().getDisponible();
            Double capacidadOcupadaMercancia= tabla.getVolumen();
            Double capacidadRestante=capacidadZona-capacidadOcupadaMercancia;

            if (capacidadRestante>=0){
                zonaAsociadaMercancia.get().setDisponible(capacidadRestante);
                zonarepositorio.save(zonaAsociadaMercancia.get());
                tabla=mercanciasrepositorio.save(tabla);
                return tabla;
            }else{
                throw new Exception("No tenemos capacidad para esta mercancia");
            }
        }catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }

    @Override
    public Mercancia actualizar(Integer id, Mercancia tabla) throws Exception {
        try{
            Optional <Mercancia> mercanciaBuscada=mercanciasrepositorio.findById(id);
            Mercancia mercancia=mercanciaBuscada.get();
            mercancia=mercanciasrepositorio.save(tabla);
            return mercancia;
        }catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }

    @Override
    public Boolean borrar(Integer id) throws Exception {
        try{
            if (mercanciasrepositorio.existsById(id)){
                Optional<Mercancia> mercanciaARetirar=mercanciasrepositorio.findById(id);
                Optional<Zona> zonaAsociada=zonarepositorio.findById(mercanciaARetirar.get().getZona().getId());

                Double capacidadOcupadaMercancia=mercanciaARetirar.get().getVolumen();
                Double capacidadDisponibleZona=zonaAsociada.get().getDisponible();
                Double capacidadLiberada=capacidadDisponibleZona+capacidadOcupadaMercancia;

                zonaAsociada.get().setDisponible(capacidadLiberada);
                zonarepositorio.save(zonaAsociada.get());
                mercanciasrepositorio.deleteById(id);

                return true;

            }else {
                throw new Exception();
            }
        }catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }
}
