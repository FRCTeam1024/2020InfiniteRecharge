/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveWithJoysticks;
import frc.robot.commands.auto.LimelightCenter;
import frc.robot.commands.auto.LimelightCenterPID;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.PIDDrivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain drivetrain = new Drivetrain();
  // private final PIDDrivetrain drivetrainPID = new PIDDrivetrain();

  public Joystick leftJoystick = new Joystick(0);
  public Joystick rightJoystick = new Joystick(1);

  private final Command m_autoCommand = new LimelightCenter(drivetrain);
  // private final Command limelightCenterPID = new LimelightCenterPID(drivetrainPID);
  private final DriveWithJoysticks driveWithJoysticks = new DriveWithJoysticks(drivetrain, leftJoystick, rightJoystick);
  

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    drivetrain.setDefaultCommand(driveWithJoysticks);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // SmartDashboard.putData("Limelight Center PID", new InstantCommand(drivetrainPID::enable, drivetrainPID));

    NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry xOffset = limelight.getEntry("tx");
    PIDController pidController = new PIDController(PIDDrivetrain.Kp, PIDDrivetrain.Ki, PIDDrivetrain.Kd);
    SmartDashboard.putData("Limelight Center PID", new PIDCommand(
        pidController, 
        () -> { 
          System.out.println("xOffset : " + xOffset.getDouble(0.0));
          return xOffset.getDouble(0.0);
        }, 
        0.0, 
        output -> {
          System.out.println("output : " + output);
          double minPower = .2;
          double maxPower = 0.95;
          double tolerance = .2;
          double finalPower = minPower + Math.abs(output);
          if(finalPower < minPower) {
            finalPower = minPower;
          }
          if(finalPower > maxPower) {
            finalPower = maxPower;
          }
          if(Math.abs(xOffset.getDouble(0.0)) <= tolerance) {
            if(Math.abs(xOffset.getDouble(0.0)) < tolerance +.05 ) {
              finalPower = 0;
            }
          }
          System.out.println("finalPower : " + finalPower);
          if(output < 0) { // we want to turn right
            drivetrain.drive(finalPower, -finalPower);
          } else {
            drivetrain.drive(-finalPower, finalPower);
          }
          
        }, 
        drivetrain));

    SmartDashboard.putData("Move Command", new StartEndCommand(
                              // start driving forward
                              () -> drivetrain.driveForward(.25),
                              // stop driving
                              () -> drivetrain.stop(),
                              // subsystem requirements here 
                              drivetrain)
                              // this is a command decorator; a convenience method
                            .withTimeout(1));

    SmartDashboard.putData("Turn Command", new StartEndCommand(
                              // start doing a turn
                              () -> drivetrain.turnRight(.50),
                              // stop driving
                              () -> drivetrain.stop(),
                              // subsystem requirements here 
                              drivetrain)
                              // this is a command decorator; a convenience method
                            .withTimeout(1));

    // example of an in-line, sequential command
    // this is another way to do a CommandGroup of sequential commands
    SmartDashboard.putData("MoveTurn Command", new StartEndCommand(
                              // start driving forward
                              () -> drivetrain.driveForward(.30),
                              // stop driving
                              () -> drivetrain.stop(),
                              // subsystem requirements here 
                              drivetrain)
                              // this is a command decorator; a convenience method
                            .withTimeout(1)
                            .andThen(new StartEndCommand(
                              // start doing a turn
                              () -> drivetrain.turnRight(.50),
                              // stop driving
                              () -> drivetrain.stop(),
                              // subsystem requirements here 
                              drivetrain)
                              // this is a command decorator; a convenience method
                            .withTimeout(1)));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
    // return limelightCenterPID;
  }
}
