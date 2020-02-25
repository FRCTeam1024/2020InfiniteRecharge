/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import org.graalvm.compiler.lir.sparc.SPARCControlFlow.ReturnOp;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private CANSparkMax shooterOne;
  private CANSparkMax shooterTwo;
  private CANEncoder shooterEncoderOne;
  private CANEncoder shooterEncoderTwo;
  public CANError canErrorOne;
  public CANError canErrorTwo;


  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    shooterOne = new CANSparkMax(39, MotorType.kBrushless);
    shooterTwo = new CANSparkMax(47, MotorType.kBrushless);
    shooterEncoderOne = shooterOne.getEncoder(EncoderType.kHallSensor, 42);
    shooterEncoderTwo = shooterTwo.getEncoder(EncoderType.kHallSensor, 42);
  }

  public void resetShooterEncoders(){
  canErrorOne = shooterEncoderOne.setPosition(0.0);
  canErrorTwo = shooterEncoderTwo.setPosition(0.0);

    if(canErrorOne == CANError.kOk){
      System.out.println("error on shooter encoder one");
    }
    if(canErrorTwo == CANError.kOk){
      System.out.println("error on shooter encoder two");
    }
  }

  public void runShooterMotors(double motorOneSpeed, double motorTwoSpeed){
    shooterOne.set(motorOneSpeed);
    shooterTwo.set(motorTwoSpeed);
  }
  
  public void runShooterOne(double motorOneSpeed){
    shooterOne.set(motorOneSpeed);
  }
  
  public void runShooterTwo(double motorTwoSpeed){
    shooterTwo.set(motorTwoSpeed);
  }
  public void stopShooterMotors(){
    shooterOne.set(0.0);
    shooterTwo.set(0.0);
  }
  public double getShooterOneEncoderValue(){
  return  shooterEncoderOne.getPosition();    
  }
  public double getShooterTwoEncoderValue(){
  return  shooterEncoderTwo.getPosition();    
  }

  public double getShooterOneEncoderVelocity(){
  return  shooterEncoderOne.getVelocity();    
  }
    
  public double getShooterTwoEncoderVelocity(){
  return  shooterEncoderTwo.getVelocity();    
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
