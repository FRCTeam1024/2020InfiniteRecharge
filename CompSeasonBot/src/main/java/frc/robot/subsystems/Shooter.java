/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private CANSparkMax shooterOne;
  private CANSparkMax shooterTwo;
  private CANEncoder shooterEncoderOne;
  private CANEncoder shooterEncoderTwo;

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    shooterOne = new CANSparkMax(39, MotorType.kBrushless);
    shooterTwo = new CANSparkMax(37, MotorType.kBrushless);
    shooterEncoderOne = shooterOne.getEncoder();
    shooterEncoderTwo = shooterTwo.getEncoder();
  }


  public void runShooterMotors(double motorOneSpeed, double motorTwoSpeed){
    shooterOne.set(motorOneSpeed);
    shooterTwo.set(motorTwoSpeed);
  }
  
  public void stopShooterMotors(){
    shooterOne.set(0.0);
    shooterTwo.set(0.0);
  }
  public void runShooterMotorsUntil(double motorOneSpeed, double motorTwoSpeed, double encoderSetpoint){
    if(shooterEncoderOne.getPosition() < encoderSetpoint && shooterEncoderTwo.getPosition() < encoderSetpoint){
      shooterOne.set(motorOneSpeed);
      shooterTwo.set(motorTwoSpeed);
    } else if(shooterEncoderOne.getPosition() >= encoderSetpoint && shooterEncoderTwo.getPosition() >= encoderSetpoint){
      stopShooterMotors();
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
