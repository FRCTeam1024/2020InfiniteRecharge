/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import frc.robot.commands.auto.LimelightCenter;
import frc.robot.oi.CONSTANTS_OI;
import frc.robot.oi.Logitech;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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


  public Joystick leftJoystick = new Joystick(0);
  public Joystick rightJoystick = new Joystick(1);
  public Logitech xboxController = new Logitech(2);

  
 // public JoystickButton runShooterAndBallFeed = new JoystickButton(leftJoystick, 6);

  
  // below this done with Marc
  public JoystickButton xboxLeftTrigger = new JoystickButton(xboxController, CONSTANTS_OI.XBOX_LEFT_TRIGGER);
  public JoystickButton xboxLeftBumper = new JoystickButton(xboxController, XboxController.Button.kBumperLeft.value);


  public JoystickButton xboxLeftClimberStick = new JoystickButton(xboxController, XboxController.Button.kStickLeft.value);
  public JoystickButton xboxRightClimberStick = new JoystickButton(xboxController, XboxController.Button.kStickRight.value);
  
  public JoystickButton xboxButtonA = new JoystickButton(xboxController, XboxController.Button.kA.value);
  public JoystickButton xboxButtonB = new JoystickButton(xboxController, XboxController.Button.kB.value);
  
  public JoystickButton xboxRightBumper = new JoystickButton(xboxController, XboxController.Button.kBumperRight.value);
  public JoystickButton xboxRightTrigger = new JoystickButton(xboxController, CONSTANTS_OI.XBOX_RIGHT_TRIGGER);
  
  public Trigger xboxDPadUp = new Trigger( () -> xboxController.getDPadState().equals(Logitech.DPadState.UP));
  public Trigger xboxDPadDown = new Trigger( () -> xboxController.getDPadState().equals(Logitech.DPadState.DOWN));

  public JoystickButton ballFeedOut = new JoystickButton(xboxController, CONSTANTS_OI.XBOX_BACK_BUTTON);
  public JoystickButton xboxStartButton = new JoystickButton(xboxController, CONSTANTS_OI.XBOX_START_BUTTON);


  private final Command m_autoCommand = new LimelightCenter(drivetrain);
  private final DriveWithJoysticks driveWithJoysticks = new DriveWithJoysticks(drivetrain, leftJoystick, rightJoystick);
  private final DriveClimberDefault driveClimberDefault = new DriveClimberDefault(climber, xboxController);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    drivetrain.setDefaultCommand(driveWithJoysticks);
    climber.setDefaultCommand(driveClimberDefault);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    
    // autoCenter.whileHeld(m_autoCommand);
    
    // NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
    // NetworkTableEntry xOffset = limelight.getEntry("tx");

    // below done with Marc

    xboxButtonB.whileHeld(new RunClimberHook(climber, 0.50));
    xboxButtonA.whileHeld(new RunClimberHook(climber, -0.50));

    xboxLeftClimberStick.whenActive(new RunClimberLeft(climber, xboxController.getRawAxis(CONSTANTS_OI.XBOX_LEFT_STICK_Y_AXIS)));
    xboxRightClimberStick.whenActive(new RunClimberRight(climber, xboxController.getRawAxis(CONSTANTS_OI.XBOX_RIGHT_STICK_Y_AXIS)));

    xboxLeftTrigger.whileHeld(new RunIntake(intake, 1.0));
    // when intake is done and we want to run it with ball feeder, replace above line with this
    // runIntakeIn.whileHeld(new ParallelCommandGroup(new RunIntake(intake, 1.0), 
    //                                               new RunBallFeed(ballFeed, -1.0)));

    xboxLeftBumper.whileHeld(new RunIntake(intake, -1.0));

    xboxDPadUp.toggleWhenActive(new ExtendIntake(intake));
    xboxDPadDown.toggleWhenActive(new RetractIntake(intake));

    xboxRightBumper.toggleWhenPressed(new RunShooter(shooter, 1.0));
    xboxRightTrigger.whileHeld(new RunShooterFeed(ballFeed, 1.0));

    xboxStartButton.whileHeld(new RunBallFeed(ballFeed, -1.0));
    ballFeedOut.whileHeld(new RunBallFeed(ballFeed, 1.0));

    
    SmartDashboard.putData("Score Power Cell", new ShootPowerCell(intake, ballFeed, drivetrain, shooter));
    //runShooterAndBallFeed.whenActive(new RunShooterFeed(ballFeed, 0.25), new RunBallFeed(ballFeed, 0.25));
    SmartDashboard.putData(drivetrain);
    SmartDashboard.putData("Run Intake", new RunIntake(intake, 0.25));

    SmartDashboard.putData("Run Shooter", new RunShooter(shooter, 1.0));

    SmartDashboard.putData("Run Climber One", new RunClimberLeft(climber, 0.35));
    SmartDashboard.putData("Stop Climber", new StopClimber(climber));

    SmartDashboard.putData("Run Climber Two", new RunClimberRight(climber, -0.35));
    SmartDashboard.putData("Run Climber", new RunClimber(climber, -0.35, 0.35));
    Shuffleboard.getTab("Climber").add("Run Climber", new RunClimber(climber, 0.35, -0.35));


    SmartDashboard.putData("Extend Intake", new ExtendIntake(intake));
    SmartDashboard.putData("Retract Intake", new RetractIntake(intake));
    SmartDashboard.putData("Run BallFeed", new RunBallFeed(ballFeed, -0.50));
    SmartDashboard.putData("Run ShooterFeed", new RunShooterFeed(ballFeed, 1.0));
    SmartDashboard.putData("Drive", new BasicDriveCommand(drivetrain));

    Shuffleboard.getTab("Shooter").add("Run Shooter PID", new RunShooterPID(shooter));
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
