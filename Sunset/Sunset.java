public class Sunset {

	public native float sumar(float a, float b); 
	public native float restar(float a, float b); 
	public native float multiplicar(float a, float b); 
	public native float dividir(float a, float b);
	public native float comparar(float a, float b);
	
	static { 
		System.loadLibrary("operacionesImpl"); 
	} 
	
	float paso1(float day, float month, float year){
		float N1 = Math.floor(divir(multiplicar(275.0f,month),9.0f));
		float N2 = Math.floor(divir(sumar(9.0f,month),12.0f));
		float N3 = sumar(1.0f,dividir(sumar(restar(year, multiplicar(4.0f, Math.floor(divir(year,4.0f)))), 2.0f), 3.0f));
		return restar(sumar(restar(N1, multiplicar(N2, N3)), day), 30.0f);
	}
	
	float paso2Salida(float longitude, float N){
		return sumar(N, dividir(restar(6.0f, lngHour), 24.0f));
	}
	
	float paso2Puesta(float longitude, float N){
		return sumar(N, dividir(restar(18.0f, lngHour), 24.0f));
	}
	
	float paso2Fijo(float longitude){
		return dividir(longitude, 15.0f);
	}
	
	//Calcula la anomalia media del sol
	
	float paso3(float t){
		return restar(multiplicar(0.9856f,t), 3.289f); //Retorna M
	}
	
	float paso4(float M){
		return sumar(sumar(sumar(multiplicar(1.916f,Math.sin(M)),multiplicar(0.020f*Math.sin(multiplicar(2.0f,M)))),282.634f),M);
	}
	
	float paso5A(float L){
		return Math.atan(multiplicar(0.91764f, Math.tan(L)) //Retorna M
		//RA potentially needs to be adjusted into the range [0,360) by adding/subtracting 360
	}
	
	float paso5B(float L, float RA){
		float Lquadrant = multiplicar((Math.floor(dividir(L, 90.0f)), 90.0f);
		float RAquadrant = multiplicar((Math.floor(dividir(RA, 90.0f)), 90.0f);
		return sumar(RA, restar(Lquadrant, RAquadrant)); // retorna el RA
	}
	
	float paso5C(float RA){
		return dividir(RA, 15.0f); // retorna el RA
	}
	
	float paso6sinDec(float L){
		return multiplicar(0.39782f,Math.sin(L));
	}
	
	float paso6cosDec(float sinDec){
		return Math.cos(Math.asin(sinDec));
	}
	
	//calcula el angulo local de la hora del sol
	float paso7ACosH(float zenith, float latitude, float sinDec){
		return dividir(restar(Math.cos(zenith), multiplicar(sinDec, Math.sin(latitude))), multiplicar(cosDec, Math.cos(latitude)));
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
		return restar(360.0f, Math.acos(cosH));
	}
	
	float paso7BPuesta(float cosH){
		return Math.acos(cosH);
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
		
		Sunset algoritmo = new Sunset();
		
		N = algoritmo.paso1(day,month,year);
		lngHour = algoritmo.paso2Fijo(longitude);
		tLower = algoritmo.paso2Salida(longitude,N);
		M = algoritmo.paso3(tLower);
		L = algoritmo.paso4(M);
		RA = algoritmo.paso5A(L);
		RA = algoritmo.paso5B(L,RA);
		RA = algoritmo.paso5C(RA);
		
		sinDec = algoritmo.paso6sinDec(L);
		cosDec = algoritmo.paso6cosDec(sinDec);
		
		cosH = algoritmo.paso7ACosH(zenith,latitude,sinDec);
		algotirmo.paso7A(cosH);
		H = algoritmo.paso7BSalida(cosH);
		H = algoritmo.paso7BFinal(H);
		tUpper = algoritmo.paso8(H,RA,tLower);
		UT = algoritmo.paso9(tUpper,lngHour);
		
		localT = algoritmo.paso10(UT,localOffset);
	} 
	

}