// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.control.ControllerManager;


/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
    public WPI_VictorSPX motorFrontLeft;
    public WPI_VictorSPX motorFrontRight;
    public WPI_VictorSPX motorBackLeft;
    public WPI_VictorSPX motorBackRight;
    public MotorControllerGroup motorLeft;
    public MotorControllerGroup motorRight;
    public DifferentialDrive drive;
    private static final String DEFAULT_AUTO = "Default";
    private static final String CUSTOM_AUTO = "My Auto";
    private String autoSelected;
    private final SendableChooser<String> chooser = new SendableChooser<>();
    
    
    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit()
    {
        CameraServer.startAutomaticCapture();
        CameraServer.startAutomaticCapture();
        chooser.setDefaultOption("Default Auto", DEFAULT_AUTO);
        chooser.addOption("My Auto", CUSTOM_AUTO);
        SmartDashboard.putData("Auto choices", chooser);
        motorFrontLeft = new WPI_VictorSPX(RobotMap.Motors.FRONT_LEFT);
        motorFrontRight = new WPI_VictorSPX(RobotMap.Motors.FRONT_RIGHT);
        motorBackLeft = new WPI_VictorSPX(RobotMap.Motors.BACK_LEFT);
        motorBackRight = new WPI_VictorSPX(RobotMap.Motors.BACK_RIGHT);
        motorRight = new MotorControllerGroup(motorFrontRight, motorBackRight);
        motorLeft = new MotorControllerGroup(motorBackLeft, motorFrontLeft);
        motorFrontRight.setNeutralMode(NeutralMode.Brake);
        motorFrontLeft.setNeutralMode(NeutralMode.Brake);
        motorBackRight.setNeutralMode(NeutralMode.Coast);
        motorBackLeft.setNeutralMode(NeutralMode.Coast);
        drive = new DifferentialDrive(motorLeft, motorRight);
        ControllerManager.init();
    }
    
    
    /**
     * This method is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        motorFrontLeft.feed();
        motorFrontRight.feed();
        motorBackLeft.feed();
        motorBackRight.feed();
    }
    
    
    /**
     * This autonomous (along with the chooser code above) shows how to select between different
     * autonomous modes using the dashboard. The sendable chooser code works with the Java
     * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
     * uncomment the getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to the switch structure
     * below with additional strings. If using the SendableChooser make sure to add them to the
     * chooser code above as well.
     */
    @Override
    public void autonomousInit()
    {
        autoSelected = chooser.getSelected();
        // autoSelected = SmartDashboard.getString("Auto Selector", DEFAULT_AUTO);
        System.out.println("Auto selected: " + autoSelected);
    }
    
    
    /** This method is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic()
    {
        switch (autoSelected)
        {
            case CUSTOM_AUTO:
                // Put custom auto code here
                break;
            case DEFAULT_AUTO:
            default:
                // Put default auto code here
                break;
        }
    }
    
    
    /** This method is called once when teleop is enabled. */
    @Override
    public void teleopInit() {}
    
    
    /** This method is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
        double y = -ControllerManager.getMainController().getLeftY()*0.80;
        double x = -ControllerManager.getMainController().getRightX()*0.80;
        if (y != 0 && x != 0) {
            System.out.println("Y: " + y + " X: " + x);
        }
        drive.arcadeDrive(y, x);
        if (ControllerManager.getMainController().getBButton()) {
            motorRight.set(0);
            motorLeft.set(0);
        }

        if (ControllerManager.getMainController().getLeftBumper()) {
            motorLeft.set(0);
        }

        if (ControllerManager.getMainController().getRightBumper()) {
            motorRight.set(0);
        }
    }
    
    
    /** This method is called once when the robot is disabled. */
    @Override
    public void disabledInit() {}
    
    
    /** This method is called periodically when disabled. */
    @Override
    public void disabledPeriodic() {}
    
    
    /** This method is called once when test mode is enabled. */
    @Override
    public void testInit() {}
    
    
    /** This method is called periodically during test mode. */
    @Override
    public void testPeriodic() {}
    
    
    /** This method is called once when the robot is first started up. */
    @Override
    public void simulationInit() {}
    
    
    /** This method is called periodically whilst in simulation. */
    @Override
    public void simulationPeriodic() {}
}
