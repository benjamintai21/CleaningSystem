@echo off
echo Cleaning bin directory...
if exist bin\* del /Q /S bin\*

echo Creating bin directory structure...
mkdir bin\model 2>nul
mkdir bin\DAO 2>nul
mkdir bin\db 2>nul
mkdir bin\dto 2>nul
mkdir bin\controller 2>nul

echo Compiling db package...
javac -d bin src/db/DatabaseConnection.java

echo Compiling dto package...
javac -d bin src/dto/UserDataDTO.java
javac -d bin src/dto/UserProfileDTO.java

echo Compiling model package...
javac -cp bin -d bin src/model/ServiceListing.java
javac -cp bin -d bin src/model/UserProfile.java
javac -cp bin -d bin src/model/UserAccount.java
javac -cp bin -d bin src/model/Cleaner.java
javac -cp bin -d bin src/model/HomeOwner.java
javac -cp bin -d bin src/model/UserAdmin.java

echo Compiling DAO package...
javac -cp "bin;mysql-connector-j-9.2.0.jar" -d bin src/DAO/UserProfileDAO.java
javac -cp "bin;mysql-connector-j-9.2.0.jar" -d bin src/DAO/UserAccountDAO.java

echo Compiling controller package...
javac -cp "bin;mysql-connector-j-9.2.0.jar" -d bin src/controller/UserProfileController.java

echo Compiling Main...
javac -cp "bin;mysql-connector-j-9.2.0.jar" -d bin src/model/Main.java

echo Build complete! 