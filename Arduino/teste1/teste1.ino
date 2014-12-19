#define LED_PIN 13 //atribui o pino 13 a variável ledPin
#define POTENCIOMETRO_PIN 0

int dado; //variável que receberá os dados da porta serial
 
void setup(){
  Serial.begin(9600);//frequência da porta serial
   pinMode(LED_PIN,OUTPUT); //define o pino o ledPin como saída
}
 
void loop(){
  if(Serial.available() > 0){ //verifica se existe comunicação com a porta serial
      dado = Serial.read();//lê os dados da porta serial
      switch(dado){
        case 1:
           digitalWrite(LED_PIN,HIGH); //liga o pino ledPin
           //Serial.write(255);
           /* Mapeia um valor analogico para 8 bits (0 to 255) */
           Serial.write(map(analogRead(POTENCIOMETRO_PIN), 0, 1023, 0, 255));
           break;
        case 0:
           digitalWrite(LED_PIN,LOW); //desliga o pino ledPin
           break;
        default:           
           break;
    }
  }
}
