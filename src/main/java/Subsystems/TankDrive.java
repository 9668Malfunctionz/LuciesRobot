package Subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDrive extends SubsystemBase{

    public static SparkMaxConfig DefaultConfig = new SparkMaxConfig();
    public static SparkMaxConfig ReverseConfig = new SparkMaxConfig();
    
    private SparkMax frontleft; 
    private SparkMax frontright;
    private SparkMax backleft;
    private SparkMax backright;
    
    public static final int kmotorCanID1 = 1;
    public static final int kmotorCanID2 = 2;
    public static final int kmotorCanID3 = 3;
    public static final int kmotorCanID4 = 4;
    
    public double speedlimit = 0.2;
    private static final double kprecisionScale = 0.35;

    static {
        DefaultConfig.smartCurrentLimit(50);
        DefaultConfig.idleMode(IdleMode.kCoast);
        DefaultConfig.openLoopRampRate(1.0);
        DefaultConfig.inverted(true);
    }
    static {
        ReverseConfig.apply(DefaultConfig);
        ReverseConfig.inverted(false);
    }

    
    public void init(){
        frontleft = new SparkMax(kmotorCanID1, MotorType.kBrushless);
        frontleft.configure(DefaultConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        frontright = new SparkMax(kmotorCanID4, MotorType.kBrushless);
        frontright.configure(ReverseConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        backleft = new SparkMax(kmotorCanID2, MotorType.kBrushless);
        backleft.configure(DefaultConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        backright = new SparkMax(kmotorCanID3, MotorType.kBrushless);
        backright. configure(ReverseConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void arcadeDrive(double forward, double turn, boolean precision){
        forward = MathUtil.applyDeadband(forward, 0.05);
        turn = MathUtil.applyDeadband(turn, 0.05);

        // Keep the motor directions used by tank drive.
        double left = -forward-turn;
        double right = -forward+turn;
        double maxOutput = Math.max(1.0, Math.max(Math.abs(left), Math.abs(right)));
        double limit = precision ? speedlimit*kprecisionScale : speedlimit;

        frontleft.set(left/maxOutput*limit);
        backleft.set(left/maxOutput*limit);
        frontright.set(right/maxOutput*limit);
        backright.set(right/maxOutput*limit);
    }

    public void drive(double left, double right){

        if (Math.abs(left) > 0.05) {
            frontleft.set(left*speedlimit);
            backleft.set(left*speedlimit);
        }
        else {
            frontleft.stopMotor();
            backleft.stopMotor();
        }

        
        if (Math.abs(right) > 0.05) {
            frontright.set(right*speedlimit);
            backright.set(right*speedlimit);
        }
        else { 
            frontright.stopMotor();
            backright.stopMotor();
        }
    }
}
