package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/* PLEASEEE READ THE COMMENTS!!!! Written by Rylan Chintada, Head of Pope John Robotics Team 248
   Programming 2024-2026 */
// Comments are here to help with future reference either for myself or future Team 284 Programmers
// I HATE CODING, #programinglivesmatter #wehavelivestoo
// FEIN FEIN FEIN!
// M- MU- M- MUS- MUST TOUCH GR- G- G- GRA- GR- GRASS!!!!!
// I love and hate Ryan Hoo - Rylan Chintada, Head of Pope John Robotics Team 248 Programming
@Autonomous(name="waitScoreNPark", group="Robot")
//@Disabled
public class waitScoreNPark extends LinearOpMode {

    // Initializing Variables
    DcMotor leftFront = null;
    DcMotor rightFront = null;
    DcMotor leftRear = null;
    DcMotor rightRear = null;
    DcMotor leftArm = null;
    DcMotor rightArm = null;

    Servo grippyGripper = null;
    Servo gripperPivot = null;
    private final ElapsedTime runtime = new ElapsedTime();

    // Variables based on robot parts, adjust whenever a part is changed
    // Calculate the pulsesPerRev for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set driveGearReduction as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    // Check Spec Sheets on goBILDA website! - Rylan Chintada, Head of Pope John Robotics Team 248 Programming
    static final double pulsesPerRev = 537.7; // Set to 312 RPM goBILDA 5203 Series Yellow Jacket Planetary Motor
    static final double driveGearReduction = 1.0; // There is no external gearing
    static final double wheelDiameter = 3.779; // For figuring circumference
    static final double pulsesPerInch = (pulsesPerRev * driveGearReduction) / (wheelDiameter * 3.1415);
    static final double turningRadius = 8.875;
    static final double driveSpeed = 0.2;
    static final double turnSpeed = 0.2;
    static final double strafeSpeed = 0.2;
    static final double armSpeed = 0.5;

    @Override
    public void runOpMode() {
        // Setting Variables
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftRear = hardwareMap.get(DcMotor.class, "leftRear");
        rightRear = hardwareMap.get(DcMotor.class, "rightRear");
        leftArm = hardwareMap.get(DcMotor.class, "leftArm");
        rightArm = hardwareMap.get(DcMotor.class, "rightArm");
        grippyGripper = hardwareMap.get(Servo.class, "grippyGripper");
        gripperPivot = hardwareMap.get(Servo.class, "gripperPivot");

        // Setting drive train motors and modes
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftRear.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.FORWARD);
        leftArm.setDirection(DcMotor.Direction.REVERSE);
        rightArm.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Update telemetry to check if motors successfully initialized
        telemetry.addData("Starting at",  "%7d :%7d",
                leftFront.getCurrentPosition(),
                rightFront.getCurrentPosition(),
                leftRear.getCurrentPosition(),
                rightRear.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (Coach presses PLAY)
        waitForStart();

        // START OF AUTONOMOUS ---------------------------------------------------------------------
        sleep(5000);
        driveForward(driveSpeed, 42.0);
        driveBackward(driveSpeed, 42.0);
        sleep(1000);
        driveBackward(driveSpeed, 50.0);
//        strafeRight(strafeSpeed, 36.0);
//        driveForward(driveSpeed, 24.0);
//        strafeRight(strafeSpeed, 10.0);
//        driveBackward(driveSpeed, 45.0);
//        driveForward(driveSpeed, 45.0);
//        strafeRight(strafeSpeed, 8.0);
//        driveBackward(driveSpeed, 45.0);
//        driveForward(driveSpeed, 45.0);
//        strafeRight(strafeSpeed, 4.25);
//        driveBackward(driveSpeed, 45.0);
//        driveForward(driveSpeed, 45.0);
//        driveBackward(driveSpeed, 45.0);
    } // END OF AUTONOMOUS -------------------------------------------------------------------------

    // MOVEMENT METHODS
    /*
     *  Methods to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the OpMode running.
     *  timeoutS is the maximum amount of time for the robot to reach the position,
     *  If it does not reach it in time method will stop and go onto next step in autonomous
     */
    public void driveForward(double speed, double distance) {
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftRearTarget;
        int newRightRearTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftFrontTarget = leftFront.getCurrentPosition() + (int)(distance * pulsesPerInch);
            newRightFrontTarget = rightFront.getCurrentPosition() + (int)(distance * pulsesPerInch);
            newLeftRearTarget = leftRear.getCurrentPosition() + (int)(distance * pulsesPerInch);
            newRightRearTarget = rightRear.getCurrentPosition() + (int)(distance * pulsesPerInch);
            leftFront.setTargetPosition(newLeftFrontTarget);
            rightFront.setTargetPosition(newRightFrontTarget);
            leftRear.setTargetPosition(newLeftRearTarget);
            rightRear.setTargetPosition(newRightRearTarget);

            // Turn On RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            //sleep(20);
            leftFront.setPower(Math.abs(speed));
            rightFront.setPower(Math.abs(speed));
            leftRear.setPower(Math.abs(speed));
            rightRear.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    // (runtime.seconds() < timeoutS) &&
                    (leftFront.isBusy() && rightFront.isBusy() && leftRear.isBusy() && rightRear.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to",  " %7d :%7d", newLeftFrontTarget,  newRightFrontTarget, newLeftRearTarget, newRightRearTarget);
                telemetry.addData("Currently at",  " at %7d :%7d",
                        leftFront.getCurrentPosition(), rightFront.getCurrentPosition(), leftRear.getCurrentPosition(), rightRear.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);

            // Turn off RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(1000);   // optional pause after each move.
        }
    }

    public void driveBackward(double speed, double distance) {
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftRearTarget;
        int newRightRearTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftFrontTarget = leftFront.getCurrentPosition() - (int)(distance * pulsesPerInch);
            newRightFrontTarget = rightFront.getCurrentPosition() - (int)(distance * pulsesPerInch);
            newLeftRearTarget = leftRear.getCurrentPosition() - (int)(distance * pulsesPerInch);
            newRightRearTarget = rightRear.getCurrentPosition() - (int)(distance * pulsesPerInch);
            leftFront.setTargetPosition(newLeftFrontTarget);
            rightFront.setTargetPosition(newRightFrontTarget);
            leftRear.setTargetPosition(newLeftRearTarget);
            rightRear.setTargetPosition(newRightRearTarget);

            // Turn On RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            //sleep(20);
            leftFront.setPower(Math.abs(speed));
            rightFront.setPower(Math.abs(speed));
            leftRear.setPower(Math.abs(speed));
            rightRear.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    // (runtime.seconds() < timeoutS) &&
                    (leftFront.isBusy() && rightFront.isBusy() && leftRear.isBusy() && rightRear.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to",  " %7d :%7d", newLeftFrontTarget,  newRightFrontTarget, newLeftRearTarget, newRightRearTarget);
                telemetry.addData("Currently at",  " at %7d :%7d",
                        leftFront.getCurrentPosition(), rightFront.getCurrentPosition(), leftRear.getCurrentPosition(), rightRear.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);

            // Turn off RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(1000);   // optional pause after each move.
        }
    }

    public void strafeRight(double speed, double distance) {
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftRearTarget;
        int newRightRearTarget;

        // Determine new target positions
        newLeftFrontTarget = leftFront.getCurrentPosition() + (int) (distance * pulsesPerInch);
        newRightFrontTarget = rightFront.getCurrentPosition() - (int) (distance * pulsesPerInch);
        newLeftRearTarget = leftRear.getCurrentPosition() - (int) (distance * pulsesPerInch);
        newRightRearTarget = rightRear.getCurrentPosition() + (int) (distance * pulsesPerInch);

        // Set target positions
        leftFront.setTargetPosition(newLeftFrontTarget);
        rightFront.setTargetPosition(newRightFrontTarget);
        leftRear.setTargetPosition(newLeftRearTarget);
        rightRear.setTargetPosition(newRightRearTarget);

        // Turn On RUN_TO_POSITION
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set power
        leftFront.setPower(speed);
        rightFront.setPower(speed);
        leftRear.setPower(speed);
        rightRear.setPower(speed);

        // Wait until motors reach the target position
        while (opModeIsActive() && (leftFront.isBusy() && rightFront.isBusy() && leftRear.isBusy() && rightRear.isBusy())) {
            telemetry.addData("LeftFront Target/Current", "%7d / %7d", newLeftFrontTarget, leftFront.getCurrentPosition());
            telemetry.addData("RightFront Target/Current", "%7d / %7d", newRightFrontTarget, rightFront.getCurrentPosition());
            telemetry.addData("LeftRear Target/Current", "%7d / %7d", newLeftRearTarget, leftRear.getCurrentPosition());
            telemetry.addData("RightRear Target/Current", "%7d / %7d", newRightRearTarget, rightRear.getCurrentPosition());
            telemetry.addData("Left Front", "Power: %.2f, Position: %d", leftFront.getPower(), leftFront.getCurrentPosition());
            telemetry.addData("Right Front", "Power: %.2f, Position: %d", rightFront.getPower(), rightFront.getCurrentPosition());
            telemetry.addData("Left Rear", "Power: %.2f, Position: %d", leftRear.getPower(), leftRear.getCurrentPosition());
            telemetry.addData("Right Rear", "Power: %.2f, Position: %d", rightRear.getPower(), rightRear.getCurrentPosition());
            telemetry.update();

        }

        // Stop the motors
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);

        // Turn off RUN_TO_POSITION
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(1000);
    }
    public void strafeLeft(double speed, double distance) {
        int newLeftFrontTarget;
        int newRightFrontTarget;
        int newLeftRearTarget;
        int newRightRearTarget;

        // Determine new target positions
        newLeftFrontTarget = leftFront.getCurrentPosition() - (int) (distance * pulsesPerInch);
        newRightFrontTarget = rightFront.getCurrentPosition() + (int) (distance * pulsesPerInch);
        newLeftRearTarget = leftRear.getCurrentPosition() + (int) (distance * pulsesPerInch);
        newRightRearTarget = rightRear.getCurrentPosition() -  (int) (distance * pulsesPerInch);

        // Set target positions
        leftFront.setTargetPosition(newLeftFrontTarget);
        rightFront.setTargetPosition(newRightFrontTarget);
        leftRear.setTargetPosition(newLeftRearTarget);
        rightRear.setTargetPosition(newRightRearTarget);

        // Turn On RUN_TO_POSITION
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set power with reversed direction
        leftFront.setPower(speed);
        rightFront.setPower(speed);
        leftRear.setPower(speed);
        rightRear.setPower(speed);

        // Wait until motors reach the target position
        while (opModeIsActive() && (leftFront.isBusy() && rightFront.isBusy() && leftRear.isBusy() && rightRear.isBusy())) {
            telemetry.addData("Left Front", "Power: %.2f, Position: %d", leftFront.getPower(), leftFront.getCurrentPosition());
            telemetry.addData("Right Front", "Power: %.2f, Position: %d", rightFront.getPower(), rightFront.getCurrentPosition());
            telemetry.addData("Left Rear", "Power: %.2f, Position: %d", leftRear.getPower(), leftRear.getCurrentPosition());
            telemetry.addData("Right Rear", "Power: %.2f, Position: %d", rightRear.getPower(), rightRear.getCurrentPosition());
            telemetry.update();

        }

        // Stop the motors
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftRear.setPower(0);
        rightRear.setPower(0);

        // Turn off RUN_TO_POSITION
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(1000);
    }

    public void rotateRight(double speed, double degrees) {
        if (opModeIsActive()) {
            // Calculate the number of encoder ticks needed to rotate the robot by the given angle
            double ticksToRotate = (degrees / 360) * (2 * Math.PI * turningRadius) * pulsesPerRev;

            // Determine new target positions
            int newLeftFrontTarget = leftFront.getCurrentPosition() - (int) ticksToRotate;
            int newRightFrontTarget = rightFront.getCurrentPosition() - (int) ticksToRotate;
            int newLeftRearTarget = leftRear.getCurrentPosition() - (int) ticksToRotate;
            int newRightRearTarget = rightRear.getCurrentPosition() - (int) ticksToRotate;

            // Set target positions
            leftFront.setTargetPosition(newLeftFrontTarget);
            rightFront.setTargetPosition(newRightFrontTarget);
            leftRear.setTargetPosition(newLeftRearTarget);
            rightRear.setTargetPosition(newRightRearTarget);

            // Turn On RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Set power to rotate right
            leftFront.setPower(Math.abs(speed));
            rightFront.setPower(Math.abs(speed));
            leftRear.setPower(Math.abs(speed));
            rightRear.setPower(Math.abs(speed));

            // Wait until motors reach the target position
            while (opModeIsActive() && (leftFront.isBusy() && rightFront.isBusy() && leftRear.isBusy() && rightRear.isBusy())) {
                telemetry.addData("Left Front", "Power: %.2f, Position: %d", leftFront.getPower(), leftFront.getCurrentPosition());
                telemetry.addData("Right Front", "Power: %.2f, Position: %d", rightFront.getPower(), rightFront.getCurrentPosition());
                telemetry.addData("Left Rear", "Power: %.2f, Position: %d", leftRear.getPower(), leftRear.getCurrentPosition());
                telemetry.addData("Right Rear", "Power: %.2f, Position: %d", rightRear.getPower(), rightRear.getCurrentPosition());
                telemetry.update();

            }

            // Stop the motors after rotation
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);

            // Turn off RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            sleep(1000);
        }
    }

    public void rotateLeft(double speed, double degrees) {
        if (opModeIsActive()) {
            // Calculate the number of encoder ticks needed to rotate the robot by the given angle
            double ticksToRotate = (degrees / 360) * (2 * Math.PI * turningRadius) * pulsesPerRev;

            // Determine new target positions
            int newLeftFrontTarget = leftFront.getCurrentPosition() + (int) ticksToRotate;
            int newRightFrontTarget = rightFront.getCurrentPosition() + (int) ticksToRotate;
            int newLeftRearTarget = leftRear.getCurrentPosition() + (int) ticksToRotate;
            int newRightRearTarget = rightRear.getCurrentPosition() + (int) ticksToRotate;

            // Set target positions
            leftFront.setTargetPosition(newLeftFrontTarget);
            rightFront.setTargetPosition(newRightFrontTarget);
            leftRear.setTargetPosition(newLeftRearTarget);
            rightRear.setTargetPosition(newRightRearTarget);

            // Turn On RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Set power to rotate left
            leftFront.setPower(Math.abs(speed));
            rightFront.setPower(Math.abs(speed));
            leftRear.setPower(Math.abs(speed));
            rightRear.setPower(Math.abs(speed));

            // Wait until motors reach the target position
            while (opModeIsActive() && (leftFront.isBusy() && rightFront.isBusy() && leftRear.isBusy() && rightRear.isBusy())) {
                telemetry.addData("Left Front", "Power: %.2f, Position: %d", leftFront.getPower(), leftFront.getCurrentPosition());
                telemetry.addData("Right Front", "Power: %.2f, Position: %d", rightFront.getPower(), rightFront.getCurrentPosition());
                telemetry.addData("Left Rear", "Power: %.2f, Position: %d", leftRear.getPower(), leftRear.getCurrentPosition());
                telemetry.addData("Right Rear", "Power: %.2f, Position: %d", rightRear.getPower(), rightRear.getCurrentPosition());
                telemetry.update();

            }

            // Stop the motors after rotation
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);

            // Turn off RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            sleep(1000);
        }
    }

    public void raiseArm(double speed, int distance) {
        int leftArmTarget = leftArm.getCurrentPosition() + (distance);
        int rightArmTarget = rightArm.getCurrentPosition() + (distance);

        leftArm.setTargetPosition(leftArmTarget);
        rightArm.setTargetPosition(rightArmTarget);

        leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftArm.setPower(speed);
        rightArm.setPower(speed);

        //leftArm.setPower(0);
        //rightArm.setPower(0);

        leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(1000);
    }

    public void lowerArm(double speed, int distance) {
        int leftArmTarget = leftArm.getCurrentPosition() - (distance);
        int rightArmTarget = rightArm.getCurrentPosition() - (distance);

        leftArm.setTargetPosition(leftArmTarget);
        rightArm.setTargetPosition(rightArmTarget);

        leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftArm.setPower(speed);
        rightArm.setPower(speed);

        leftArm.setPower(0);
        rightArm.setPower(0);

        leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(1000);
    }
}