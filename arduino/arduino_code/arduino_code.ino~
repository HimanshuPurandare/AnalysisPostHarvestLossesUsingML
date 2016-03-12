
#include"dht.h"

dht DHT;
void setup() {
Serial.begin(9600);
}

void loop() {
delay(100);
Serial.print(analogRead(A1)*100/1024);
delay(100);
int humidity = DHT.read11(A2);
Serial.print("\n");
Serial.print(DHT.humidity);
delay(100);
Serial.print("\n");
Serial.print(DHT.temperature);
Serial.print("\n");
delay(700);
}
