import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.Writer;
import javax.swing.JOptionPane;

public class Sunset {
  
    public native float sumar(float a, float b); 
    public native float restar(float a, float b); 
    public native float multiplicar(float a, float b); 
    public native float dividir(float a, float b);
    public native float comparar(float a, float b);
    public native float sin(float grado);
    public native float cos(float grado);
    public native float tan(float grado);
    public native float asin(float grado);
    public native float acos(float grado);
    public native float atan(float grado);
    

    private BufferedReader buffer;

    public Sunset(){
        buffer = new BufferedReader(new InputStreamReader(System.in));
    }

   public String leerLinea() throws IOException{
        return buffer.readLine();
    }
    
    static { 
            System.loadLibrary("sunsetIm"); 
    } 
    
    

    float paso1(float day, float month, float year){
            float N1 = (float) Math.floor(dividir(multiplicar(275.0f,month),9.0f));
            float N2 = (float) Math.floor(dividir(sumar(9.0f,month),12.0f));
            float N3 = sumar(1.0f,dividir(sumar(restar(year, multiplicar(4.0f, (float)Math.floor(dividir(year,4.0f)))), 2.0f), 3.0f));
            return restar(sumar(restar(N1, multiplicar(N2, N3)), day), 30.0f);
    }

    float paso2Salida(float lngHour, float N){
            return sumar(N, dividir(restar(6.0f, lngHour), 24.0f));
    }

    float paso2Puesta(float lngHour, float N){
            return sumar(N, dividir(restar(18.0f, lngHour), 24.0f));
    }

    float paso2Fijo(float longitude){
            return dividir(longitude, 15.0f);
    }

    //Calcula la anomalia media del sol

    float paso3(float t){
            return restar(multiplicar(0.9856f,t), 3.289f); //Retorna M
    }

    //dividir(multiplicar(var,(float)Math.PI),180.0f)

    float paso4(float M){
            return sumar(sumar(sumar(multiplicar(1.916f,(float) Math.sin(dividir(multiplicar(M,(float)Math.PI),180.0f))),multiplicar(0.020f, (float) Math.sin(dividir(multiplicar(multiplicar(2.0f,M),(float)Math.PI),180.0f)))),282.634f),M);
    }

    float paso5A(float L){
            return dividir(multiplicar(180.0f,(float)Math.atan(multiplicar(0.91764f, (float) Math.tan(dividir(multiplicar(L,(float)Math.PI),180.0f))))),(float)Math.PI); //Retorna M
            //RA potentially needs to be adjusted into the range [0,360) by adding/subtracting 360
    }

    float paso5B(float L, float RA){
            float Lquadrant = multiplicar( (float) Math.floor(dividir(L,90.0f)),90.0f);
            float RAquadrant = multiplicar( (float) Math.floor(dividir(RA,90.0f)),90.0f);
            return sumar(RA, restar(Lquadrant, RAquadrant)); // retorna el RA
    }

    float paso5C(float RA){
            return dividir(RA, 15.0f); // retorna el RA
    }

    float paso6sinDec(float L){
            return multiplicar(0.39782f, (float)Math.sin(dividir(multiplicar(L,(float)Math.PI),180.0f)));
    }

    float paso6cosDec(float sinDec){
            return (float) Math.cos((float)Math.asin(sinDec));  //NO SE LE HACE NADA A ESTE
    }

    //calcula el angulo local de la hora del sol
    float paso7ACosH(float zenith, float latitude, float sinDec, float cosDec){
            return dividir(restar((float) Math.cos(dividir(multiplicar(zenith,(float)Math.PI),180.0f)), multiplicar(sinDec, (float) Math.sin(dividir(multiplicar(latitude,(float)Math.PI),180.0f)))), multiplicar(cosDec, (float) Math.cos(dividir(multiplicar(latitude,(float)Math.PI),180.0f))));
    }

    void paso7A(float cosH){
            if (cosH >1){
                    System.out.println("El sol nunca sale en esta localizacion en la fecha indicada");
            }
            if (cosH < -1){
                    System.out.println("El sol nunca se pone en esta localizacion en la fecha indicada");
            }
    }

    float paso7BSalida(float cosH){
            return restar(360.0f, (float) dividir(multiplicar(180.0f,(float)Math.acos(cosH)),(float) Math.PI));
    }

    float paso7BPuesta(float cosH){
            return (float) dividir(multiplicar(180.0f,(float) Math.acos(cosH)),(float) Math.PI);
    }
    float paso7BFinal(float H){
            return dividir(H, 15.0f);
    }

    float paso8(float H, float RA, float t){
            return sumar(H,restar(RA,restar(multiplicar(0.06571f,t),6.622f)));
    }

    float paso9(float T, float lngHour){
            return restar(T,lngHour);
    }

    float paso10(float UT, float localOffset){
            return sumar(UT,localOffset);
    }

    float obtenerLocalOffset(float longitude){
        return (float) Math.floor(dividir(longitude, 15.0f));
    }
    
    String leerArchivo(String fileName,String str) throws FileNotFoundException{
        Scanner scan = new Scanner(new File(fileName));
        while(scan.hasNext()){
            String line = scan.nextLine().toLowerCase();
            if(line.contains(str.toLowerCase())){
                return line;
            }
        }
        return "";
    }


    void guardarRegistro(String nombreCiudad, float latitude, float longitude){
            String nuevaLinea;
            nuevaLinea = "\n" + nombreCiudad + "," + String.valueOf(latitude) + "," + String.valueOf(longitude);
            Writer output;
            try{
                    output = new BufferedWriter(new FileWriter("file.txt", true));
                    output.append(nuevaLinea);
                    output.close();
            }catch (Exception e){
                    System.out.println("No se pudo guardar el registro en el archivo.");
            }
    }

    public static void main(String[] args) { 
            float day = 0f;
            float month = 0f;
            float year = 0f;
            float latitude = 0f;
            float longitude = 0f;
            float zenith = 90.50f;
            float N;
            float lngHour;
            float tLower;
            float M;
            float L;
            float RA;
            float H;
            float UT;
            float localT;
            float cosH;
            float sinDec;
            float cosDec;
            float tUpper;
            float localOffset = -6.0f;
            String lineaArchivo;
            String city = "";
            
            float horaSalida = 0f;
            float horaPuesta = 0f;

            Sunset algoritmo = new Sunset();
            
            boolean correcto = false;
            
            //  dia del año
            while (!correcto){
                try{
                    day = Float.parseFloat(JOptionPane.showInputDialog(null,"Dia del mes","Ingrese el dia del mes",1));
                    if(day < 1 || day > 31){
                        JOptionPane.showMessageDialog(null,"Numero de mes invalido","Error",0);
                    }
                    else{
                        correcto = true;
                    }
                }
                catch(HeadlessException | NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"El formato es incorrecto, por favor intentelo de nuevo.","Error",0);
                }
            }
            
            // obtener el mes
            
            correcto = false;
            while (!correcto){
                try{
                    month = Float.parseFloat(JOptionPane.showInputDialog(null,"Mes","Ingrese el mes",1));                    
                    if(month < 1 || month > 12){
                        JOptionPane.showMessageDialog(null,"Numero de mes invalido","Error",0);
                    }
                    else{
                        correcto = true;
                    }
                    
                }
                catch(HeadlessException | NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"El formato es incorrecto, por favor intentelo de nuevo.","Error",0);
                }
            }
            
            //obtener el año
            correcto = false;
            while (!correcto){
                try{
                    year = Float.parseFloat(JOptionPane.showInputDialog(null,"Año","Ingrese el año",1));                
                    correcto = true;
                }
                catch(HeadlessException | NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"El formato es incorrecto, por favor intentelo de nuevo.","Error",0);
                }
            }
           
           correcto = false;
           while (!correcto){
                try{
                    city = (String)  JOptionPane.showInputDialog(null,"Nombre de la ciudad","Ingrese el nombre de la ciudad que desea buscar:",1);            
                    correcto = true;
                }
                catch(HeadlessException | NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"El formato es incorrecto, por favor intentelo de nuevo.","Error",0);
                }
            }
            
            try{
                    lineaArchivo = algoritmo.leerArchivo("file.txt",city);
                    System.out.println(lineaArchivo);
                    
                    if(lineaArchivo.equals("")){
                        JOptionPane.showMessageDialog(null,"La ciudad no se encuentra en la base de datos","Error",0);
                        //--------
                        correcto = false;
                        while (!correcto){
                            try{
                                latitude = Float.parseFloat(JOptionPane.showInputDialog(null,"Agregar ciudad: Latitud","Ingrese la latitud de la ciudad por agregar",1));                    
                                correcto = true;
                            }
                            catch(HeadlessException | NumberFormatException e){
                                JOptionPane.showMessageDialog(null,"El formato es incorrecto, por favor intentelo de nuevo.","Error",0);
                            }
                        }
                        //--------
                        correcto = false;
                        while (!correcto){
                            try{
                                longitude = Float.parseFloat(JOptionPane.showInputDialog(null,"Agregar ciudad: Longitud","Ingrese la longitud de la ciudad por agregar",1));                    
                                correcto = true;
                            }
                            catch(HeadlessException | NumberFormatException e){
                                JOptionPane.showMessageDialog(null,"El formato es incorrecto, por favor intentelo de nuevo.","Error",0);
                            }
                        }
                        //--------
                       
                        System.out.println("Ciudad encrontrada: " + city);
                        System.out.println("Latitud asociada: " +latitude);
                        System.out.println("Longitud asociada: " + longitude);
                        
                        algoritmo.guardarRegistro(city,latitude,longitude);
                        JOptionPane.showMessageDialog(null,"La ciudad ha sido registrada en la base de datos","Ciudad registrada",1);
                        
                    }
                    else{
                        String[] partes = lineaArchivo.split(",");
                        String name = partes[0];
                        latitude = Float.parseFloat(partes[1]);
                        longitude = Float.parseFloat(partes[2]);
                        System.out.println("Ciudad encrontrada: " + name);
                        System.out.println("Latitud asociada: " +latitude);
                        System.out.println("Longitud asociada: " + longitude);
                    }
                    
                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  C A L C U L O S    D E    V A R I A B L E S
                    
                    //Calcular hora de salida
                    N = algoritmo.paso1(day,month,year);
                    lngHour = algoritmo.paso2Fijo(longitude);
                    tLower = algoritmo.paso2Salida(longitude,N);
                    M = algoritmo.paso3(tLower);

                    L = algoritmo.paso4(M);
                    if(L > 360)
                       L-=360;
                    if(L < 0)
                       L+=360;

                    RA = algoritmo.paso5A(L);
                    if(RA > 360)
                       RA-=360;
                    if(RA < 0)
                       RA+=360;

                    RA = algoritmo.paso5B(L,RA);
                    RA = algoritmo.paso5C(RA);

                    sinDec = algoritmo.paso6sinDec(L);
                    cosDec = algoritmo.paso6cosDec(sinDec);

                    cosH = algoritmo.paso7ACosH(zenith,latitude,sinDec,cosDec);
                    algoritmo.paso7A(cosH);
                    H = algoritmo.paso7BSalida(cosH);
                    H = algoritmo.paso7BFinal(H);
                    tUpper = algoritmo.paso8(H,RA,tLower);

                    UT = algoritmo.paso9(tUpper,lngHour);
                    if(UT > 24)
                       UT-=24;
                    if(UT < 0)
                       UT+=24;	

                    localT = algoritmo.paso10(UT,localOffset);
                    
                    horaSalida = localT;
                    
                    //Calcular hora de puesta
                    
                    N = algoritmo.paso1(day,month,year);
                    lngHour = algoritmo.paso2Fijo(longitude);
                    tLower = algoritmo.paso2Puesta(longitude,N);
                    M = algoritmo.paso3(tLower);

                    L = algoritmo.paso4(M);
                    if(L > 360)
                       L-=360;
                    if(L < 0)
                       L+=360;

                    RA = algoritmo.paso5A(L);
                    if(RA > 360)
                       RA-=360;
                    if(RA < 0)
                       RA+=360;

                    RA = algoritmo.paso5B(L,RA);
                    RA = algoritmo.paso5C(RA);

                    sinDec = algoritmo.paso6sinDec(L);
                    cosDec = algoritmo.paso6cosDec(sinDec);

                    cosH = algoritmo.paso7ACosH(zenith,latitude,sinDec,cosDec);
                    algoritmo.paso7A(cosH);
                    H = algoritmo.paso7BPuesta(cosH);
                    H = algoritmo.paso7BFinal(H);
                    tUpper = algoritmo.paso8(H,RA,tLower);

                    UT = algoritmo.paso9(tUpper,lngHour);
                    if(UT > 24)
                       UT-=24;
                    if(UT < 0)
                       UT+=24;	

                    localT = algoritmo.paso10(UT,localOffset);
                    
                    horaPuesta = localT;
                    //------------------- Hora de puesta
                    
                    String fecha = Math.round(day) + "/" + Math.round(month) + "/" + Math.round(year);
                    JOptionPane.showMessageDialog(null, "Fecha: "+fecha+ "\n"+"Ciudad: "+city + "\n"+"Hora de salida: "+horaSalida + "\n"+"Hora de puesta: "+horaPuesta,"Sunrise | Sunset",1);
                    
            }
            catch(IOException | NumberFormatException e){
                    JOptionPane.showMessageDialog(null,"Base de datos no encontrada","File Error",0);
            }

            //algoritmo.guardarRegistro("San Carlos",95.42f,52.09f);
    } 
	

}