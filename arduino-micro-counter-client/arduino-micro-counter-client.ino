#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <Wire.h>
#include <U8g2lib.h>

const char* ssid = "<ENTER YOUR WIFI NETWORK HERE>";
const char* password = "<ENTER YOUR WIFI PASSWORD HERE>";

U8G2_SSD1306_128X32_UNIVISION_F_HW_I2C u8g2(U8G2_R0, /* reset=*/ U8X8_PIN_NONE);

void setup () {

  Serial.begin(9600);
  u8g2.begin();
  u8g2.setContrast(0);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(3000);
    Serial.print("Connecting..");
  }

}

void loop() {

  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;

    http.begin("http://p3dt.net:1234/counter/value");
    int httpCode = http.GET();                   
    if (httpCode == 200) {
      String payload = http.getString();
      Serial.println(payload);
      u8g2.clearBuffer();
      u8g2.setFont(u8g2_font_profont12_tf);
      u8g2.drawStr(0, 8, "http://p3dt.net:1234/");
      u8g2.drawStr(0, 31, "by @pauls_3d_things");
      u8g2.drawStr(0, 20, String("Visits: " + payload).c_str());
      u8g2.sendBuffer();

    }

    http.end();
  }

  delay(10000);
}

