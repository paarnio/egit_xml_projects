2016-06-27
JAXB examples from
(https://docs.oracle.com/javase/tutorial/jaxb/intro/examples.html) 
utilize jaxb_ri loaded from: https://jaxb.java.net/
and stored into C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri
HUOM:
Lähdekooditiedostot generoidaan ANT työkalulla
----------------------------------------------
Sample files in (C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri

\samples) are build using ant:
---
C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\samples\modify-

marshal
>ant
Buildfile: C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\samples

\mod
ify-marshal\build.xml

compile:
     [echo] Compiling the schema...
    [mkdir] Created dir: C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-

2.2.11\jaxb-
ri\samples\modify-marshal\gen-src
      [xjc] C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri

\samples\mo
dify-marshal\gen-src\primer.po is not found and thus excluded from the 

dependenc
y check
      [xjc] Compiling file:/C:/D-PA/programs/java/2016/JAXB_ri/jaxb-ri-

2.2.11/ja
xb-ri/samples/modify-marshal/po.xsd
      [xjc] Writing output to C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-

2.2.11\
jaxb-ri\samples\modify-marshal\gen-src
     [echo] Compiling the java source files...
    [mkdir] Created dir: C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-

2.2.11\jaxb-
ri\samples\modify-marshal\classes
    [javac] C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri

\samples\mo
dify-marshal\build.xml:30: warning: 'includeantruntime' was not set, 

defaulting
to build.sysclasspath=last; set to false for repeatable builds
    [javac] Compiling 5 source files to C:\D-PA\programs\java\2016\JAXB_ri

\jaxb-
ri-2.2.11\jaxb-ri\samples\modify-marshal\classes

run:
     [echo] Running the sample application...
     [java] <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     [java] <purchaseOrder orderDate="1999-10-20">
     [java]     <shipTo country="US">
     [java]         <name>Alice Smith</name>
     [java]         <street>123 Maple Street</street>
     [java]         <city>Cambridge</city>
     [java]         <state>MA</state>
     [java]         <zip>12345</zip>
     [java]     </shipTo>
     [java]     <billTo country="US">
     [java]         <name>John Bob</name>
     [java]         <street>242 Main Street</street>
     [java]         <city>Beverly Hills</city>
     [java]         <state>CA</state>
     [java]         <zip>90210</zip>
     [java]     </billTo>
     [java]     <items>
     [java]         <item partNum="242-NO">
     [java]             <productName>Nosferatu - Special Edition (1929)

</product
Name>
     [java]             <quantity>5</quantity>
     [java]             <USPrice>19.99</USPrice>
     [java]         </item>
     [java]         <item partNum="242-MU">
     [java]             <productName>The Mummy (1959)</productName>
     [java]             <quantity>3</quantity>
     [java]             <USPrice>19.98</USPrice>
     [java]         </item>
     [java]         <item partNum="242-GZ">
     [java]             <productName>Godzilla and Mothra: Battle for 

Earth/Godzi
lla vs. King Ghidora</productName>
     [java]             <quantity>3</quantity>
     [java]             <USPrice>27.95</USPrice>
     [java]         </item>
     [java]     </items>
     [java] </purchaseOrder>

BUILD SUCCESSFUL
Total time: 2 seconds
C:\D-PA\programs\java\2016\JAXB_ri\jaxb-ri-2.2.11\jaxb-ri\samples\modify-

marshal
>

