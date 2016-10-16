public class Sunset {

	public native float sumar(float a, float b); 
	public native float restar(float a, float b); 
	public native float multiplicar(float a, float b); 
	public native float dividir(float a, float b);
	public native float comparar(float a, float b);
	//fasrg
	
	static { 
		System.loadLibrary("operacionesImpl"); 
	} 
	
	float paso1(float day, float month, float year){
		float N1 = Math.floor(divir(multiplicar(275.0f,month),9.0f));
		float N2 = Math.floor(divir(sumar(9.0f,month),12.0f));
		float N3 = dividir(sumar(restar(year, multiplicar(4.0f, Math.floor(divir(year,4.0f)))), 2.0f), 3.0f);
		float N = restar(sumar(restar(N1, multiplicar(N2, N3)), day), 3.0f);
	}
	
	float paso2Salida(float longitude, float N, boolean flag){
		lngHour= dividir(longitude, 15.0f);
		t= sumar(N, dividir(restar(6.0f, lngHour), 24.0f))
	}
	
	float paso2Puesta(float longitude, float N, boolean flag){
		lngHour= dividir(longitude, 15.0f);
		t= sumar(N, dividir(restar(18.0f, lngHour), 24.0f))
	}
	
	public static void main(String[] args) { 
		float day;
		float month;
		float year;
		float latitude;
		float longitude;
		float zenith = 90.0f;
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
		
	} 
	

}