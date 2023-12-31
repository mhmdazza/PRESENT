//PRESENT CODE COMPILATED BY MUHAMMAD AZZA ULIN NUHA

#include <stdio.h>
#include "PRESENT (Master).h"

void setup() {
  Serial.begin(9600);
  

}

void loop() {
  unsigned long int m[]={0x0000,0x0000,0x0000,0x0000};
  unsigned long int m1[]={0xffff,0xffff,0xffff,0xffff};
  unsigned long int k[5],kkk[]={0xffff,0xffff,0xffff,0xffff,0xffff};

  for(int i=0;i<5;i++){
    k[i]=kkk[i];
  }
  
  encryptPRESENT(m1,kkk,31);
  String enkrip = String(m1[0],HEX)+String(m1[1],HEX)+String(m1[2],HEX)+String(m1[3],HEX);
  Serial.print("Ciphertext : ");
  Serial.println(enkrip);
  //Serial.println("%.4x %.4x %.4x %.4x \n",m1[0],m1[1],m1[2],m1[3]);
  
  decryptPRESENT(m1,k, 31);
  String dekrip = String(m1[0],HEX)+String(m1[1],HEX)+String(m1[2],HEX)+String(m1[3],HEX);
  Serial.print("Plaintext : ");
  Serial.println(dekrip);
//  Serial.println("%.4x %.4x %.4x %.4x \n",m1[0],m1[1],m1[2],m1[3]);
  delay(500);


}
