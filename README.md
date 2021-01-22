Schematická vizualizácia grafov pomocou multipólov 
==============
Report zo zimného semestra 2020/2021
Pavol Kebis


Ciele projektu 
--------------

Cieľom projektu je vytvoriť vizualizačný nástroj, pre jednoduchšiu prácu
s malými grafmi vrámci knižnice ba-graph.

-   Pri vykresľovaní grafu zvýrazniť malé rezy

-   Zjednodušiť graf schematickým zobrazením multipólov (typická časť
    grafu)

-   Pri zobrazení multipólov zvýrazniť konektory (zameniteľné hrany
    vrámci miltipólu)

-   Dať možnosť jednoduchej manipulácie s grafom (posúvanie,
    kopírovanie...)

-   Preskúmať možnosť využitia existujúcich platforiem

### Motivácia 

Knižnicu ba-graph využíva primárne špičkový tím “Grafy, mapy a iné
diskrétne štruktúry”  pod vedením p. Škovieru na fakulte matematiky,
fyziky a informatiky Univerzity Komenského v Bratislave na prácu s
kubickými grafmi. Pri tejto práci sa vyskytne potreba zanalyzovať
niektoré grafy a pri tejto analýze je nápomocné mať opakujúce sa a známe
časti grafu (napr. petersenov graf) zjednodušené do tzv. multipólov,
ktoré sprehľadňujú zobrazenie. Taktiež pomáha, ak sú v grafe zvýraznené
malé rezy [^1] (poznámka, čo je malý rez) a ak je možné hýbať s
jednotlivými časťami grafu. Táto analýza je využiteľná hlavne pri
skúmaní väčších grafov, kde sa využívajú poznatky nadobudnuté z
pozorovaní na malých grafoch.

Program má za cieľ pomôcť pozrieť sa na grafy inak, ako bežné grafické
softwéry a ponúknuť funkcionality, ktoré bežne nie sú dostupné.

### Zásady projektu 

Podobný projekt začalo v priebehu posledných dvoch rokov viacero
študentov, menovite , avšak nikdy sa nepodarilo dotiahnuť projekt do
úspešného konca použiteľného pre fakultu. V tomto zmysle som si stanovil
zásady, ktoré chcem pri projekte dodržať.

-   Funkčnosť - projekt musí byť po skončení semestra použiteľný pre
    svoj účel

-   Jednoduchosť - projekt nemá byť zložitým na inštalovanie či
    používanie

-   Kompatibilita - projekt musí vedieť bežať na rôznych OS a sj
    starších zariadeniach

-   Udržateľnosť - podpora projektu musí vyrdžať aspoň zopár rokov

Analýza a výber platformy 
-----------------------------------

V prvej fázi projektu bolo potrebné zanalyzovať existujúce platformy a
porovnať ich funkčnosť s požadovanými vlastnosťami. Tu si vypísané
niektoré kľúčové vlastnosti

-   force layout - Automatické rozloženie vrcholov a hrán do
    rovnomerného zobrazenia

-   CLI support - Možnosť automatizovať program cez konzolu

-   nodes and edges properties - Úpravovať vlastnosti hrán a vrcholov
    pre účely zafarbenia, či zvýšenia priority hrany pri force layout

-   output formats - Dostatočné možnosti zobrazenia/exportovania grafu
    (.pdf, .dot, vlastný formát)

-   interaktivita - Možnosť manuálne hýbať s objektami

-   grupovanie vrcholov - Pre účely vytvárania multipólov

-   groupovanie hrán - Pre účely vytvárania konektorov [^2]

-   podpora systému - Podpora čo najviac OS a ľahká inštalácia programu

### JavaFX ###

Varianta, ktorú som si napokon vybral, je vytvoriť od základov vlastnú
aplikáciu použitím existujúcich GUI libraries v Jave. Najlepšia a
najmodernešia knižnica je aktuálne JavaFX. Táto varianta sa nevylučuje s
použitím iných knižníc na menšie úlohy ako napr. force layout od
graphviz.\
Má: čokoľvek

### Graphviz ###

Táto široko využívaná knižnica je už používaná v knižnica ba-graph,
avšak nie je v nej možnosť interaktivity a ani jednoduchej možnosti
vytvárania multipólov. V projekte ju využijeme na vytváranie force
layout rozložení.\
Má: force layot, CLI support, nodes and edges properties, output
formats, podpora systému\
Nemá: interaktivita, grupovanie vrcholov (iba rectangle outlines),
grupovanie hrán\

### Gephi, yEd, Qt ###

Tieto grafické knižnice ponúkajú mnohé možnosti práce s grafmi, avšak
chýbajú im kľúčové vlastnosti a kvôli slabej modulovateľnosti je ťažké
ich integrovať do projektu. Gephi má tzv. gephi-toolkit, ale v tomto
prípade je omnoho jednoduchšie funkcionalitu naprogramovať vo vlastnom
projekte. Má: force layot, CLI support (gephi iba pre force layout),
nodes and edges properties (iba obmedzene), grupovanie vrcholov (iba
niektoré verzie)\
Nemá: CLI support, grupovanie hrán, output formats (málo formátov),
podpora systému\

### JS libraries ###

V množstve a funkcionalitách grafických knižnicíc jednoznačne prevláda
dominancia JavaScriptu a iných webovských javykov. Tu je však riziko
závislosti programu od verzie JS browsera a krátkodobá podpora. Toto je
možné vidieť aj na spred dvoch rokov, kde je podpora závislá od knižníc,
operačného systému a napokon aj browsera. Navyše je pre mňa osobne
pohodlnšie programovať v Jave vďaka skúsenosti s JavaFX knižnicou.\
Má: čokoľvek Nemá: stabilnú podporu

Plánovanie funkcionalít a použitých formátov 
------------------------------------------------------

Celú prácu programu možno nasledovne zjednodušiť na niekoľkých celkov,
ktorú bude potrebné vrámci projektu riešiť samostatne.

### Vstupné formáty ###

Program potrebuje získať vedomosť o tom, ako má vyzerať graf, ktorý
chceme zobraziť. Spôsobov ako to komunikovať je veľa a v projekte
použijeme niekoľko. Program bude vedieť čítať grafy zo známych formátov
(dot, g6, bratislavský formát), ktoré budú dostupné buď v súbore, alebo
priamo načítavaním cez príkazový riadok. Program by mal mať možnosť
vytvoriť graf ručne pridávaním vrcholov a hrán, avšak tento prístup nie
je kľúčový. Do letného semestra treba premyslieť, ako možno integrovať
tento Javovský projekt použitím C-čkovskej knižnice.\
Pri posielaní vstupu treba zakódovať aj informáciu o multipóloch v
grafe. Toto bude možné cez vlastný intuitívny formát, ktorý bude v
projekte dobre zdokumentovaný.

### Zobrazenie grafu ###

Vo výstupnom grafe chceme reprezentovať vrcholy pomocou bodov a
multipóly pomocou kruhov. Konektory by sme chceli zobraziť tak, aby boli
hrany jedného konektora pri sebe (pri vychádzaní z multipólu) a jasne
odlíšené od ostatných hrán vychádzajúcich z vrchola.\

![Príklad grafu s využitím multipólov](obr)

### Interaktivita ###

V tejto oblasti možno pridať mnohé funkcionality, ktoré všetky sú však
vrámci projektu dobrovoľné. Jediná vyžadovaná funkcionalita je možnosť
hýábať s vrcholmi vrámci okna. Iné dodatočné funkcionality sú:
označovanie, pripínanie, vymazávanie, prefarbovanie objektov; znova
usporiadanie do prehľadného zobrazenia; nádhľad obsahu multipólu;
načítanie viacerých grafov a zmena zobrazenia na iný graf; undo/redo
funkcionalita.

### Export ###

Program má mať možnosť uložiť si aktuálny stav grafu aj s úpravami do
súboru pre pokračovanie v budúcnosti. Taktiež by mala byť možnosť
exportovať graf ako pdf a prípadne do iných známych formátov (napr.
.dot).

### Externé knižnice ###

Niektoré metódy v projekte vyriešim pomocou už existujúcich knižníc

##### Najmenší rez v grafe 

Táto metóda je naprogramovaná v C-čku v knižnica ba-graph

##### Multipóly 

Grupovaniu vrcholov sa minulý rok vo svojom ročníkovom projekte venoval
T. Janeta

##### Force layot 

Pre prehľadné zobrazenie použijeme knižnicu ba-graph s vhodným výstupným
formátom (napr. .dotjson)

Štrukturovanie programu 
-----------------------

V aktuálnom stave je naprogramovaná kostra programu, ktorá obsahuje
základné abstraktné triedy pre budúcu implemetáciu. Medzi tieto triedy
patria:

-   Romulo - prvotná trieda spúšťajúca iné triedy

-   Session - reprezentuje “okno” kde môže byť otvorených viacero grafov
    spolu s ovládacými prvkami programu. Uchováva vlastnosti o aktálne
    zobrazenom grafe a všetkých grafoch. Metódy patriace tejto triede sú
    napr. import, zmena nastavení programu, undo/redo...

-   Graph - reprezentuje samotný graf a teda jeho vlastnosti sú hlavne
    prvky, ktoré obsahuje a plátno na ktorom sú rozmiestnené. Metódy
    triedy sú hlavne pridávanie a uberanie nových prvkov, či
    znovu-rozloženie prvkov na force layout.

-   Glyph - abstraktná trieda reprezentuje jeden prvok/objekt grafu
    (vrchol/multipól, hrana...). Metódy sa realizujú cez design pattern
    Visitor a triedy GlyphVisitor

-   GlyphVisitor - abstraktná trieda reprezentuje metódu triedy Glyph

-   ImportStrategy - abstraktná trieda reprezentujúca metódu na
    načítanie vstupu. Konkrétne triedy budú implementovať vstup cez
    rôzne typy súborov, či konzolu.

[^1]: 

[^2]: Skupina polhrán vychádzajúca z multipólu, vrámci ktorej je
    ľubovoľne možné vymeniať hrany
