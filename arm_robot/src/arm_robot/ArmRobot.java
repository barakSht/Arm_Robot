

void main() {
  char dataRx  ;
  int BASE_POS=50 ,FIRST_ARM_POS=70,SEC_ARM_POS=86,CLIPPER_ANGLE_POS=50 ,CLIPPER_POS=42,j,i,k;   // servo places //
  ANSELD = 0 ; TRISD = 0 ; // 0000000 Servo output  0-BASE_POS ,1-FIRST_ARM_POS ,2-SEC_ARM_POS ,3-CLIPPER_ANGLE_POS ,4-CLIPPER_POS    =>   5 outputs
  UART1_Init(9600);
  UART_Set_Active(&UART1_Read, &UART1_Write, &UART1_Data_Ready, &UART1_Tx_Idle); // set UART1 active
   
  while (1){
      if (UART_Data_Ready()){  dataRx=UART1_Read();
         UART1_Write(dataRx);

       if  (dataRx < 8){
         if( dataRx < 5) {
            if( dataRx < 2) {
              if  (dataRx == 0)
              {
            	  BASE_POS=50 ;
            	  FIRST_ARM_POS= 70 ;
            	  SEC_ARM_POS= 86  ;
            	  CLIPPER_ANGLE_POS= 50  ;
            	  CLIPPER_POS= 42 ;
              }
               if (dataRx == 1 && BASE_POS < 96 ) 
               {
            	   BASE_POS=BASE_POS+2;   
            	   UART1_Write(BASE_POS); 
               }
                   }
            else {
                if (dataRx == 2 && FIRST_ARM_POS < 91)
                { 
                	FIRST_ARM_POS=FIRST_ARM_POS+2;   
                	UART1_Write(FIRST_ARM_POS);
                }
                if (dataRx == 3 && SEC_ARM_POS> 4 )    
                   {  
                	   SEC_ARM_POS=SEC_ARM_POS-2;    
                	   UART1_Write(SEC_ARM_POS); 
                   }
                if (dataRx == 4 && CLIPPER_ANGLE_POS > 4) 
                {  
                	CLIPPER_ANGLE_POS=CLIPPER_ANGLE_POS-2 ;
                	UART1_Write(CLIPPER_ANGLE_POS);
                }
                } // dataRx < 2
                }/*  dataRx < 8 */
                else {            
                      if (dataRx == 5 && CLIPPER_POS > 7) 
                      { 
                    	  CLIPPER_POS=CLIPPER_POS-2;
                    	  UART1_Write(CLIPPER_POS);
                      }
                      if (dataRx == 6 && BASE_POS > 4) 
                      { 
                    	  BASE_POS=BASE_POS-2;   
                    	  UART1_Write(BASE_POS); 
                      }
                      if (dataRx == 7 && FIRST_ARM_POS > 8)
                      {   
                    	  FIRST_ARM_POS=FIRST_ARM_POS-2;    
                    	  UART1_Write(FIRST_ARM_POS);
                      }
                     } // else  //

                     }/*   dataRx < 8  */  
       				else  
       				{
                        if (dataRx < 11)
                        {
                            if (dataRx == 8 && SEC_ARM_POS < 96)
                            {   
                            	SEC_ARM_POS=SEC_ARM_POs+2;   
                            	UART1_Write(SEC_ARM_POS); 
                            }
                            if (dataRx == 9 && CLIPPER_ANGLE_POS < 96)
                            {
                            CLIPPER_ANGLE_POS=CLIPPER_ANGLE_POS+2;
                            UART1_Write(CLIPPER_ANGLE_POS);
                            }
                            if (dataRx == 10 && CLIPPER_POS < 85)
                            {
                            CLIPPER_POS=CLIPPER_POS+2;   
                            UART1_Write(CLIPPER_POS);
                            }
                         }
            else { if (dataRx < 13) 
            {
            	if (dataRx == 11)
            		SEC_ARM_POS=  20 ;
            	if  (dataRx == 12)
            	{
            		BASE_POS=90 ;
            		FIRST_ARM_POS= 44 ;
            		SEC_ARM_POS= 74  ;
            		CLIPPER_ANGLE_POS= 52  ;
            		CLIPPER_POS= 42 ;
            	}
            }
            else 
            {
               if  (dataRx == 13)
               {  
               BASE_POS=50 ;
               FIRST_ARM_POS= 44 ;
               SEC_ARM_POS= 74  ;
               CLIPPER_ANGLE_POS= 52  ;
               CLIPPER_POS= 42 ;
               }
               if  (dataRx == 14)
               {
               BASE_POS=6 ;
               FIRST_ARM_POS= 44;
               SEC_ARM_POS= 74;
               CLIPPER_ANGLE_POS= 52;
               CLIPPER_POS= 42;
               }
            }
             } // else //if
             }// else  //
             }  //  if  UART_Data_Ready  //
                // for(i=0;i<2;i++){        // 0.1 sec cycle
         PORTd= 0b00011111    ;
         Delay_us(700);
                   for(j=0;j<100;j++){
                      if(BASE_POS==j)
                    	  portd.RB0=0 ;                     //
                      if(FIRST_ARM_POS==j)
                    	  portd.RB1=0 ;                     //   "on" time
                      if(SEC_ARM_POS==j) 
                    	  portd.RB2=0 ;                     //
                      if(CLIPPER_ANGLE_POS==j)
                    	  portd.RB3=0 ;                     //
                      if(CLIPPER_POS==j)  
                    	  portd.RB4=0 ;                     //
                    Delay_us(13);
                   }  // for j
       Delay_us(18000);
                    // } // for i //
      }  // while //
  }   // main   //

===========================================================================================================================

int BASE_POS=50 ,FIRST_ARM_POS=70,SEC_ARM_POS=86,CLIPPER_ANGLE_POS=50 ,CLIPPER_POS=42,j;   // servo places //

enum {RESET=0, BASE_POS_ADD2,  FIRST_ARM_POS_ADD2,  SEC_ARM_POS_MINUS2,        CLIPPER_ANGLE_POS_MINUS2,        CLIPPER_POS_MINUS2, BASE_POS_MINUS2,
    FIRST_ARM_POS_MINUS2,    SEC_ARM_POS_ADD2,    CLIPPER_ANGLE_POS_ADD2,    CLIPPER_POS_ADD2, STRETCH_ARM,   GO_LEFT,   GO_MIDDLE,    GO_RIGHT };

void interrupt() {  char dataRx  ;   dataRx=UART1_Read();

 switch (dataRx) {
         case RESET: 
        	 BASE_POS=50 ; FIRST_ARM_POS= 70 ;
        	 SEC_ARM_POS= 86  ;
        	 CLIPPER_ANGLE_POS= 50  ;
        	 CLIPPER_POS= 42  ;
        	 break;
         case BASE_POS_ADD2:
        	 if ( BASE_POS < 92 )
        		 BASE_POS=BASE_POS+2   ;
        	 	 break;
         case FIRST_ARM_POS_ADD2:
        	 if (FIRST_ARM_POS < 91) 
        		 FIRST_ARM_POS=FIRST_ARM_POS+2 ;
        	 	 break;
         case SEC_ARM_POS_MINUS2:         if (SEC_ARM_POS> 4 )              SEC_ARM_POS=SEC_ARM_POS-2                                  ; break;
         case CLIPPER_ANGLE_POS_MINUS2:   if (CLIPPER_ANGLE_POS > 4)        CLIPPER_ANGLE_POS=CLIPPER_ANGLE_POS-2                      ; break;
         case CLIPPER_POS_MINUS2:         if (CLIPPER_POS > 7)              CLIPPER_POS=CLIPPER_POS-2                                  ; break;
         case BASE_POS_MINUS2:            if (BASE_POS > 8)                 BASE_POS=BASE_POS-2                                        ; break;
         case FIRST_ARM_POS_MINUS2:       if (FIRST_ARM_POS > 8)            FIRST_ARM_POS=FIRST_ARM_POS-2                              ; break;
         case SEC_ARM_POS_ADD2:           if (SEC_ARM_POS < 96)             SEC_ARM_POS=SEC_ARM_POs+2                                  ; break;
         case CLIPPER_ANGLE_POS_ADD2:     if (CLIPPER_ANGLE_POS < 96)       CLIPPER_ANGLE_POS=CLIPPER_ANGLE_POS+2                      ; break;
         case CLIPPER_POS_ADD2:           if (CLIPPER_POS < 85)             CLIPPER_POS=CLIPPER_POS+2                                  ; break;
         case STRETCH_ARM:                SEC_ARM_POS=  20 ;                                                                           ; break;
         case GO_LEFT:                    BASE_POS=90 ; FIRST_ARM_POS= 44 ; SEC_ARM_POS= 70  ;CLIPPER_ANGLE_POS= 52                    ; break;
         case GO_MIDDLE:                  BASE_POS=50 ; FIRST_ARM_POS= 44 ; SEC_ARM_POS= 70  ;CLIPPER_ANGLE_POS= 52                    ; break;
         case GO_RIGHT:                   BASE_POS=8  ; FIRST_ARM_POS= 44 ; SEC_ARM_POS= 70  ;CLIPPER_ANGLE_POS= 52                    ; break;
         default: break;
 }                                       RC1IF_bit = 0;    }              
                                         // interupt  //

void main() {
                                          //define Interrupt  //
 RCON.IPEN = 1;
 INTCON.GIEH=1;
 INTCON.PEIE=1;
 IPR1.RC1IP = 1;
 IPR1.TX1IP = 0;
 PIE1.RC1IE=1;
 PIE1.TX1IE = 0;
 Delay_ms(100);

 ANSELA = 0 ;         TRISA = 0 ; // 0000000 Servo output  0-BASE_POS ,1-FIRST_ARM_POS ,2-SEC_ARM_POS ,3-CLIPPER_ANGLE_POS ,4-CLIPPER_POS    =>   5 outputs

 UART1_Init(9600);    UART_Set_Active(&UART1_Read, &UART1_Write, &UART1_Data_Ready, &UART1_Tx_Idle); // set UART1 active

 while (1){                                                                               // for(i=0;i<2;i++){        // 0.1 sec cycle
       PORTA= 0b00011111    ;
       Delay_us(900);
         for(j=0;j<100;j++){
            if(BASE_POS==j)  
            	porta.RB0=0 ;                     //
            if(FIRST_ARM_POS==j) 
            	porta.RB1=0 ;                     //   "on" time
            if(SEC_ARM_POS==j)
            	porta.RB2=0 ;                     //
            if(CLIPPER_ANGLE_POS==j)
            	porta.RB3=0 ;                     //
            if(CLIPPER_POS==j)
            	porta.RB4=0 ;                     //
          Delay_us(11);
         }  // for j
     Delay_us(18000);
             // } // for i //
    }  // while //
}   // main   //