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
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.Logitech.DPAD;
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
  //public XboxController xboxController = new XboxController(1);
  public Logitech logi = new Logitech(1);
  
 // public JoystickButton autoCenter = new JoystickButton(leftJoystick, 2);
 // public JoystickButton runShooter = new JoystickButton(leftJoystick, 7);
 // public JoystickButton scorePowerCell = new JoystickButton(leftJoystick, 8);
  public JoystickButton runIntake = new JoystickButton(logi, 7);
  public JoystickButton runIntakeOut = new JoystickButton(logi, 5);
 // public JoystickButton extendIntake = new JoystickButton(logi, 0);
 // public JoystickButton retractIntake = new JoystickButton(logi, 180);

  public JoystickButton runShooter = new JoystickButton(logi, 6);
  public JoystickButton runShooterDPADTEMPORARY = new JoystickButton(logi, 0);

  //public JoystickButton runBallFeed = new JoystickButton(logi, XboxController.Button.kBumperLeft.value);
  public JoystickButton runShooterFeed = new JoystickButton(logi, 8);
  public JoystickButton runShooterFeedDPADTEMPORARY = new JoystickButton(logi, 180);




//  public JoystickButton runRightClimberButtonDown = new JoystickButton(xboxController, XboxController.Button.kA.value);
 // public JoystickButton runRightClimberButtonUp = new JoystickButton(xboxController, XboxController.Button.kB.value);
//  public JoystickButton runLeftClimberButtonDown = new JoystickButton(xboxController, XboxController.Button.kX.value);
  //public JoystickButton runLeftClimberButtonUp = new JoystickButton(xboxController, XboxController.Button.kY.value);


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
    runShooter.toggleWhenActive(new RunShooter(shooter, 1.0, -1.0));
    runShooterDPADTEMPORARY.whileHeld(new RunShooter(shooter, 1.0, -1.0));
    runShooterFeedDPADTEMPORARY.whileHeld(new RunShooterFeed(ballFeed, 1.0));
    runShooterFeed.whileHeld(new RunShooterFeed(ballFeed, 1.0));
    //extendIntake.whenPressed(new ExtendIntake(intake));
  //  retractIntake.whenPressed(new RetractIntake(intake));

  //  runBallFeed.whileHeld(new RunBallFeed(ballFeed, -1.0));
  //  runShooterFeed.whileHeld(new RunShooterFeed(ballFeed, 1.0));

  //  scorePowerCell.whenActive(new ShootPowerCell(intake, ballFeed, drivetrain, shooter));
    runIntake.whileHeld(new RunIntake(intake, 1.0));
    runIntakeOut.whileHeld(new RunIntake(intake, -1.0));


  //  runRightClimberButtonUp.whileHeld(new RunClimberRight(climber, 0.25));
 //   runRightClimberButtonDown.whileHeld(new RunClimberRight(climber, -0.25));
   // runLeftClimberButtonUp.whileHeld(new RunClimberLeft(climber, 0.25));
 //   runLeftClimberButtonDown.whileHeld(new RunClimberLeft(climber, -0.25));

    SmartDashboard.putData("Extend Intake", new PneumaticIntake(intake, true));
    SmartDashboard.putData("Retract Intake", new PneumaticIntake(intake, false));
    // SmartDashboard.putNumber("Shooter Encoder One Value", shooter.getShooterOneEncoderValue());
    // SmartDashboard.putNumber("Shooter Encoder Two Value", shooter.getShooterTwoEncoderValue());
    // SmartDashboard.putNumber("Shooter Encoder One Velocity", shooter.getShooterOneEncoderVelocity());
    // SmartDashboard.putNumber("Shooter Encoder Two Velocity", shooter.getShooterTwoEncoderVelocity());

    SmartDashboard.putData("Score Power Cell", new ShootPowerCell(intake, ballFeed, drivetrain, shooter));
    //runShooterAndBallFeed.whenActive(new RunShooterFeed(ballFeed, 0.25), new RunBallFeed(ballFeed, 0.25));
    SmartDashboard.putData(drivetrain);
    SmartDashboard.putData("Run Intake", new RunIntake(intake, 1.0));
    SmartDashboard.putData("Run Shooter", new RunShooter(shooter, 1.0 , -1.0));

    
    SmartDashboard.putData("Run Shooter One", new RunShooterOne(shooter, 1.0));
    
    SmartDashboard.putData("Run Shooter Two", new RunShooterTwo(shooter, -1.0));
    SmartDashboard.putData("Run Climber One", new RunClimberLeft(climber, 0.35));
    SmartDashboard.putData("Stop Climber", new StopClimber(climber));
    SmartDashboard.putData("Reset Shooter Encoders", new ResetShooterEncoders(shooter));
    SmartDashboard.putData("Run Climber Two", new RunClimberRight(climber, -0.35));
    SmartDashboard.putData("Run Climber", new RunClimber(climber, -0.35, 0.35));
    Shuffleboard.getTab("Climber").add("Run Climber", new RunClimber(climber, 0.35, -0.35));


    SmartDashboard.putData("Extend Intake", new ExtendIntake(intake));
    SmartDashboard.putData("Retract Intake", new RetractIntake(intake));
    SmartDashboard.putData("Run BallFeed", new RunBallFeed(ballFeed, -0.50));
    SmartDashboard.putData("Run ShooterFeed", new RunShooterFeed(ballFeed, 1.0));
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
    System.out.print(shooter.getShooterOneEncoderValue());
    System.out.print(shooter.getShooterTwoEncoderValue());
    System.out.print(shooter.getShooterOneEncoderVelocity());
    System.out.print(shooter.getShooterTwoEncoderVelocity());

  }

  public void outputToSmartDashboard() {
    SmartDashboard.putNumber("Yaw", sensors.getHeading());
    SmartDashboard.putData("Reset Gyro", new InstantCommand(sensors::resetGyro));
    SmartDashboard.putNumber("Shooter Encoder One Value", shooter.getShooterOneEncoderValue());
    SmartDashboard.putNumber("Shooter Encoder Two Value", shooter.getShooterTwoEncoderValue());
    SmartDashboard.putNumber("Shooter Encoder One Velocity", shooter.getShooterOneEncoderVelocity());
    SmartDashboard.putNumber("Shooter Encoder Two Velocity", shooter.getShooterTwoEncoderVelocity());
    
  }
}
