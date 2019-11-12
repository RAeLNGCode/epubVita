package modelo;

public class config {
    private String ruta;
    private int direccion;
    private int calidad;
    private boolean borrarTemp;

    public config() {
        this.ruta = ".";
        this.direccion = 0;
        this.calidad = 1;
        this.borrarTemp=true;
    }

    public config(String ruta, String direccion, String calidad,String borrarTemp){
        this.ruta = ruta;
        this.direccion = Integer.parseInt(direccion);
        this.calidad = Integer.parseInt(calidad);
        this.borrarTemp=borrarTemp.equals("true");
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDirecString() {
        return String.valueOf(direccion);
    }
    
    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int Direccion) {
        this.direccion = Direccion;
    }

    public String getCaliString() {
        return String.valueOf(calidad);
    }
    
    public int getCalidad() {
        return calidad;
    }

    public void setCalidad(int Calidad) {
        this.calidad = Calidad;
    }

    public String getBorrarTemp() {
        if(borrarTemp){
            return "true";
        }else{
            return "false";
        }
    }
    
    public boolean isBorrarTemp() {
        return borrarTemp;
    }

    public void setBorrarTemp(boolean borrarTemp) {
        this.borrarTemp = borrarTemp;
    }

    
    
}
