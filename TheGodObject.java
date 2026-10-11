package org.firstinspires.ftc.teamcode;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.limelightvision.LLFieldMap;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLResultTypes.*;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import java.util.List;
import com.acmerobotics.*;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
@Autonomous(name = "TheGodObject")
public class TheGodObject extends LinearOpMode{
    double x = 0.0, y = 0.0, theta = 0.0;

    static final double p = 0.022, i = 0.02, d = 0.001;
    static final double ph = 0.02, ih = 0.02, dh = 0.001;

    //--Drive Motors--


    //--Pinpoint--
    private GoBildaPinpointDriver pinpoint;

    private PIDController xCon = new PIDController(p, i, d);
    private PIDController yCon = new PIDController(p, i, d);
    private PIDController thetaCon = new PIDController(ph, ih, dh);

    private Curves path;
    private MoveScum profile;
    private ElapsedTime timer;

    private CustomDrive drive;

    // Constants for Feedforward
    // If your max speed is 40 inches/sec, Kv is roughly 1/40 (0.025) to convert inches/sec to motor power.
    private final double kV = 0.025;


    @Override
    public void runOpMode() throws InterruptedException {

        drive = new CustomDrive(hardwareMap);

        path = new Curves(
        new Point(0, 0),
        new Point(0, 30),
        new Point(30, 0),
        new Point(40, 0)
        );

        Curves path2 = new Curves(
                new Point(40, 0),
                new Point(30, 15),
                new Point(20,30),
                new Point(0, 0)
        );

        profile = new MoveScum(40, 30);


        telemetry.addLine("Ready");
        telemetry.update();

        waitForStart();

        //THIS IS WHERE YOU ACTUALLY ADD STUFF, DON'T TOUCH ANYTHING PAST THE END OF THIS IF STATEMENT
        if(opModeIsActive()){
            followPath(path, profile, 0.0, 3);
            sleep(500);
            followPath(path2, profile, 85, 3);
        }


    }
    public void followPath(Curves path, MoveScum profile, double targetheta, double last) {
        xCon.reset();
        yCon.reset();
        thetaCon.reset();
        
        ElapsedTime timer = new ElapsedTime();
        double totPathDist = path.arclength;

        while (opModeIsActive()) {
            drive.updatePose();
            double currentTime = timer.seconds();
            if(currentTime >= last){
                break;
            }
            MoveScum.Profile targetState = profile.calculate(totPathDist, currentTime);

            Point endPoint = path.getPosT(1.0);
            if (targetState.v == 0 && Math.abs(endPoint.x - drive.x) < 2.0 && Math.abs(endPoint.y - drive.y) < 2.0) {
                break;
            }

            double t = path.getT(targetState.pos);
            Point targetCoord = path.getPosT(t);
            Point rawDerivative = path.getDerivT(t);

            double pathAngle = Math.atan2(rawDerivative.y, rawDerivative.x);

            double ffPowerX = Math.cos(pathAngle) * targetState.v * kV;
            double ffPowerY = Math.sin(pathAngle) * targetState.v * kV;

            double pidPowerX = xCon.calculate(drive.x, targetCoord.x);
            double pidPowerY = yCon.calculate(drive.y, targetCoord.y);

            double headingError = targetheta - drive.theta;
            while (headingError > 180) {
                headingError -= 360;
            }
            while (headingError <= -180){ 
                headingError += 360;
            }
            double pidPowerHeading = thetaCon.calculate(0, headingError);

            double finalDriveX = -(ffPowerX + pidPowerX);
            double finalDriveY = -(ffPowerY + pidPowerY);
            double finalTurn = -pidPowerHeading;

            drive.fieldrive(finalDriveX, finalDriveY, finalTurn, drive.theta);

            telemetry.addData("Target Dist", targetState.pos);
            telemetry.addData("Error X", targetCoord.x - drive.x);
            telemetry.addData("Error Y", targetCoord.y - drive.y);
            telemetry.addData("Error Theta", targetheta - drive.theta);
            telemetry.update();
        }
        drive.drive(0, 0, 0);
    }
}


