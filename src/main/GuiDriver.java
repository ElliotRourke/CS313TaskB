package main;

public class GuiDriver {
    public static void main(String[] args){
        main.ThreadDriver td = new ThreadDriver();
        GUI guiDriver = new GUI(td);
    }
}
