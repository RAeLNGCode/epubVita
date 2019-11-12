package modelo;

public class libro {

    private int id;
    private String nombre;
    private String codigo;
    private int numero;
    //opcionales
    private String descripcion;
    private String creador;
    private String publicador;
    private String direccionLectura;
    private String idioma;
    private String ruta;
    private boolean leido;

    public libro() {
        ruta = "ux0:/book/bookModelData/guest/";
    }

    public libro(int id, String nombre, String codigo, int numero, String descripcion, String creador, String publicador, String ruta, boolean leido) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.numero = numero;
        this.descripcion = descripcion;
        this.creador = creador;
        this.publicador = publicador;
        this.ruta = ruta;
        this.leido = leido;
    }

    public libro(int id, String nombre, String codigo, int numero, String descripcion, String creador, String publicador, String direccionLectura, String idioma, String ruta, boolean leido) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.numero = numero;
        this.descripcion = descripcion;
        this.creador = creador;
        this.publicador = publicador;
        this.direccionLectura = direccionLectura;
        this.idioma = idioma;
        this.ruta = ruta;
        this.leido = leido;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void genCodigo() {
        String tempNom = "0000";
        tempNom = tempNom.substring(String.valueOf(numero).length(), 4);
        tempNom = nombre.replaceAll(" ", "") + tempNom + numero;
        this.codigo = tempNom.toUpperCase();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void aumentarNumero() {
        this.numero++;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getPublicador() {
        return publicador;
    }

    public void setPublicador(String publicador) {
        this.publicador = publicador;
    }

    public String getDireccionLectura() {
        return direccionLectura;
    }

    public void setDireccionLectura(String direccionLectura) {
        this.direccionLectura = direccionLectura;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public libro clonar(){
        return new libro(id, nombre, codigo, numero, descripcion, creador, publicador, direccionLectura, idioma, ruta, leido);
    }
}
