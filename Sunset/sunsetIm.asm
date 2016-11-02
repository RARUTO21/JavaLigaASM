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

Java_Sunset_sin proc JNIEnv:DWORD, jobject:DWORD,grado:DWORD
   finit
   fld grado
   fsin 
   ret
Java_Sunset_sin endp

Java_Sunset_cos proc JNIEnv:DWORD, jobject:DWORD,grado:DWORD
   finit
   fld grado
   fcos
   ret
Java_Sunset_cos endp

Java_Sunset_tan proc JNIEnv:DWORD, jobject:DWORD,grado:DWORD
   finit
   fld grado
   fptan
   ret
Java_Sunset_tan endp

Java_Sunset_atan proc JNIEnv:DWORD, jobject:DWORD,grado:DWORD
   finit
   fld grado
   fpatan
   ret
Java_Sunset_atan endp

;asin(x) = atan(sqrt(x*x/(1-x*x)))
Java_Sunset_asin  proc    JNIEnv:DWORD, jobject:DWORD,grado:DWORD
    finit
    fld     grado
    fld     st(0)           ;Duplicate X on tos.
    fmul                    ;Compute X**2.
    fld     st(0)           ;Duplicate X**2 on tos.
    fld1                    ;Compute 1-X**2.
    fsubr
    fdiv                    ;Compute X**2/(1-X**2).
    fsqrt                   ;Compute sqrt(x**2/(1-X**2)).
    fld1                    ;To compute full arctangent.
    fpatan                  ;Compute atan of the above.
    ret
Java_Sunset_asin  endp

Java_Sunset_acos  proc    JNIEnv:DWORD, jobject:DWORD,grado:DWORD
   finit
   fld     grado
   fld     st(0)           ;Duplicate X on tos.
   fmul                    ;Compute X**2.
   fld     st(0)           ;Duplicate X**2 on tos.
   fld1                    ;Compute 1-X**2.
   fsubr
   fdivr                   ;Compute (1-x**2)/X**2.
   fsqrt                   ;Compute sqrt((1-X**2)/X**2).
   fld1                    ;To compute full arctangent.NOTA: Si lo que llevo es negativo, cargo -1 en vez de 1, si es positivo entonces se deja como está
   fpatan                  ;Compute atan of the above.
   ret
Java_Sunset_acos  endp

Java_Sunset_pi proc JNIEnv:DWORD, jobject:DWORD
   finit
   fldpi
   ret
Java_Sunset_pi endp


END