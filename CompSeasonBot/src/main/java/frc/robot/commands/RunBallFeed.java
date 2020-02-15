/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RunBallFeed extends CommandBase {
  /**
   * Creates a new RunBallFeed.
   */
  BallFeed ballFeed;
  public RunBallFeed(BallFeed ballFeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.ballFeed = ballFeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ballFeed.runBallFeedMotor(0.25);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ballFeed.stopBallFeedMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}