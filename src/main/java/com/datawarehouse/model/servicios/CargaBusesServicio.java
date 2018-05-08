package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.ArchivosDao;
import com.datawarehouse.model.dao.BusesDao;
import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.view.util.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CargaBusesServicio {

    @Autowired
    private BusesDao busesDao;

    @Autowired
    private ArchivosDao archivosDao;

    public CargaBusesServicio() {
    }


    public List<LogDatos> agregarInformacionBuses(Programacion programacion, Archivos archivo, List<LogDatos> logDatos, Cuadro cuadro) {
        String nombre = archivo.getNombre();
        if(archivo.getTipo().equals(FormatoArchivo.CSV_COMMA)){
            nombre = incluirFilasArchivoComma(programacion,nombre,cuadro);
        }else if(archivo.getTipo().equals(FormatoArchivo.CSV_PUNTO_COMMA)){
            nombre = incluirFilasArchivoPuntoComma(programacion,nombre,cuadro);
        }
        try {
            busesDao.cargarArchivoBuses(nombre);
            busesDao.eliminarDatosBuses(programacion);
            archivosDao.addArchivos(archivo);
        } catch (Exception e) {
            logDatos.add(new LogDatos(e.getMessage(), TipoLog.ERROR));
        }

        return logDatos;
    }

    private String incluirFilasArchivoPuntoComma(Programacion programacion, String nombre, Cuadro cuadro) {
        String csvFile = PathFiles.PATH+"/"+nombre;
        String csvFileOut = PathFiles.PATH+"/out_"+nombre;
        CSVWriter writer = null;
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile),';');
            writer = new CSVWriter(new FileWriter(csvFileOut), ',');
            String[] entries = null;
            while ((entries = reader.readNext()) != null) {
                ArrayList<String> list = new ArrayList(Arrays.asList(entries));
                list.set(9,list.get(9).replace(",","."));
                if(list.size()>12){
                    list.remove(list.size()-1);
                }
                list.add(programacion.getIdentificador());
                list.add(programacion.getModo());
                list.add(cuadro.getNumero());
                list.add(Util.convertirAString(programacion.getFecha()));
                entries =  list.toArray(new String[list.size()]);
                writer.writeNext(entries);
            }
            writer.close();
        } catch (IOException e) {
        }
        return csvFileOut;
    }

    private String incluirFilasArchivoComma(Programacion programacion, String nombre, Cuadro cuadro) {
        String csvFile = PathFiles.PATH+"/"+nombre;
        String csvFileOut = PathFiles.PATH+"/out_"+nombre;
        CSVWriter writer = null;
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile),',');
            writer = new CSVWriter(new FileWriter(csvFileOut), ',');
            String[] entries = null;
            while ((entries = reader.readNext()) != null) {
                ArrayList<String> list = new ArrayList(Arrays.asList(entries));
                ArrayList<String> listaFinal = new ArrayList<>();
                if(list.size()>12){
                    for(int x=0;x<list.size();x++){
                        if(x == 9 ){
                            listaFinal.add(list.get(x)+"."+list.get(x+1));
                            x++;
                        }else{
                            listaFinal.add(list.get(x));
                        }

                    }
                }else{
                    listaFinal = list;
                }
                listaFinal.add(programacion.getIdentificador());
                listaFinal.add(programacion.getModo());
                list.add(cuadro.getNumero());
                list.add(Util.convertirAString(programacion.getFecha()));
                entries =  listaFinal.toArray(new String[listaFinal.size()]);
                writer.writeNext(entries);
            }
            writer.close();
        } catch (IOException e) {
        }
        return csvFileOut;
    }


    public void eliminarDatosDeCuadro(Cuadro cuadro) {
        busesDao.eliminarDatosDeCuadro(cuadro);
    }
}
