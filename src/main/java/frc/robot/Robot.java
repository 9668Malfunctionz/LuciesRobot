// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import Subsystems.TankDrive;

public class Robot extends TimedRobot {

  private XboxController controller;
  private TankDrive tankdrive;

  public Robot() {}


  @Override
  public void robotInit() {
    tankdrive = new TankDrive();
    tankdrive.init();
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
    tankdrive.drive(controller.getLeftY(), controller.getRightY());
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
