// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Robot extends TimedRobot {


  private XboxController controller;
  private double currentRightStickValue;
  private double currentLeftStickValue;
  public static SparkMaxConfig DefaultConfig = new SparkMaxConfig();
  public static SparkMaxConfig ReverseConfig = new SparkMaxConfig();
  private SparkMax motor2;
  private SparkMax motor1; 
  private SparkMax motor4;
  private SparkMax motor3;
  public static final int kmotor2CanID = 2;
  public static final int kmotor1CanID = 1;
  public static final int kmotor4CanID = 4;
  public static final int kmotor3CanID = 3;
  
  static {
    DefaultConfig.smartCurrentLimit(50);
    DefaultConfig.idleMode(IdleMode.kCoast);
    DefaultConfig.openLoopRampRate(1.0);
    DefaultConfig.inverted(false);
  }
static {
    ReverseConfig.smartCurrentLimit(50);
    ReverseConfig.idleMode(IdleMode.kCoast);
    ReverseConfig.openLoopRampRate(1.0);
    ReverseConfig.inverted(true);
  }

  public Robot() {}


  @SuppressWarnings("removal")
  @Override
  public void robotInit() {
    motor3 = new SparkMax(kmotor3CanID, MotorType.kBrushless);
    motor3.configure(ReverseConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    motor4 = new SparkMax(kmotor1CanID, MotorType.kBrushless);
    motor4.configure(DefaultConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    motor1 = new SparkMax(kmotor4CanID, MotorType.kBrushless);
    motor1.configure(ReverseConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    motor2 = new SparkMax(kmotor2CanID, MotorType.kBrushless);
    motor2. configure(DefaultConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    controller = new XboxController(0);
  }

  @Override
  public void robotPeriodic() {}

  @Override 
  public void autonomousInit() {}

  @Override 
  public void autonomousPeriodic() {}

  @Override 
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {

    currentRightStickValue = controller.getRightTriggerAxis(); 
  if (Math.abs(currentRightStickValue) > 0.05) {
    motor3.set(currentRightStickValue);
    motor4.set(currentRightStickValue);
  }
  else {
    motor3.stopMotor();
    motor4.stopMotor();
  }

  currentLeftStickValue = controller.getLeftTriggerAxis();
  if (Math.abs(currentLeftStickValue) > 0.05) {
    motor1.set(currentLeftStickValue);
    motor2.set(currentLeftStickValue);
  }
  else { 
    motor1.stopMotor();
    motor2.stopMotor();
  }
}



@Override
public void disabledInit() {}

@Override
public void disabledPeriodic() {}

@Override 
public void testInit() {}

@Override 
public void testPeriodic () {} 
}
