/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  private CANSparkMax intakeMotor;

  // private final DoubleSolenoid intakeSolenoid = new DoubleSolenoid(0, 1);

  public Intake() {
    intakeMotor = new CANSparkMax(0, MotorType.kBrushless);
  }

  public void runIntake(double motorSpeed){
    intakeMotor.set(motorSpeed);
  }
  public void stopIntake(){
    intakeMotor.set(0.0);
  }
  public void extendIntake(){
    // intakeSolenoid.set(kForward);
  }
  public void retractIntake(){
    // intakeSolenoid.set(kReverse);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
