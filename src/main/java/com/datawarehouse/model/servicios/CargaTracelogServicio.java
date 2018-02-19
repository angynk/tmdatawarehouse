package com.datawarehouse.model.servicios;

import com.datawarehouse.model.dao.ArchivosDao;
import com.datawarehouse.model.dao.TracelogDao;
import com.datawarehouse.model.entity.Archivos;
import com.datawarehouse.model.entity.Cuadro;
import com.datawarehouse.model.entity.Programacion;
import com.datawarehouse.view.util.FormatoArchivo;
import com.datawarehouse.view.util.LogDatos;
import com.datawarehouse.view.util.PathFiles;
import com.datawarehouse.view.util.TipoLog;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CargaTracelogServicio {

    @Autowired
    TracelogDao tracelogDao;

    @Autowired
    private ArchivosDao archivosDao;

    public List<LogDatos> agregarDatosTracelog(Programacion programacion, Archivos archivo, List<LogDatos> logDatos) {
        String nombre = archivo.getNombre();
        if(archivo.getTipo().equals(FormatoArchivo.TXT)){
            nombre = incluirFilasArchivo(programacion,nombre,archivo.getCuadro());
        }

        try {
            tracelogDao.cargarArchivoTracelog(nombre);
            archivosDao.addArchivos(archivo);
        } catch (Exception e) {
            logDatos.add(new LogDatos(e.getMessage(), TipoLog.ERROR));
        }

        return logDatos;
    }

    private String incluirFilasArchivo(Programacion programacion, String nombre, Cuadro cuadro) {
            BufferedReader br = null;
            String line = "";
            String csvFile = PathFiles.PATH+"/"+nombre;
            nombre = nombre.replace(".txt",".csv");
            String csvFileOut = PathFiles.PATH+"/out_"+nombre;
            CSVWriter writer = null;
            CSVReader reader = null;
            try {
                writer = new CSVWriter(new FileWriter(csvFileOut), ',');
                br = new BufferedReader(new FileReader(csvFile));
                br.readLine(); // Leer encabezados
                while ((line = br.readLine()) != null){
                    line = br.readLine();
                    String [] valores = line.split("\t");
                    ArrayList<String> list = new ArrayList(Arrays.asList(valores));
                    list.set(3,list.get(3).replace(",","-"));
                    list.remove(23);
                    for(int k=0;k<8;k++){
                        list.remove(14);
                    }
                    list.add(programacion.getIdentificador());
                    list.add(cuadro.getNumero());
                    valores =  list.toArray(new String[list.size()]);
                    writer.writeNext(valores);

                }

                writer.close();
            } catch (IOException e) {
            }
            return csvFileOut;

    }
}
