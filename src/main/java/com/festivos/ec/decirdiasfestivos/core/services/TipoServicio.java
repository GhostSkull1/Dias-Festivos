package com.festivos.ec.decirdiasfestivos.core.services;

import com.festivos.ec.decirdiasfestivos.core.repositorio.FestivoRepositorio;
import com.festivos.ec.decirdiasfestivos.core.repositorio.TipoRepositorio;
import com.festivos.ec.decirdiasfestivos.dominio.entidades.Festivos;
import com.festivos.ec.decirdiasfestivos.dominio.entidades.Tipo;
import com.festivos.ec.decirdiasfestivos.dominio.entidades.dto.ResponseCalculate;
import com.festivos.ec.decirdiasfestivos.utils.HolidayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TipoServicio {

    TipoRepositorio tipoRepositorio;
    FestivoRepositorio festivoRepositorio;


    @Autowired
    public TipoServicio(TipoRepositorio tipoRepositorio, FestivoRepositorio festivoRepositorio) {
        this.tipoRepositorio = tipoRepositorio;
        this.festivoRepositorio = festivoRepositorio;
    }


    public String esFestivo(int ano, int dia, int mes) {

        List<Festivos> response = getList();

        Festivos responseFes = response.stream()
                .filter(festivos -> festivos.getDia().equals(dia))
                .filter(festivos -> festivos.getMes().equals(mes))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(responseFes)) return "Si se pudo, es dia festivo";
        if (obtenerFestivoMovido(ano, dia, mes)) return "Si se pudo, es dia festivo";


        return "NO se pudo";
    }

    private List<Festivos> getList() { return festivoRepositorio.findAll(); }

    private boolean obtenerFestivoMovido(int ano, int dia, int mes) {
        HolidayUtil holidayUtil = new HolidayUtil(ano);
        List<ResponseCalculate> list = holidayUtil.getHolidays().stream()
                .filter(festivos -> Integer.valueOf(festivos.getDia()).equals(dia))
                .filter(festivos -> Integer.valueOf(festivos.getMes()).equals(mes - 1))
                .toList();

        return !CollectionUtils.isEmpty(list);
    }

}
