package com.datawarehouse.model.servicios;

import com.datawarehouse.controller.mapeo.ExpedicionesDEF;
import com.datawarehouse.model.dao.BusDao;
import com.datawarehouse.model.dao.BusRegistroDao;
import com.datawarehouse.model.dao.ExpedicionesDao;
import com.datawarehouse.model.dao.OperadorDao;
import com.datawarehouse.model.entity.*;
import com.datawarehouse.view.util.*;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
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


    public List<LogDatos> cargarArchivoExpediciones(Archivos archivo, List<LogDatos> logDatos, Programacion programacion) {
        operadorDefecto = encontrarOperadorDefault();
        if(archivo.getTipo().equals(FormatoArchivo.CSV_COMMA)){
                logDatos = leerCSVFile(",",archivo,logDatos,programacion);
        }else if (archivo.getTipo().equals(FormatoArchivo.CSV_PUNTO_COMMA)){

        }else if (archivo.getTipo().equals(FormatoArchivo.TXT)){

        }else if (archivo.getTipo().equals(FormatoArchivo.XLS)){

        }
        return logDatos;
    }

    private Operador encontrarOperadorDefault() {
        return operadorDao.encontrarOperadorDefault();
    }

    private List<LogDatos> leerCSVFile(String separador, Archivos archivo, List<LogDatos> logDatos, Programacion programacion) {
        String csvFile = PathFiles.PATH+"/"+archivo.getNombre();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFile));
            String[] line = reader.readNext();
            while ((line = reader.readNext()) != null) {

                //GUARDAR BUS REGISTRO
                BusRegistro busRegistro = guardarBusRegistro(programacion,line[ExpedicionesDEF.BUS]);
                //GUARDAR INFO EXPEDICIONES
                guardarExpedicion(line,busRegistro);
            }
        } catch (IOException e) {
            logDatos.add(new LogDatos(e.getMessage(),TipoLog.ERROR));
        }
        return logDatos;
    }

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
        //1. Validar el bus
         Bus bus = encontrarBus(numeroBus);
        //2. Validar registroBus
        BusRegistro busRegistro = encontrarBusRegistro(bus,programacion);
        return busRegistro;
    }

    private BusRegistro encontrarBusRegistro(Bus bus,Programacion programacion) {
        BusRegistro busRegistro = busRegistroDao.encontrarBusRegistro(bus,programacion);
        if(busRegistro== null){
            busRegistro = new BusRegistro();
            busRegistro.setBus(bus);
            busRegistro.setOperador(operadorDefecto);
            busRegistro.setProgramacion(programacion);
            busRegistroDao.addBusRegistro(busRegistro);
        }
        return busRegistro;
    }

    private Bus encontrarBus(Integer numeroBus) {
        Bus bus = busDao.encontrarBus(numeroBus);
        if(bus== null){
            bus = new Bus(numeroBus,new Date());
            busDao.addBus(bus);
        }

        return bus;
    }


}
