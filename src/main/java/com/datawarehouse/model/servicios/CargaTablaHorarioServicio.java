package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.ArchivosDao;
import com.datawarehouse.model.dao.TablaHorarioDao;
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
public class CargaTablaHorarioServicio {

    @Autowired
    private TablaHorarioDao tablaHorarioDao;

    @Autowired
    private ArchivosDao archivosDao;


    public List<LogDatos> agregarInformacionTablaHorario(Programacion programacion, Archivos archivo, List<LogDatos> logDatos, Cuadro cuadro) {
        String nombre = archivo.getNombre();
        if(archivo.getTipo().equals(FormatoArchivo.CSV_COMMA)){
            nombre = incluirFilasArchivo(programacion,nombre,',',cuadro);
        }else if(archivo.getTipo().equals(FormatoArchivo.CSV_PUNTO_COMMA)){
            nombre = incluirFilasArchivo(programacion,nombre,';',cuadro);
        }
        try {
            tablaHorarioDao.cargarArchivoTablaHorario(nombre);
            tablaHorarioDao.eliminarDatos(programacion);
            archivosDao.addArchivos(archivo);
        } catch (Exception e) {
            logDatos.add(new LogDatos(e.getMessage(), TipoLog.ERROR));
        }

        return logDatos;
    }

    private String incluirFilasArchivo(Programacion programacion, String nombre, char separador, Cuadro cuadro) {
        String csvFile = PathFiles.PATH+"/"+nombre;
        String csvFileOut = PathFiles.PATH+"/out_"+nombre;
        CSVWriter writer = null;
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile),separador);
            writer = new CSVWriter(new FileWriter(csvFileOut), ',');
            String[] entries = null;
            while ((entries = reader.readNext()) != null) {
                ArrayList<String> list = new ArrayList(Arrays.asList(entries));
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

    public void eliminarDatosDeCuadro(Cuadro cuadro) {
        tablaHorarioDao.eliminarDatosDeCuadro(cuadro);
    }
}
