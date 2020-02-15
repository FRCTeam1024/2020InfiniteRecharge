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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import frc.robot.commands.auto.LimelightCenter;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain drivetrain = new Drivetrain();
  private final Sensors sensors = new Sensors();
  private final Intake intake = new Intake();
  private final Shooter shooter = new Shooter();
  private final Climber climber  = new Climber();
  private final BallFeed ballFeed  = new BallFeed();


  public Joystick leftJoystick = new Joystick(2);
  public Joystick rightJoystick = new Joystick(0);

  public JoystickButton autoCenter = new JoystickButton(leftJoystick, 2);

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
    
    // autoCenter.whileHeld(m_autoCommand);
    
    NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry xOffset = limelight.getEntry("tx");
    
    SmartDashboard.putData(drivetrain);
    SmartDashboard.putData("Run Shooter", new RunShooter(shooter));
    SmartDashboard.putData("Run Climber", new RunClimber(climber));
    SmartDashboard.putData("Extend Intake", new ExtendIntake(intake));
    SmartDashboard.putData("Retract Intake", new RetractIntake(intake));
    SmartDashboard.putData("Run BallFeed", new RunBallFeed(ballFeed));
    SmartDashboard.putData("Run ShooterFeed", new RunShooterFeed(ballFeed));
    SmartDashboard.putData("Drive", new BasicDriveCommand(drivetrain));

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

  public void periodic() {
    outputToSmartDashboard();
  }

  public void outputToSmartDashboard() {
    SmartDashboard.putNumber("Yaw", sensors.getHeading());
    SmartDashboard.putData("Reset Gyro", new InstantCommand(sensors::resetGyro));
    
  }
}
