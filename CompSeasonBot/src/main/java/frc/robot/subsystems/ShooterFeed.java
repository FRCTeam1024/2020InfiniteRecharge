/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// aka the blue wheel that feeds the shooter
public class ShooterFeed extends SubsystemBase {

  private WPI_TalonSRX shooterFeedMotor; // this is the small blue one
  
  /**
   * Creates a new ShooterFeed.
   */
  public ShooterFeed() {
    shooterFeedMotor = new WPI_TalonSRX(13);
  }

  public void runShooterFeedMotor(double motorSpeed){
    shooterFeedMotor.set(motorSpeed);
  }
  
  public void stopShooterFeedMotor(){
    shooterFeedMotor.set(0.0);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
