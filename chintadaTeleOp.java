package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="chintadaTeleOp", group="Linear Opmode")
//@Disabled

public class chintadaTeleOp extends LinearOpMode {


    //declarations and stuff
    private final ElapsedTime runtime = new ElapsedTime();

    //gp1 declarations
    DcMotor leftFront = null;
    DcMotor rightFront = null;
    DcMotor leftRear = null;
    DcMotor rightRear = null;

    // motor speed variables
    double lf; double rf; double lr; double rr;
    double ra; double la;

    // gp2 declarations
    DcMotor leftArm = null;
    DcMotor rightArm = null;
    Servo grippyGripper = null;
    Servo gripperPivot = null;

    // joystick + trigger position variables
    double X1; double Y1; double X2; double Y2;

    // scalars
    double joyScale = 1.0; double trigScale = 1.0;
    double motorMax = 0.8; // limit motor power to this value for Andymark~ RUN_USING_ENCODER mode


    @Override
    public void runOpMode() {

        leftFront = hardwareMap.dcMotor.get("leftFront"); //FrontLeft
        rightFront = hardwareMap.dcMotor.get("rightFront"); //FrontRight
        leftRear = hardwareMap.dcMotor.get("leftRear"); //BackLeft
        rightRear = hardwareMap.dcMotor.get("rightRear"); //BackRight
        grippyGripper = hardwareMap.servo.get("grippyGripper");
        gripperPivot = hardwareMap.servo.get("gripperPivot");
        leftArm = hardwareMap.dcMotor.get("leftArm");
        rightArm = hardwareMap.dcMotor.get("rightArm");


        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftRear.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.FORWARD);
        leftArm.setDirection(DcMotor.Direction.REVERSE);
        rightArm.setDirection(DcMotorSimple.Direction.FORWARD);


        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // wait for the game to start
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            // reset speed variables
            lf = 0; rf = 0; lr = 0; rr = 0;

            // get joystick + trigger values
            Y1 = -gamepad1.left_stick_y * joyScale; // negative inverts so up is positive (correcting for motor mount direction)
            X1 = gamepad1.right_stick_x * joyScale; // frank here, idk if we still need this, look into that when you have a working chassis
            X2 = gamepad1.left_stick_x * joyScale;

            Y2 = gamepad2.left_stick_y * joyScale;
            // forward/back movement
            lf += Y1;
            rf += Y1;
            lr += Y1;
            rr += Y1;

            // side to side movement (strafing)
            lf += X2;
            rf -= X2;
            lr -= X2;
            rr += X2;

            // rotation
            lf += X1;
            rf -= X1;
            lr += X1;
            rr -= X1;

            // Slow Turning
            if (gamepad1.dpad_right){
                lf += .25; rf -= .25; lr += .25; rr -= .25;
            }
            if (gamepad1.dpad_left){
                lf -= .25; rf += .25; lr -= .25; rr += .25;
            }
            if (gamepad1.dpad_up){
                lf += .25; rf += .25; lr += .25; rr += .25;
            }
            if (gamepad1.dpad_down) {
                lf -= .25; rf -= .25; lr -= .25; rr -= .25;

            }

            if (gamepad2.left_bumper) {
                grippyGripper.setPosition(0.4);
            }
            if (gamepad2.right_bumper) {
                grippyGripper.setPosition(0.475);
            }

            if(gamepad2.y) {
                gripperPivot.setPosition(0.7);
            }
            if(gamepad2.a) {
                gripperPivot.setPosition(0.0);
            }

            la = Y2 * joyScale;
            ra = Y2 * joyScale;

            if(gamepad2.b) {
                la = -0.25;
                ra = -0.25;
            }

            //motor power limiter
            lf = Math.max(-motorMax, Math.min(lf, motorMax)); //FrontLeft
            rf = Math.max(-motorMax, Math.min(rf, motorMax)); //FrontRight
            lr = Math.max(-motorMax, Math.min(lr, motorMax)); //BackLeft
            rr = Math.max(-motorMax, Math.min(rr, motorMax)); //BackRight
            la = Math.max(-motorMax, Math.min(la, motorMax));
            ra = Math.max(-motorMax, Math.min(ra, motorMax));

            //set power to motors
            leftFront.setPower(lf);
            rightFront.setPower(rf);
            leftRear.setPower(lr);
            rightRear.setPower(rr);
            leftArm.setPower(la);
            rightArm.setPower(ra);

            // send some useful parameters to the driver station
            // (outputting the current wheel speed)
            telemetry.addData("LF", "%.3f", lf);
            telemetry.addData("RF", "%.3f", rf);
            telemetry.addData("LR", "%.3f", lr);
            telemetry.addData("RR", "%.3f", rr);
            telemetry.update();
        }
    }
}


//Expansion Hub: Arm Up Down --> 0
//Arm Extend Retract --> 2
//Arm Servo --> 0

/*
left joystick - arm slides
right joystick - swing gripper
left trigger - open gripper
right trigger - close gripper
 */