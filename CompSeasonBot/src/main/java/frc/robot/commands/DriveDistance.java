/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;


public class DriveDistance extends CommandBase {
  double distance;
  final double encoderFeet = 803;
  final double encoderInches = encoderFeet / 12;
  Drivetrain drivetrain;
  /**
   * Creates a new DriveDistance. The distance is in inches
   */
  public DriveDistance(Drivetrain drivetrain, double distance) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);
    this.distance = distance * encoderInches;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(drivetrain.frontLeft.getSelectedSensorPosition() * -1 < distance) {
      drivetrain.drive(.25, .25);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(drivetrain.frontLeft.getSelectedSensorPosition() * -1 >= distance) {
      return true;
    } else {
      return false;
    }
  }
}
