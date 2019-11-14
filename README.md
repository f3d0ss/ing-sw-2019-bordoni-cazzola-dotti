# Prova Finale Ingegneria del Software 2019
## Gruppo AM44

- ###   10534736    Paolo Bordoni ([@supernivem](https://github.com/supernivem))<br>paolo1.bordoni@mail.polimi.it
- ###   10530016    Federico Cazzola ([@f-cazzola](https://github.com/f-cazzola))<br>federico.cazzola@mail.polimi.it
- ###   10530612    Francesco Dotti ([@dottif](https://github.com/dottif))<br>francesco3.dotti@mail.polimi.it

| Functionality | State |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Complete rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Socket | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| RMI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| GUI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| CLI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Multiple games | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Persistence | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Domination or Towers modes | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Terminator | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |

<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->


### Build instructions

The jar are built using the [Maven Shade Plugin](https://maven.apache.org/plugins/maven-shade-plugin/)
If you want to build yourself the executables jar files run:
```
mvn clean package
```

### Istruzioni per l'avvio
#### Server
Requires java 11+ 

Run:
```
java -jar server.jar
```

Verranno chiesti in ordine i seguenti parametri:
1. Porta per il server socket
2. Porta per il server rmi
3. Timeout (in secondi) dopo il raggiungimento del numero minimo di giocatori
4. Secondi concessi ad ogni giocatore per ogni mossa
5. Numero di teschi iniziale per ogni partita

Esempio:
```
6000
8500
60
120
8
```

#### Client
Requires java 11+ and [javafx 11.0.2+](https://openjfx.io/)

Run:
```
java --module-path /path/to/javfx/lib --add-modules javafx.controls,javafx.fxml -jar client.jar
````

Verranno chiesti in ordine i seguenti parametri, sia per cli che per gui:
1. Tipo di interfaccia utente (1 cli, 2 gui)
2. Tecnologia di connessione
3. Indirizzo ip del server
4. Porta del server (diversa se socket o rmi)
5. Scelta fra nuova partita o riconnessione
6. Nickname *oppure* codice utente fornito all'iscrizione
7. (solo se primo iscritto) Numero board

Esempio (per cli con socket in locale):
```
1
1
127.0.0.1
6000
1
user
1
```
