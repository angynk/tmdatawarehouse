package com.datawarehouse.model.servicios;

import com.datawarehouse.controller.mapeo.ExpedicionesDEF;
import com.datawarehouse.model.dao.BusDao;
import com.datawarehouse.model.dao.BusRegistroDao;
import com.datawarehouse.model.dao.ExpedicionesDao;
import com.datawarehouse.model.dao.OperadorDao;
import com.datawarehouse.model.entity.*;
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
import java.util.Date;
import java.util.List;

@Service
public class CargaExpedicionesServicio {

    @Autowired
    private BusDao busDao;

    @Autowired
    private BusRegistroDao busRegistroDao;

    @Autowired
    private OperadorDao operadorDao;

    private Operador operadorDefecto;

    @Autowired
    private ExpedicionesDao expedicionesDao;




    private void guardarExpedicion(String[] line, BusRegistro busRegistro) {
        Expediciones expediciones = new Expediciones();
        expediciones.setBusRegistro(busRegistro);
        expediciones.setEvento(line[ExpedicionesDEF.EVENTO]);
        expediciones.setTipo(line[ExpedicionesDEF.TIPO]);
        expediciones.setLinea(line[ExpedicionesDEF.LINEA]);
        expediciones.setInicio(Util.convertirTime(line[ExpedicionesDEF.HORA_INICIO]));
        expediciones.setFin(Util.convertirTime(line[ExpedicionesDEF.HORA_FIN]));
        expediciones.setDuracion(Util.convertirTime(line[ExpedicionesDEF.DURACION]));
        expediciones.setPuntoInicio(Util.formatoPuntos(line[ExpedicionesDEF.PUNTO_INICIO]));
        expediciones.setPuntoFin(Util.formatoPuntos(line[ExpedicionesDEF.PUNTO_FIN]));
        expediciones.setKilometros(Util.formatoKilometros(line[ExpedicionesDEF.KILOMETROS]));
        expedicionesDao.addExpedicion(expediciones);
    }

    private BusRegistro guardarBusRegistro(Programacion programacion, String numBus) {
        Integer numeroBus = Util.convertirANumero(numBus);
        if(numeroBus!=0){
            //1. Validar el bus
            Bus bus = encontrarBus(numeroBus);
            //2. Validar registroBus
            BusRegistro busRegistro = encontrarBusRegistro(bus,programacion);
            return busRegistro;
        }

        return null;

    }

    private BusRegistro encontrarBusRegistro(Bus bus,Programacion programacion) {
        BusRegistro busRegistro = busRegistroDao.encontrarBusRegistro(bus,programacion);
        if(busRegistro== null){
            busRegistro = new BusRegistro();
            busRegistro.setBus(bus);
            busRegistro.setOperador(operadorDefecto);
//            busRegistro.setProgramacion(programacion);
            busRegistroDao.addBusRegistro(busRegistro);
        }
        return busRegistro;
    }

    private Bus encontrarBus(Integer numeroBus) {
        Bus bus = busDao.encontrarBus(numeroBus);
//        if(bus== null){
//            bus = new Bus(numeroBus,new Date());
//            busDao.addBus(bus);
//        }

        return bus;
    }


    public String incluirFilaArchivo(String nombre, Programacion nuevaProgramacion, String modo,String numeroCuadro) {
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
                if(list.size()>16){
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
                listaFinal.add(nuevaProgramacion.getIdentificador()); // Add the new element here
                listaFinal.add(numeroCuadro); // Add the new element here
                listaFinal.add(modo); // Add the new element here
                entries =  listaFinal.toArray(new String[listaFinal.size()]);
                writer.writeNext(entries);
            }
            writer.close();
        } catch (IOException e) {
        }
        return csvFileOut;
    }

    public void cargarArchivoExpediciones(String nombre) throws Exception {
        expedicionesDao.cargarArchivoExpediciones(nombre);
    }

    public void eliminarDatosTemporales(Programacion nuevaProgramacion) {
        expedicionesDao.eliminarDatos(nuevaProgramacion);
    }

    public String incluirFilaArchivoPuntoComa(String nombre, Programacion nuevaProgramacion, String modo,String numeroCuadro) {
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
                list.add(nuevaProgramacion.getIdentificador()); // Add the new element here
                list.add(numeroCuadro); // Add the new element here
                list.add(modo); // Add the new element here
                entries =  list.toArray(new String[list.size()]);
                writer.writeNext(entries);
            }
            writer.close();
        } catch (IOException e) {
        }
        return csvFileOut;
    }

    public void getExpedicionesCuadro(Cuadro cuadroObj, String consultaPath) {
        expedicionesDao.getExpedicionesCuadro(cuadroObj,consultaPath);
    }
}
