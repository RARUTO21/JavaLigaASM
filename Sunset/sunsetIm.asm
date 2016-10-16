.386
.model flat,stdcall
.code

Java_Sunset_sumar proc JNIEnv:DWORD, jobject:DWORD, a:DWORD,b:DWORD
   finit 
   fld a
   fld b
   fadd 

   ret    	  ;por default se retorna el valor del registro eax

Java_Sunset_sumar endp 

Java_Sunset_restar proc JNIEnv:DWORD, jobject:DWORD, a:DWORD,b:DWORD
   finit
   fld a
   fld b
   fsub
   
   ret
Java_Sunset_restar endp

Java_Sunset_multiplicar proc JNIEnv:DWORD, jobject:DWORD,a:DWORD, b:DWORD    
   finit
   fld a
   fld b
   fmul
   
   ret
Java_Sunset_multiplicar endp 

Java_Sunset_dividir proc JNIEnv:DWORD, jobject:DWORD,a:DWORD, b:DWORD    
   finit
   fld a
   fld b
   fdiv
   
   ret
Java_Sunset_dividir endp

Java_Sunset_comparar proc JNIEnv:DWORD, jobject:DWORD,a:DWORD, b:DWORD    
	finit
	mov eax,a
	mov ebx,b
	sub eax,ebx
	jnc mayora
	jmp mayorb
	
	mayora:
	fld a
	jmp finalizar
	
	mayorb:
	fld b
	
	finalizar:
	ret
Java_Sunset_comparar endp

END