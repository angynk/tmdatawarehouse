package com.datawarehouse.view.util;

/**
 * Created by user on 09/05/2018.
 */
public class ConsultaBasicaDEF {

    /*REPORTE EXPEDICIONES CUADRO*/
    public static int EXP_EVENTO = 0;
    public static int EXP_TIPO = 1;
    public static int EXP_INICIO = 2;
    public static int EXP_DE = 3;
    public static int EXP_INICIO_TX = 4;
    public static int EXP_FIN = 5;
    public static int EXP_A = 6;
    public static int EXP_FIN_TX = 7;
    public static int EXP_DUR = 8;
    public static int EXP_BUS = 9;
    public static int EXP_LINEA = 10;
    public static int EXP_KM = 11;
    public static int EXP_INFERIDO = 12;
    public static int EXP_ID = 13;
    public static int EXP_FREC = 14;
    public static int EXP_SERBUS = 15;
    public static int EXP_DESA = 16;
    public static int EXP_DESB = 17;

    public static String EXP_TX_EVENTO = "Evento";
    public static String EXP_TX_TIPO = "Tipo";
    public static String EXP_TX_INICIO = "Inicio";
    public static String EXP_TX_DE = "De P";
    public static String EXP_TX_DE_TX = "De Punto";
    public static String EXP_TX_FIN = "Fin";
    public static String EXP_TX_A = "A P";
    public static String EXP_TX_A_TX = "A Punto";
    public static String EXP_TX_DUR = "Duración";
    public static String EXP_TX_BUS = "Bus";
    public static String EXP_TX_LINEA = "Línea";
    public static String EXP_TX_KM = "KM";
    public static String EXP_TX_INFERIDO = "V.Inferido";
    public static String EXP_TX_ID = "Id";
    public static String EXP_TX_FREC = "Frec";
    public static String EXP_TX_SERBUS = "Serbus";
    public static String EXP_TX_DESA = "Desviación Duración";
    public static String EXP_TX_DESB = "Desviación Frecuencia";


    /*REPORTE BUSES CUADRO*/
    public static int BUS_BUS = 0;
    public static int BUS_INICIO = 1;
    public static int BUS_FIN = 2;
    public static int BUS_JOR = 3;
    public static int BUS_CIR = 4;
    public static int BUS_DE = 5;
    public static int BUS_A = 6;
    public static int BUS_LDE = 7;
    public static int BUS_LA = 8;
    public static int BUS_KM = 9;
    public static int BUS_TIPO = 10;
    public static int BUS_BUSBD = 11;
    public static int BUS_OPERADOR = 12;

    public static String BUS_TX_BUS = "Bus";
    public static String BUS_TX_INICIO = "Inicio";
    public static String BUS_TX_FIN = "Fin";
    public static String BUS_TX_JOR = "Jor";
    public static String BUS_TX_CIR = "Circ";
    public static String BUS_TX_DE = "De";
    public static String BUS_TX_A = "A";
    public static String BUS_TX_LDE = "L.De";
    public static String BUS_TX_LA = "L.A";
    public static String BUS_TX_KM = "Km";
    public static String BUS_TX_TIPO = "Tipo";
    public static String BUS_TX_BUSBD = "Bus BD";
    public static String BUS_TX_OPERADOR = "Operador";


    /*REPORTE IPH*/
    public static int IPH_JORNADA_TIPO = 0;
    public static int IPH_TIPO_DIA = 1;
    public static int IPH_OPERADOR = 2;
    public static int IPH_INSTANTE = 3;
    public static int IPH_SERBUS = 4;
    public static int IPH_EVENTO = 5;
    public static int IPH_LINEA = 6;
    public static int IPH_COCHE = 7;
    public static int IPH_SUBLINEA = 8;
    public static int IPH_RUTA = 9;
    public static int IPH_PUNTO = 10;
    public static int IPH_TIPO_NODO = 11;
    public static int IPH_VIAJE = 12;

    public static String IPH_TX_JORNADA_TIPO = "JornadaTipo";
    public static String IPH_TX_TIPO_DIA = "TipoDia";
    public static String IPH_TX_OPERADOR = "Operador";
    public static String IPH_TX_INSTANTE = "Instante";
    public static String IPH_TX_SERBUS = "Serbus";
    public static String IPH_TX_EVENTO = "Evento";
    public static String IPH_TX_LINEA = "Linea";
    public static String IPH_TX_COCHE = "Coche";
    public static String IPH_TX_SUBLINEA = "Sublinea";
    public static String IPH_TX_RUTA = "Ruta";
    public static String IPH_TX_PUNTO = "Punto";
    public static String IPH_TX_TIPO_NODO = "TipoNodo";
    public static String IPH_TX_VIAJE = "Viaje";

    /*REPORTE DISTRIBUCIONES*/
    public static int DIS_SERBUS = 0;
    public static int DIS_OPERADOR = 1;
    public static int DIS_PATIO_INICIO = 2;
    public static int DIS_PATIO_FIN = 3;
    public static int DIS_PATIO_VALLE = 4;
    public static int DIS_TIPOLOGIA = 5;
    public static int DIS_PUNTO_INICIO = 6;
    public static int DIS_PUNTO_FIN = 7;
    public static int DIS_DISTANCIA = 8;
    public static int DIS_VACIO_INTERNO = 9;
    public static int DIS_KM_VACIO_VALLE = 10;
    public static int DIS_VACIO_EXTERNO = 11;
    public static int DIS_VACIO_TOTAL = 12;
    public static int DIS_PUNTO_INICIO_VALLE = 13;
    public static int DIS_PUNTO_FIN_VALLE = 14;
    public static int DIS_DUR_VALLE = 15;
    public static int DIS_RESERVA = 16;

    public static String DIS_TX_SERBUS = "Serbus";
    public static String DIS_TX_OPERADOR = "Operador";
    public static String DIS_TX_PATIO_INICIO = "Patio Inicio";
    public static String DIS_TX_PATIO_FIN = "Patio Fin";
    public static String DIS_TX_PATIO_VALLE = "Patio Valle";
    public static String DIS_TX_TIPOLOGIA = "Tipologia";
    public static String DIS_TX_PUNTO_INICIO = "Punto Inicio";
    public static String DIS_TX_PUNTO_FIN = "Punto Fin";
    public static String DIS_TX_DISTANCIA = "Distancia";
    public static String DIS_TX_VACIO_INTERNO = "Vacio Interno";
    public static String DIS_TX_KM_VACIO_VALLE = "Km Vacio Valle";
    public static String DIS_TX_VACIO_EXTERNO = "Vacio Externo";
    public static String DIS_TX_VACIO_TOTAL = "Vacio Total";
    public static String DIS_TX_PUNTO_INICIO_VALLE = "Punto Inicio Valle";
    public static String DIS_TX_PUNTO_FIN_VALLE = "Punto Fin Valle";
    public static String DIS_TX_DUR_VALLE = "Duración Valle";
    public static String DIS_TX_RESERVA = "Reserva";



}
