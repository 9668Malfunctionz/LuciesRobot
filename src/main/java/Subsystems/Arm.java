package Subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase{

    public static SparkFlexConfig DefaultConfig = new SparkFlexConfig();
    
    private SparkFlex arm; 
    
    public static final int kmotorCanID5 = 5;
    
    public double speedlimit = 0.2;

    static {
        DefaultConfig.smartCurrentLimit(50);
        DefaultConfig.idleMode(IdleMode.kCoast);
        DefaultConfig.openLoopRampRate(1.0);
        DefaultConfig.inverted(true);
    }

    
    public void init(){

        arm = new SparkFlex(kmotorCanID5, MotorType.kBrushless);
        arm.configure(DefaultConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void raise(double speed){

        if (Math.abs(speed) > 0.05) {
            arm.set(speed*speedlimit);
        }
        else {
            arm.stopMotor();
        }
    }
}
