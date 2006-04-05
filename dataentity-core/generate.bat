@echo off

set VELCP=./target/classes
set VEL_HOME=c:\Java\velocity-1.4

for %%i in (%VEL_HOME%\bin\*.jar) do call appendVELCP %%i
for %%i in (%VEL_HOME%\bin\lib\*.jar) do call appendVELCP %%i
call appendVELCP .\bin

echo Using classpath:  %VELCP%

java -cp %VELCP% -Dcom.codegenerator.outputpath="target/autosrc" org.mikis.dataentity.datamodel.generator.Generator src\test\resources\testentity.xml XML PSMVelocityExporter

