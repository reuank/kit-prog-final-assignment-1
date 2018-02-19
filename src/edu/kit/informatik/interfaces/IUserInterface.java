package edu.kit.informatik.interfaces;

public interface IUserInterface {
    void registerCommands(ICommand[] commands);
    void init();
    void start();
    void stop();
}
