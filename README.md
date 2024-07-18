# Tetris Installation Options

There are two ways to play this game. There's a simple method and a more complex method that involves more steps and software installed. 

1. Download executable and use JRE that supports the program
2. Compile repository code and run it

Both installation options are detailed below.

## Option 1 - Download executable and run with supported JRE

0. Install JRE (Java Runtime Environment) to run the program if it already isn't installed.
1. Download the executable located in the root directory of this repository. It's called `Tetris.exe`. 

Now all you have to do is click on the executable and it should run. 

## Option 2 - Compile repository code and run it

The following set of instructions will only be useful for developers using WSL (or Ubuntu and possibly some other Linux distributions) and VSCode. 

1. Install JDK
```
sudo apt install openjdk-11-jdk
```

This will install java version 11.

2. Install Java Extension Pack

Install the `Extension Pack for Java` in VSCode. It's an official extension made by Microsoft. 

