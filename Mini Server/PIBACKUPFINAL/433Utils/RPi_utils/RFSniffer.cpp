#include "RCSwitch.h"
#include <stdlib.h>
#include <stdio.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <unistd.h>
RCSwitch mySwitch;

char *socket_path = "/home/pi/uds_socket";

int main(int argc, char *argv[]) {
     // Consult https://projects.drogon.net/raspberry-pi/wiringpi/pins/
     int PIN = 2;
        struct sockaddr_un addr;
        char buf[100];
        int fd,rc;

        if (argc > 1) socket_path=argv[1];

        if ( (fd = socket(AF_UNIX, SOCK_STREAM, 0)) == -1) {
                perror("socket error");
                exit(-1);
        }

        memset(&addr, 0, sizeof(addr));
        addr.sun_family = AF_UNIX;
        strncpy(addr.sun_path, socket_path, sizeof(addr.sun_path)-1);

        if (connect(fd, (struct sockaddr*)&addr, sizeof(addr)) == -1) {
                perror("connect error");
		printf("connect error");
                exit(-1);
                }
     if(wiringPiSetup() == -1) {
       printf("wiringPiSetup failed, exiting...");
       return 0;
     }

     int pulseLength = 0;
     if (argv[1] != NULL) pulseLength = atoi(argv[1]);

     mySwitch = RCSwitch();
         if (pulseLength != 0) mySwitch.setPulseLength(pulseLength);
     mySwitch.enableReceive(PIN);  // Receiver on interrupt 0 => that is $

     while(1) {
      if (mySwitch.available()) {

        int value = mySwitch.getReceivedValue();

        if (value == 0) {
          printf("Unknown encoding\n");
        } else {
                int n = mySwitch.getReceivedValue();
                printf("Rec %i\n",n );
                snprintf(buf, sizeof(buf), "%d", n);
		rc=strlen(buf);
                if (write(fd,buf , rc) != rc) {
                        if (rc > 0) fprintf(stderr,"partial write");
                        else {
                                perror("write error");
                                exit(-1);
                        }
                }
        }
        mySwitch.resetAvailable();
      }
  }
  exit(0);
}
