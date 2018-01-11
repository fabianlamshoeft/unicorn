Willkommen bei Unicorn 1.0
Gruppe: Fabian Lamshöft (199935), Simon Koschel (199842), Lena Scholz(192953)

In den folgenden Abschnitten werden alle nötigen Informationen zur Nutzung von Unicorn 
gegeben sowie Designentscheidungen erläutert.

INHALTSVERZEICHNIS

1. VORRAUSSETZUNG ZUR NUTZUNG VON UNICORN
2. UNICORN BENUTZEN - SCHRITT FÜR SCHRITT ANLEITUNG
3. DESIGNENTSCHEIDUNGEN
4. AUFGABENVERTEILUNG DER KLASSEN
5. BEISPIELSZENARIEN - INTERAKTIONEN DER KLASSEN/OBJEKTE

-------------------------------------------------------------------------------------
1. VORRAUSSETZUNG ZUR NUTZUNG VON UNICORN
-------------------------------------------------------------------------------------
-       Um Unicon 1.0 ausführen zu können müssen Sie mindestens Java 9 installiert
        haben.
        
-       Falls Sie versuchen sollten, mit Kommunikationspartnern außerhalb Ihres
        lokalen Netzwerks zu Chatten, müssen Sie eine Portfreigabe an Ihrem Router
        einrichten. Ansonsten kann es sein, dass eingehende TCP Verbindungen von 
        Unicorn durch die Firewall geblockt werden.
        
-------------------------------------------------------------------------------------
2. UNICORN BENUTZEN - SCHRITT FÜR SCHRITT ANLEITUNG
-------------------------------------------------------------------------------------
Um Unicorn auszuführen müssen Sie die mitgelieferte runnable .jar Datei ausführen.
Nachdem Sie das Programm gestartet haben erscheint ein Dialogfenster,
in welchem Sie Ihren Session Namen und den Port eintragen können.

Session Name:   Der Session Name ist der Name, unter dem Sie bei allen verbundenen
                Peers sichtbar sind.
                
Port:           Der Port gibt an, unter welchem Port nach neu eingehenden Verbindungen
                gelauscht werden soll. 
                
Mit einem Klick auf "Los geht's" gelangen Sie auf das Chat Fenster:

... LENA, AM BESTEN MACHST DU HIER WEITER ... IST JA DEINE OBERFLÄCHE ;) ...

-------------------------------------------------------------------------------------
3. DESIGNENTSCHEIDUNGEN
-------------------------------------------------------------------------------------
KOMMUNIKATION:
Unicorn nutzt ein persistentes Kommunikationsprotokoll (wie HTTP/1.1). Für jeden neuen Peer
werden zwei neue Sockets verwendet, die an die für den Peer vorgesehenden 
Ports gebunden sind. Auf diese Weise können Nachrichten (gemäß Aufgabenstellung)
Simplex ausgetauscht werden. Ein Socket wird ausschließlich zum Nachhören
auf neue Nachrichten verwendet und der andere Socket ausschließlich zum
versenden.

Zudem ist es möglich mit Clients zu kommunizieren, welche nicht persistent arbeiten.
Dies wird durch eine Erweiterung des ServerPortListeners erreicht,
siehe: 4. Aufgabenveriteilung der Klassen.

PROGRAMM-ARCHITEKTUR: Unicorn besitzt eine MVC Architektur.

Da die Funktionalität des Chatts gerade nur im Versenden, Verarbeiten und 
Empfangen liegt, ist keine weitere Aufteilung in weitere Subsysteme nötig.
Somit liegen alle zur Funktion relevanten Klassen im Package "de.unicorn.model".

Alle geschriebenen Klassen haben nur eine einzelne Aufgabe, welche sie erledigen.
Im nächsten Abschnitt wird weiter darauf eingegangen.

(Konstruktionsprinzip: Single Responsibility)

-------------------------------------------------------------------------------------
4. AUFGABENVERTEILUNG DER KLASSEN
-------------------------------------------------------------------------------------
Eine detalliertere Dokumentation befindet sich direkt im Code. Dieser Abschnitt ist
lediglich dazu gedacht, um sich ein Überblick über die verschliedenen Klassen machen
zu können.

-> Kommunikation mit einem konkreten Peer:

CONNECTION:
Die Klasse Connection verwaltet die Verbindungsinformationen zu einem einzelnen Peer,
darunter SessionName, IP-Adresse und Port. Zudem protokolliert Connection alle
eingehenden und Ausgehenden Nachrichten in einer History mit. Die Verwaltung der 
eingehenden und ausgehenden Sockets wird an zwei weitere Klassen weiterdeligiert:
InputPort und OutputPort.

INPUT_PORT:
Die Klasse InputPort lauscht auf dem Port für eingehende Verbindungen nach neuen 
Nachrichten und reicht sie an die Klasse Connection weiter, um diese 
interpretieren zu lassen. (siehe: interpretIncommingMessage(String msg))
Dies wird durch einen eigenen Thread umgesetzt.

OUTPUT_PORT:
Die Klasse OutputPort verwaltet den Port, welcher zum versenden von Nachrichten 
verwendet wird. Dazu stellt OutputPort eine Reihe von Methoden zur Verfügung,
welche alle möglichen Nachrichtentypen repräsentieren, darunter sendPoke,
sendMessage und sendDisconnect.

-> Alle aktiven Verbindungen werden in einer Liste verwaltet, welche sich in der Klasse
ConnectionRegistry befindet:

CONNECTION_REGISTRY:
Verwaltet die Liste der aktiven Verbindungen. Hierfür werden Methoden zum hinzufügen,
ausgeben und löschen einzelner Verbindungen zur Verfügung gestellt. Alle Attribute 
und Methoden der Klasse sind statisch, um einen zentralen Zugriffspunkt für andere
Klassen zu realisieren. Die Verwaltung der Liste übernehmen die Klassen 
ConnectionListManager und OnwardTransmitter. So ist gewärleistet, dass ConnectionRegisty
alleinig zum "speichern" der Verbindungen genutzt wird. 

-> Listenverwaltung:

CONNECTION_LIST_MANAGER:
Der ConnectionListManager läuft alle 30 sekunden über die Liste aktiver Verbindungen
in der ConnectionRegistry und führt folgende Aktionen aus:

- Löschen veralteter Verbindungen, dessen letzter Poke länger als eine Minute
  her ist.
 
- Versenden von Poke Nachrichten bei allen aktiven Verbindungen.

Der ConnectionListManager ist durch einen eigenen Thread umgesetzt.

ONWARD_TRANSMITTER:
Der Onward Transmitter stellt Methoden zur Verfügung, mit welchen Poke und
Disconnect Nachrichten an alle anderen Bekannten Peers weitergeleitet werden
können. Hierbei werden der letzte Absender sowie der orginale Absender der 
Poke / Disconnectnachricht ausgenommen, um unnötigen Netzwerkverkehr 
entgegen zu wirken. 

(Es ist anzunehmen, dass der letzte Sender der Poke bzw.
Disconnect Nachricht bereits über die Änderung der Situation bescheid weiß,
wesshalb ein erneutes Senden nicht mehr notwendig ist. Dies gilt natürlich
auch für den Peer, welcher den Poke / Disconnect Nachricht gesendet hat.)

-> Verbindungsaufbau

SERVER_PORT_LISTENER:

CONNECTION_FACTORY:

SESSION_MANAGER:

-> Kommunikation mit Benutzerschnittstelle:

FACADE:

I_FACADE_OBSERVER:

-> Weitere Klassen:

SYNTAX_CHECKER:

-------------------------------------------------------------------------------------
5. BEISPIELSZENARIEN - INTERAKTIONEN DER KLASSEN/OBJEKTE
-------------------------------------------------------------------------------------
1. Ein unbekanntes POKE erreicht den ServerPortListener:

2. Der Benutzer sendet ein CONNECT Befehl

3. Der Benutzer will eine Nachricht senden:

4. Eine DISCONNECT Nachricht trudelt ein:

5. Der Benutzer hat keine Lust mehr:

6. Eine ungültige Nachricht trudelt ein:

7. Ein nicht Persistenter Peer will sich verbinden:
