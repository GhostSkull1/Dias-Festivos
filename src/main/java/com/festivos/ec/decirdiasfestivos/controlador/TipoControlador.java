package com.festivos.ec.decirdiasfestivos.controlador;

import com.festivos.ec.decirdiasfestivos.core.services.TipoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TipoControlador {

    TipoServicio tipoServicio;

    @Autowired
    public TipoControlador(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    @GetMapping("/esFestivo")
    public String getAll(@RequestParam("ano") int ano,
                             @RequestParam("mes") int mes,
                             @RequestParam("dia") int dia) {
        return tipoServicio.esFestivo(ano, dia, mes);
    }
}
