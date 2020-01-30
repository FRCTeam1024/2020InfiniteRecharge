/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveWithJoysticks;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.auto.LimelightCenter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
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

  public Joystick leftJoystick = new Joystick(0);
  public Joystick rightJoystick = new Joystick(1);

  private final Command m_autoCommand = new LimelightCenter(drivetrain);
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
  }
}
