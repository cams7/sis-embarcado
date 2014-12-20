#define BAUD_RATE 9600

#define PIN_LED_VERDE     12
#define PIN_LED_AMARELA   13 //atribui o pino 13 a variável ledPin

#define BTN_LED_VERDE     7
#define BTN_LED_AMARELA   8

#define PIN_POTENCIOMETRO 0

const int LED_AMARELA_APAGADA = 10;
const int LED_AMARELA_ACESA = 11;

const int LED_VERDE_APAGADA = 12;
const int LED_VERDE_ACESA = 13;

const int BTN_LED_AMARELA_SOLTO = 14;
const int BTN_LED_AMARELA_PRESSIONADO = 15;

const int BTN_LED_VERDE_SOLTO = 16;
const int BTN_LED_VERDE_PRESSIONADO = 17;

const int MIN_POTENCIOMETRO = 100;
const int MAX_POTENCIOMETRO = 200;

int dado;

int ultimoValorPotenciometro = 0;
int valorAtualPotenciometro;

int estadoBtnLEDAmarela = HIGH;
int ultimoEstadoBtnLEDAmarela = estadoBtnLEDAmarela;

int estadoBtnLEDVerde = HIGH;
int ultimoEstadoBtnLEDVerde =  estadoBtnLEDVerde;

void setup(){
  Serial.begin(BAUD_RATE);//frequência da porta serial - USART
  
  pinMode(PIN_LED_AMARELA, OUTPUT); //define o pino o ledPin como saída
  pinMode(PIN_LED_VERDE, OUTPUT);
  
  pinMode(BTN_LED_VERDE, INPUT);
  digitalWrite(BTN_LED_VERDE, estadoBtnLEDVerde);
  
  pinMode(BTN_LED_AMARELA, INPUT);
  digitalWrite(BTN_LED_AMARELA, estadoBtnLEDAmarela);
}
 
void loop(){
  if(Serial.available() > 0){ //verifica se existe comunicação com a porta serial
      dado = Serial.read();//lê os dados da porta serial
      
      switch(dado){
        case LED_AMARELA_ACESA:
           digitalWrite(PIN_LED_AMARELA, HIGH); //liga o pino ledPin
           Serial.write(LED_AMARELA_ACESA);                     
           break;
        case LED_AMARELA_APAGADA:
           digitalWrite(PIN_LED_AMARELA, LOW); //desliga o pino ledPin
           Serial.write(LED_AMARELA_APAGADA);
           break;
        case LED_VERDE_ACESA:
           digitalWrite(PIN_LED_VERDE, HIGH); 
           Serial.write(LED_VERDE_ACESA);          
           break;
        case LED_VERDE_APAGADA:
           digitalWrite(PIN_LED_VERDE, LOW); 
           Serial.write(LED_VERDE_APAGADA);
           break;
        default:           
           break;
    }
  }
  
  valorAtualPotenciometro = analogRead(PIN_POTENCIOMETRO);
  if(valorAtualPotenciometro != ultimoValorPotenciometro){
    ultimoValorPotenciometro = valorAtualPotenciometro;
    /* Mapeia um valor analogico para 8 bits (0 to 255) */
    Serial.write(map(ultimoValorPotenciometro, 0, 1023, MIN_POTENCIOMETRO, MAX_POTENCIOMETRO));
  }
  
  estadoBtnLEDAmarela = digitalRead(BTN_LED_AMARELA);
  if(estadoBtnLEDAmarela != ultimoEstadoBtnLEDAmarela){
    if(estadoBtnLEDAmarela==HIGH){
      digitalWrite(PIN_LED_AMARELA, LOW); 
      Serial.write(BTN_LED_AMARELA_SOLTO);
    }else{
      digitalWrite(PIN_LED_AMARELA, HIGH);
      Serial.write(BTN_LED_AMARELA_PRESSIONADO);
    }  
    ultimoEstadoBtnLEDAmarela = estadoBtnLEDAmarela; 
  }
  
  estadoBtnLEDVerde = digitalRead(BTN_LED_VERDE);
  if(estadoBtnLEDVerde != ultimoEstadoBtnLEDVerde){
    if(estadoBtnLEDVerde==HIGH){
      digitalWrite(PIN_LED_VERDE, LOW); 
      Serial.write(BTN_LED_VERDE_SOLTO);
    }else{
      digitalWrite(PIN_LED_VERDE, HIGH);
      Serial.write(BTN_LED_VERDE_PRESSIONADO);
    }  
    ultimoEstadoBtnLEDVerde = estadoBtnLEDVerde; 
  }  
}
