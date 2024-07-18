# How to play

Keybinds:
A = rotate CCW
S = drop the tetrimino down
Q = hold the tetrimino

Use left and right arrow keys to shift the block.

These keybinds are probably not ideal, so if you're interested, I would recommend you look through the file `Visual.java` if you want to modify it. 

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
sudo apt-get install default-jdk
```

This will install a version of Java. To check what version, you can run:
```
java -version
```

2. Install Java Extension Pack

Install the `Extension Pack for Java` in VSCode. It's an official extension made by Microsoft. 

3. Run the program

Make sure that you're on `Main.java` and then click on the arrow button on the top right. It will compile the code and run the program.

## Create an executable

This section is just a reference for how I made an executable from this Java project. 
