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

1. Generate a JAR file. 

On the bottom left of the explorer tab in VSCode, there should be a section called `JAVA PROJECTS`. If you hover over that tab, then there should be an icon that allows you to export as a JAR file. 

2. Install `Launch4j`. Here's the official site: https://launch4j.sourceforge.net/. On the left side bar there is a link where you can download the executable. 

3. Set up configurations.

There are two main configurations you have to set up:
1. Output file: specify the path and name for the output `.exe` file
2. Jar: specify the JAR file

Optionally, you can specify an icon for the executable by providing an `.ico` file. 

There are plenty of other options to consider too when making the executable file, but those are the main ones that I really considered. 

3. Build the wrapper

Click the `Build wrapper` button. It should resemble a gear. If it gives you an error asking for the config, first save the file (it should be an XML file) and then when it asks you for the config again, provide the config. 

Now you should have an executable file of your Java project!
