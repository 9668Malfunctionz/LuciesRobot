// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import Subsystems.TankDrive;
import Subsystems.Arm;
import Subsystems.Claw;

public class Robot extends TimedRobot {

  private XboxController controller;
  private TankDrive tankdrive;
  private Arm arm;
  private Claw claw;

  public Robot() {}


  @Override
  public void robotInit() {
    tankdrive = new TankDrive();
    tankdrive.init();
    arm = new Arm();
    arm.init();
    claw = new Claw();
    claw.init();
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
    tankdrive.arcadeDrive(-controller.getLeftY(), controller.getLeftX(), controller.getLeftBumperButton());
    arm.raise(-controller.getRightY(), controller.getRightBumperButton());
    claw.move(controller.getLeftTriggerAxis() > 0.05, controller.getRightTriggerAxis() > 0.05);
  }

  @Override
  public void teleopExit() {
    claw.stop();
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
