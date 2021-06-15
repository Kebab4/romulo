Schematická vizualizácia grafov pomocou multipólov
==============
Report z letného a zimného semestra 2020/2021
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

## Report z letného semestra

Hlavná časť práce v letnom semetri bolo dizajnovanie a programovanie 
modelu zo zimného semestra. Ten sa napokon upravil do nižšie popísanej verzie.

Formáty
--------------
Počas semestra som narazil na problém práce s formátmi reprezentácie grafu. Napokon som 
tento problém vyriešil vytvorením dvoch formátov, ktoré sú oba podporované aplikáciou.
Formát EMBA podporuje zapísanie obsahu multipólu, čo umožní v niektorých prípadoch
nájsť minimálny rez, ktorý môže prechádzať cez multipól. Formát SMBA je jednoduchší a
reprezentuje multipól ako jeden vrchol. V oboch formátoch je informácia o rozložení
hrán do konektorov a názov multipólu.

### old BA formát ###
Tento formát používa knižnica ba-graph. Tento formát reprezentácie grafu má v sebe
pre každý graf informáciu o incidencií jeho vrcholov. Pseukód načítania oldBA formátu
by bol nasledovný:

    pocet_grafov
    for i in pocet_grafov:
        index_grafu
        velkost_grafu
        for j in velkost_grafu:
            susedia_vrchola = input().split()


Ukážka súboru

    2
    1
    4
    1 2 3
    0 3 3 2 2
    0 1 1 3 3
    0 1 1 2 2
    2
    12
    6 7 3 4 5
    8 9 3 4 5
    10 11 3 4 5
    0 1 2
    0 1 2
    0 1 2
    0 9 11
    0 8 10
    1 7 11
    1 6 10
    2 7 9
    2 6 8

### Extended Multipole BA formát (EMBA) ###
Tento formát v sebe obsahuje informáciu o tom, aká je štruktúra vnútri multipólu.
Prvá časť formátu obsahuje informáciu totožnú s formátom oldBA a potom nasleduje 
informácia o multipóloch:

    pocet_grafov
    for i in pocet_grafov:
        index_grafu
        velkost_grafu, pocet multipolov
        for j in velkost_grafu:
            susedia_vrchola = input().split()
        for j in pocet_multipolov:
            meno_multipolu
            vrcholy_multipolu = input().split()
            pocty_v_konektoroch = input().split()
            hrany_podla_konektorov = input().split(",").split()

Hrany_podla_konektorov obsahuje hrany zapisané ako dve čísla oddelené medzerou.
Hrany su oddelené čiarkou a sú usporiadané podľa toho ako sú v konektoroch s tým, že
počty hrán v jednom konektore sú dané premennou pocty_v_konektoroch

Ukážka súboru

    1
    1
    10 2
    1 4 5
    0 2 6
    1 3 7
    2 4 8
    3 0 9
    0 7 8 8
    1 8 9 8
    2 9 5
    3 5 6 6 5
    4 6 7
    M2
    0 1
    2 2
    0 4, 1 2, 0 5, 1 6
    M1
    8 9
    8
    8 3, 8 5, 8 5, 8 6, 8 6, 9 4, 6 9, 7 9


### Simple Multipole BA formát (SMBA) ###
Tento formát je jednoduchší v tom, že neobsahuje informáciu o obsahu multipólu.
To, či je vrchol multipól je dané tým, či na konci riadka je, alebo nie je meno
zapísané ako reťazec. Informácia o incidenií je usporiadaná podľa konektorov a
jednotlivé konektory sú oddelené čiarkou.

    pocet_grafov
    for i in pocet_grafov:
        index_grafu
        velkost_grafu
        for j in velkost_grafu:
            if (input() na konci ma string):
                multipol = input().split(",").split(), input().koniec()
            else:
                vrchol = input().split()

Ukážka súboru

    2
    1
    4
    1 2 3
    0,2 2,3 3 N1
    0,1 1,3 3 N2
    0,1 1,2 2 N3
    2
    4
    1 2 3
    3 3,0 2 2 T1
    3 3,0 1 1 T2
    0,1 1,2 2 N

Funkcionality
--------------
Aplikácia po spustení otvorí okno s tabom s možnými funkcionalitami. Medzi ne patrí 
načítanie súboru, zmena 'zaviazania' medzivrcholov hrán a posledná funkcionalita je
prepínanie medzi grafmi vrámci súboru.

### Zobrazovanie hrany ###
Reprezentácia hrán v grafe iba čisto ako čiaru medzi vrcholmi má nevýhodu v tom,
že znemožňuje lepšiu manipulácia s hranami a pri duplicitných hranách sa zlievajú.
Tieto ťažkosti som vyriešil reprezentovaním hrany ako dvoch čiar so stredovým bodom,
ktorý sa vie hýbať samostatne. Tým vieme zabezpečiť, aby sa duplicitné hrany neprekrývali,
aby sme vedeli krajšie usporiadať hrany a navyše tým vieme farebne prideliť hranu
do konektora. Stačí totiž tú polovicu hrany, ktorá je bližšie k vrcholu zafarbiť
hranou daného konektora.

![Obrázok grafu aj s prvkami a konektoromt](docs/romulo.png?raw=true "Obrázok grafu aj s prvkami a konektorom")

### Zaviazanie hrany ###
Reprezentácia hrany so stredným vrcholom má nevýhodu v tom, že pri posúvaní vrchola
ostáva stredný vrchol stáť a to robí citeľne graf neprehľadnejším. Preto som pridal
možnosť, aby sa stredný vrchol hýbal spolu s krajným vrcholmi a teda by bol zafixovaný.
Tento mód sa dá striedať s normálnym módom klinutím na príslušné tlačidlo.
Pri prepnutí do zaviazaného módu sa stredné vrcholy premiestnia na pozíciu skoro medzi
2 krajné vrcholy. Nie presne preto, aby ostala v grafe zachovaná informácia
o duplicitných hranách.

Štruktúra kódu
---------------------
Kód sa skladá z časti napísanej v jazyku JAVA a pomocného kódu napísaného v jazyku C++
vrámci knižnice BA-GRAPH. JAVA časť obsahuje základnú triedu Romulo, package graph, 
package format a pomocné triedy. Trieda Romulo obsauje spustiteľnú metódu main(). 
Táto trieda má na starosti prácu s GUI prvkami aplikácie a spustením nižíšich častí.
Package graph obsahuje reprezentáciu jednotlivých častí grafu. 
Session má na starosti uchovávanie informácie o aktuálne zobrazenom grafe.
Graf má na starosti odkazovať na hrany, vrcholy a zaväzovať hrany.
Node prezentuje vrcholy. Point dedí Node a reprezentuje stredný vrchol hrán.
Vertex dedí Node a reprezentuje koncové vrcholy hrán. Multipole dedí Vertex 
a obsahuje navyše informáciu o konektoroch.
Halfedge reprezentuje jednu čiaru, teda polku hrany. Edge odkazuje na dve HalfEdge.
Connector si uchováva info o HalfEdge zozname a mení im farby.

Package format obsahuje prácu s formátmi a logiku parsovania. Najzložitejšie sú triedy
EMBA a SMBA, ktoré parsujú vstupný súbor v danom formáte na model reprezentovaný cez
package graph. Ďalej sú tam triedy, DOT, CUT, BA, JSON, ktoré poskytujú pomocné
metódy pre prevádzanie do rôznych formátov.

Náčrt fungovania parsovania
------------------------------------------
Pri načítaní súboru potrebuje program parsovať vstupný súbor do modelu.
To sa deje cez niekoľko krokov pre každý graf v súbore.
- SMBA
    - Najprv vytvoria vrcholy a multipóly na základe konca riadkov
    - Potom sa vytvoria hrany, ale pri Multipoloch sa popri tom pridavaju aj do konektorov
    - Potom sa reprezentacia grafu prevedie do oldBA formátu a spustí sa na nej
  hľadanie najmenšieho rezu
    - Následne sa zparsuje súbor CUT, kde je reprezentovaný najmenší rez v grafe. 
    - Najmenší rez sa použije na to, aby sme vytvorili DOT súbor, v ktorom pre 
    najmenší rez pridáme konštantnú väčšiu váhu 
    -  DOT súbor posunieme knižnici graphviz, ktorá nám povie rozloženie vrcholov na scéne
- EMBA
    - Pri EMBA parsujeme rovnako, ale na začiatku musíme vytvoriť BA formát so všetkými vrcholmi
    - Pred tým než prejdeme ku knižnici graphviz však už musíme spojiť vrcholy do multipólov 
      aby sme ich mali reprezentovaných ako jeden vrchol.


Future goals
------------------
Projekt má v sebe viacero nedostatkov. V prvom rade nedostatočne overuje vstup
a nerobí logovanie, čo zpodstatne zťažuje analýzu a hľadanie chýb.
Iné nedostatky má projekt napr. v coding conventions alebo v neplnení princípov
dobrého dizajnu. Model má veľkú previazanosť, nízku čitateľnosť, slabú abstrakciu.

Záver
------------------
Z hľadiska funkčnosti bol naplnený cieľ do väčšej miery. Základné funkčné požiadavky 
sa splnili, avšak v realite môže dôjsť k problémom, ktoré znemožnia správnu funkčnosť.
Jednoduchosť práce s aplikáciou bola splnená.
Kompatibilita nebola naplnená, pretože program má v requirements knižnicu ba-graph 
a kničnicu graphviz. Tieto požiadavky sú v praxi na systéme Windows nesplniteľné.
Avšak na systéme windows poskytuje aspoň základné grafové rozhranie bez možnosti 
prvotného rozloženie vrcholov. 
Udržateľnosť projektu hodnotím ako dostatočnú rozsahu projektu. 

## Report zo zimného semestra


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

![Príklad grafu s využitím multipólov](docs/obr.png?raw=true "Príklad grafu s využitím multipólov")

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
