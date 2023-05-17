package frc.robot.control;

import edu.wpi.first.wpilibj.XboxController;

public class ControllerManager {
    private static XboxController controller;
    public static void controllerInit(int port) {
        controller = new XboxController(port);
    }
    public static XboxController getController() {
        return controller;
    }
}
