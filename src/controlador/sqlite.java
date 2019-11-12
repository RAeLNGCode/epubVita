package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.libro;

public class sqlite {

    String ruta;
    Connection conexion;

    public sqlite(String ruta) {
        this.ruta = ruta;
    }

    public void conectar() {
        try {
            String dburl = "jdbc:sqlite:" + ruta;
            conexion = DriverManager.getConnection(dburl);
        } catch (SQLException ex) {
            Logger.getLogger(sqlite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void desconectar(){
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(sqlite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarLibro(libro libro){
        try {
            conectar();
            PreparedStatement st = conexion.prepareStatement("INSERT INTO books(`ID`,`UserId`,`EBookId`,`SonyBookId`,`Title`,`TitleForDataSorting`,`Author`,`AuthorForDataSorting`,`DescriptionExcerpt`,`Publisher`,`PublisherForDataSorting`,`SeriesName`,`SeriesOrdinal`,`AccuralMethod`,`ContentFilePath`) VALUES "
                                                                             + "(NULL,'guest',?,?,?,?,?,?,?,?,?,?,?,'preload',?);");
            
            st.setString(1, libro.getCodigo());
            st.setString(2, libro.getCodigo());
            st.setString(3, libro.getNombre()+" "+libro.getNumero());
            st.setString(4, libro.getNombre()+" "+libro.getNumero());
            st.setString(5, libro.getCreador());
            st.setString(6, libro.getCreador());
            st.setString(7, libro.getDescripcion());
            st.setString(8, libro.getPublicador());
            st.setString(9, libro.getPublicador());
            st.setString(10, libro.getNombre());
            st.setInt(11, libro.getNumero());
            st.setString(12, libro.getRuta()+libro.getNombre()+" "+libro.getNumero()+".epub");
            st.execute();
            st.close();
        } catch (SQLException ex) {
            log.escribirLogSalida(ex.toString());
        }finally{
            desconectar();
        }
 
    }
    
    public ArrayList<libro> obtenerLibros(){
        ArrayList<libro> listaLibros=new ArrayList<>();
        ResultSet result = null;
        try {
            conectar();
            PreparedStatement st = conexion.prepareStatement("SELECT * FROM `books`;");
            result = st.executeQuery();
            while (result.next()){
                boolean leido=result.getString("HasRead")!=null;
                listaLibros.add(new libro(result.getInt("ID"), result.getString("SeriesName"), result.getString("EBookId"), result.getInt("SeriesOrdinal"), result.getString("DescriptionExcerpt"), result.getString("Author"), result.getString("Publisher"), result.getString("ContentFilePath"),leido));
            }
        } catch (SQLException ex) {
            log.escribirLogSalida(ex.toString());
        }finally{
            desconectar();
        }
        return listaLibros;
    }
    
    public ArrayList<libro> obtenerLibros(String nombreSerie){
        ArrayList<libro> listaLibros=new ArrayList<>();
        ResultSet result = null;
        try {
            conectar();
            PreparedStatement st = conexion.prepareStatement("SELECT * FROM `books` WHERE SeriesName=?;");
            st.setString(1, nombreSerie);
            result = st.executeQuery();
            while (result.next()){
                boolean leido=result.getString("HasRead")!=null;
                listaLibros.add(new libro(result.getInt("ID"), result.getString("SeriesName"), result.getString("EBookId"), result.getInt("SeriesOrdinal"), result.getString("DescriptionExcerpt"), result.getString("Author"), result.getString("Publisher"), result.getString("ContentFilePath"),leido));
            }
        } catch (SQLException ex) {
            log.escribirLogSalida(ex.toString());
        }finally{
            desconectar();
        }
        return listaLibros;
    }
    
    public ArrayList<String> obtenerListaNomSer(){
        ArrayList<String> listaNomSer=new ArrayList<>();
        ResultSet result = null;
        try {
            conectar();
            PreparedStatement st = conexion.prepareStatement("SELECT SeriesName FROM `books`;");
            result = st.executeQuery();
            while (result.next()) {if(listaNomSer.indexOf(result.getString("SeriesName"))==-1){
                    listaNomSer.add(result.getString("SeriesName"));
                }
            }
        } catch (SQLException ex) {
            log.escribirLogSalida(ex.toString());
        }finally{
            desconectar();
        }
        return listaNomSer;
    }
    
    public void cambiarRuta(String codigo,String rutaLibro){
        try {
            conectar();
            PreparedStatement st = conexion.prepareStatement("UPDATE `books` SET `ContentFilePath`=? WHERE EBookId=?;");
            st.setString(1, rutaLibro);
            st.setString(2, codigo);
            st.execute();
            st.close();
        } catch (SQLException ex) {
            log.escribirLogSalida(ex.toString());
        }finally{
            desconectar();
        }
    }
    
    public void modificarLibro(libro libr){
        try {
            conectar();
            PreparedStatement st = conexion.prepareStatement("UPDATE `books` SET `Title`=?,`TitleForDataSorting`=?,`Author`=?,`AuthorForDataSorting`=?,`SeriesName`=?,`SeriesOrdinal`=?,`DescriptionExcerpt`=?,`ContentFilePath`=?, `HasRead`=?  WHERE EBookId=?;");
            st.setString(1, libr.getNombre()+" "+libr.getNumero());
            st.setString(2, libr.getNombre()+" "+libr.getNumero());
            st.setString(3, libr.getCreador());
            st.setString(4, libr.getCreador());
            st.setString(5, libr.getNombre());
            st.setInt(6, libr.getNumero());
            st.setString(7, libr.getDescripcion());
            st.setString(8, libr.getRuta());
            if(libr.isLeido()){
                st.setString(9, "1");
            }else{
                st.setString(9, null);
            }
            st.setString(10, libr.getCodigo());
            st.execute();
            st.close();
        } catch (SQLException ex) {
            log.escribirLogSalida(ex.toString());
        }finally{
            desconectar();
        }
    }
    
    public void marcarNoLeido(String codigo){
        try {
            conectar();
            PreparedStatement st = conexion.prepareStatement("UPDATE `books` SET `HasRead`=null WHERE EBookId=?;");
            st.setString(1, codigo);
            st.execute();
            st.close();
        } catch (SQLException ex) {
            log.escribirLogSalida(ex.toString());
        }finally{
            desconectar();
        }
    }
    
    public void eliminarLb(String codigo){
        try {
            conectar();
            PreparedStatement st = conexion.prepareStatement("DELETE FROM `books` WHERE `EBookId`=?;");
            st.setString(1, codigo);
            st.execute();
            st.close();
        } catch (SQLException ex) {
            log.escribirLogSalida(ex.toString());
        }finally{
            desconectar();
        }
    }
}
