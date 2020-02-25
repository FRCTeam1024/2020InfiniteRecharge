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
import frc.robot.oi.MustangController;
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
  public MustangController xboxController = new MustangController(2);

  
  public JoystickButton autoCenter = new JoystickButton(leftJoystick, 2);
  public JoystickButton runShooter = new JoystickButton(leftJoystick, 7);
  public JoystickButton scorePowerCell = new JoystickButton(leftJoystick, 8);
  public JoystickButton runIntake = new JoystickButton(leftJoystick, 9);
  public JoystickButton runShooterAndBallFeed = new JoystickButton(leftJoystick, 6);

  // JoystickButton leftTriggerButton = new JoystickButton(xboxController, (xboxController) -> xboxController.getTriggerAxis(Hand.kLeft) >= 0.5);

  public JoystickButton runLeftClimberButtonDown = new JoystickButton(xboxController, XboxController.Button.kX.value);
  public JoystickButton runLeftClimberButtonUp = new JoystickButton(xboxController, XboxController.Button.kY.value);
  
  // below this done with Marc
  public JoystickButton runLeftClimberStick = new JoystickButton(xboxController, XboxController.Button.kStickLeft.value);
  public JoystickButton runRightClimberStick = new JoystickButton(xboxController, XboxController.Button.kStickRight.value);
  
  public JoystickButton runClimberHookDown = new JoystickButton(xboxController, XboxController.Button.kA.value);
  public JoystickButton runClimberHookUp = new JoystickButton(xboxController, XboxController.Button.kB.value);
  
  public JoystickButton runShooterWheel = new JoystickButton(xboxController, XboxController.Button.kBumperLeft.value);
  public JoystickButton runShooterFeedWheel = new JoystickButton(xboxController, XboxController.Button.kBumperRight.value);
  
  // public JoystickButton intakeIn = new JoystickButton(xboxController, xboxController.getDPadState());
  public Trigger intakeUp = new Trigger( () -> xboxController.getDPadState().equals(MustangController.DPadState.UP));
  public Trigger intakeDown = new Trigger( () -> xboxController.getDPadState().equals(MustangController.DPadState.DOWN));

  public Trigger shooterTrigger = new Trigger( () -> xboxController.getLeftTriggerAxis() > 0.50 );


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
    runShooter.whenActive(new RunShooter(shooter, 1.0));
    scorePowerCell.whenActive(new ShootPowerCell(intake, ballFeed, drivetrain, shooter));
    runIntake.whenActive(new RunIntake(intake, 0.25));

    // below done with Marc
    runClimberHookUp.whileHeld(new RunClimberHook(climber, 0.25));
    runClimberHookDown.whileHeld(new RunClimberHook(climber, -0.25));

    runLeftClimberButtonUp.whileHeld(new RunClimberLeft(climber, 0.25));
    runLeftClimberButtonDown.whileHeld(new RunClimberLeft(climber, -0.25));

    runShooterWheel.toggleWhenPressed(new RunShooter(shooter, 1.0));
    runShooterFeedWheel.whileHeld(new RunShooterFeed(ballFeed, 1.0));

    // intakeUp.whenActive(new RetractIntake(intake));
    intakeUp.toggleWhenActive(new RunShooterFeed(ballFeed, 1.0));  // just testing whether dpad works
    // intakeDown.whenActive(new ExtendIntake(intake));
    intakeDown.toggleWhenActive(new RunShooterFeed(ballFeed, 1.0)); // just testing whether dpad works

    shooterTrigger.whileActiveContinuous(new RunShooterFeed(ballFeed, 1.0)); // just testing trigger

    SmartDashboard.putData("Score Power Cell", new ShootPowerCell(intake, ballFeed, drivetrain, shooter));
    //runShooterAndBallFeed.whenActive(new RunShooterFeed(ballFeed, 0.25), new RunBallFeed(ballFeed, 0.25));
    SmartDashboard.putData(drivetrain);
    SmartDashboard.putData("Run Intake", new RunIntake(intake, 0.25));

    SmartDashboard.putData("Run Shooter", new RunShooter(shooter, 1.0));
    // SmartDashboard.putData("Run Shooter One", new RunShooterOne(shooter, 1.0));    
    // SmartDashboard.putData("Run Shooter Two", new RunShooterTwo(shooter, -1.0));

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
