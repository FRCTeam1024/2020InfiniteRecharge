/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DriveWithJoysticks extends CommandBase {

  Joystick leftJoystick;
  Joystick rightJoystick;

  private final Drivetrain drivetrain;
  /**
   * Creates a new DriveWithJoysticks.
   */
  public DriveWithJoysticks(Drivetrain drivetrain, Joystick leftJoystick, Joystick rightJoystick) {
    this.drivetrain = drivetrain;
    this.leftJoystick = leftJoystick;
    this.rightJoystick = rightJoystick;
    
    rightJoystick = new Joystick(0);
    leftJoystick = new Joystick(2);
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //THESE ARE INVERSE OF WHAT THEY SHOULD BE, ALLISON JUST WANTED THEM THAT WAY FOR TESTING
    drivetrain.drive(-leftJoystick.getY(), -rightJoystick.getY());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
