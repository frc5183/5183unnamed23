package frc.robot.control;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.EventLoop;
import frc.robot.RobotMap;

public class ControllerManager {
    private static XboxController mainController;
    public static void init() {
        mainController = new XboxController(RobotMap.CONTROLLER_PORT);
    }

    public static XboxController getMainController() {
        return mainController;
    }
}
