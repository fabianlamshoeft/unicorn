Willkommen bei Unicorn 1.0
Gruppe: Fabian Lamshöft (199935), Simon Koschel (), Lena Scholz()

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
Unicorn nutzt ein persistentes Kommunikationsprotokoll. Für jeden neuen Peer
werden zwei neue Sockets verwendet, die an die für den Peer vorgesehenden 
Ports gebunden sind. Auf diese Weise können Nachrichten (gemäß Aufgabenstellung)
Simplex ausgetauscht werden. Ein Socket wird ausschließlich zum Nachhören
auf neue Nachrichten verwendet und der andere Socket ausschließlich zum
versenden.

PROGRAMM-ARCHITEKTUR: Unicorn besitzt eine MVC Architektur.

Da die Funktionalität des Chatts gerade nur im Versenden, Verarbeiten und 
Empfangen liegt, ist keine weitere Aufteilung in weitere Subsysteme nötig.
Somit liegen alle zur Funktion relevanten Klassen im Package "de.unicorn.model".

-------------------------------------------------------------------------------------
4. AUFGABENVERTEILUNG DER KLASSEN
-------------------------------------------------------------------------------------

-------------------------------------------------------------------------------------
5. BEISPIELSZENARIEN - INTERAKTIONEN DER KLASSEN/OBJEKTE
-------------------------------------------------------------------------------------
