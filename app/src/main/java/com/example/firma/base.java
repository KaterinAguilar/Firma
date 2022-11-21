package com.example.firma;

public class base {

    public static final String NameDatabase = "PM2_Ej4";

    public static final String TablaFirmas = "FirmaDigital";

    public static final String id = "id";
    public static final String descripcion = "descripcion";
    public static final String firma = "firma";

    public static final String createTableFirmas = "CREATE TABLE "+base.TablaFirmas+
            " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "descripcion TEXT,"+
            "firma BLOB)";

    public static final String GetFirmas = "SELECT * FROM "+base.TablaFirmas;

    public static final String DropTableFirmas = "DROP TABLE IF EXISTS FirmaDigital";

    public static final String DeleteRegistro = "DELETE FROM "+TablaFirmas;

}

